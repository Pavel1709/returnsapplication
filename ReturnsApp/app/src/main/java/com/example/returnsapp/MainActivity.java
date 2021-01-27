package com.example.returnsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView texteview = (TextView) findViewById(R.id.textView3);
        texteview.setTextSize(25);

        Button button = findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final Handler handler = new Handler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.setDocumentName(editText.getText().toString());
                openActivitySecond();
            }
        });
    }

    public void openActivitySecond() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
