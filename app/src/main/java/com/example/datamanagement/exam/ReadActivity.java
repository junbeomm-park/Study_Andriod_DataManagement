package com.example.datamanagement.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.datamanagement.R;

public class ReadActivity extends Activity {
    TextView result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        Log.d("kim",data.toString()+"=======================read");
        TextView t = (TextView)findViewById(R.id.resultpage);
        t.setText(data.getInt("_id")+","+data.getString("name")+
                ","+data.getInt("totPrice"));
    }
}