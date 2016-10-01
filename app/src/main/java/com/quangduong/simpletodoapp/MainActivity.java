package com.quangduong.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String inputFile = "items.txt";
    private static final int REQUEST_CODE = 100;
    EditText tvItem;
    ListView lvItems;
    String inputData;
    ArrayAdapter adapter;
    List<String> listResults;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.icon);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        tvItem = (EditText) findViewById(R.id.etItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        findViewById(R.id.btnAdd).setOnClickListener(this);

        //read data from file
        listResults = FileUtils.readFromFile(this, inputFile);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listResults);
        lvItems.setAdapter(adapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", (String)lvItems.getItemAtPosition(i));
                index = i;
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listResults.remove(i);
                adapter.notifyDataSetChanged();
                persisData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                inputData = tvItem.getText().toString();
                if(inputData.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.message_warning, Toast.LENGTH_SHORT).show();
                    return;
                }
                listResults.add(inputData);
                adapter.notifyDataSetChanged();
                tvItem.setText("");
                persisData();

                break;
        }
    }

    private void persisData() {
        StringBuilder myOutWriter = new StringBuilder();
        for (int i = 0; i < listResults.size(); i++) {
            myOutWriter.append(listResults.get(i));
            myOutWriter.append("\n");
        }

        //TODO: optimate cho nay sau. Luu cho nay la tach
        FileUtils.writeToFile(myOutWriter.toString(), this, inputFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String result = data.getExtras().getString("result");
            if(index != -1 && !listResults.get(index).equals(result)){
                listResults.set(index, result);
                adapter.notifyDataSetChanged();
                persisData();
            }
        }
    }
}
