package com.example.datamanagement.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.datamanagement.R;

public class ReadActivity extends AppCompatActivity {
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        result = findViewById(R.id.resultpage);
        Intent intent = getIntent();
        String product = intent.getStringExtra("resultPage");
        result.setText(product);
    }
}