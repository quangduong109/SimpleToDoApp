package com.quangduong.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    EditText etItem;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.icon);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        etItem =(EditText) findViewById(R.id.etItem);
        btnSave = (Button) findViewById(R.id.btnSave);

        String item = getIntent().getStringExtra("item");
        etItem.setText(item);
        etItem.setSelection(item.length());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = etItem.getText().toString();
                if(result.isEmpty()){
                    Toast.makeText(EditItemActivity.this, R.string.message_warning, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("result", result);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
