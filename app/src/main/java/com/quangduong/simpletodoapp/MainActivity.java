package com.quangduong.simpletodoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String inputFile = "items.txt";
    EditText tvItem;
    ListView lvItems;
    String inputData;
    ArrayAdapter adapter;
    List<String> listResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAdd).setOnClickListener(this);

        //read data from file
        listResults = FileUtils.readFromFile(this, inputFile);

        tvItem = (EditText) findViewById(R.id.etItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listResults);
        lvItems.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                inputData = tvItem.getText().toString();
                listResults.add(inputData);
                adapter.notifyDataSetChanged();
                tvItem.setText("");

                StringBuilder myOutWriter = new StringBuilder();
                for (int i = 0; i < listResults.size(); i++) {
                    myOutWriter.append(listResults.get(i));
                    myOutWriter.append("\n");
                }

                //TODO: optimate cho nay sau. Luu cho nay la tach
                FileUtils.writeToFile(myOutWriter.toString(), this, inputFile);
                break;
        }
    }
}
