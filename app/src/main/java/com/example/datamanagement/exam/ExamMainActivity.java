package com.example.datamanagement.exam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datamanagement.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    EditText productName;
    EditText productprice;
    EditText productamount;
    ListView result;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        handler = DBHandler.open(this);

        findViewById(R.id.btnIns).setOnClickListener(this);
        findViewById(R.id.btnResult).setOnClickListener(this);
        findViewById(R.id.btnResult2).setOnClickListener(this);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        result = findViewById(R.id.list);
        result.setOnItemClickListener(this);

        productName = findViewById(R.id.edtName);
        productamount = findViewById(R.id.edtSu);
        productprice = findViewById(R.id.edtPrice);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnIns){
            if(productName.getText().toString().equals("")){
                Toast.makeText(this,"상품명을 입력하세요 ",Toast.LENGTH_LONG).show();
                return;
            }
            int price = Integer.parseInt(productprice.getText().toString());
            int su = Integer.parseInt(productamount.getText().toString());
            int totPrice = price * su;
            handler.insertData(productName.getText().toString(),
                                price,su,totPrice);

        }else if(v.getId()==R.id.btnResult){
            String[] datas = handler.selectAllData();
            ArrayAdapter adapter =
                    new ArrayAdapter(this,
                            android.R.layout.simple_list_item_1,
                            datas);
            result.setAdapter(adapter);

        }else if(v.getId()==R.id.btnResult2){
            Cursor c = handler.search("all");
            SimpleCursorAdapter adapter =
                    new SimpleCursorAdapter(this,
                            android.R.layout.simple_list_item_2,
                            c,
                            new String[]{"name","price"},
                            new int[] {android.R.id.text1,android.R.id.text2},0);
            result.setAdapter(adapter);
        }else if(v.getId()==R.id.btnSearch){
            String search = productName.getText().toString();
            Cursor c = handler.search(search);
            SimpleCursorAdapter adapter =
                    new SimpleCursorAdapter(this,
                            android.R.layout.simple_list_item_2,
                            c,
                            new String[]{"name","totPrice"},
                            new int[] {android.R.id.text1,android.R.id.text2});
            result.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle data = handler.readData(position+1);
        Log.d("park",data.toString());
        Log.d("park","onListItemClick()");
        Intent i = new Intent(this, ReadActivity.class);
        i.putExtra("data",data);
        startActivity(i);
    }
}


