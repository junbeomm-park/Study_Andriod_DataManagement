package com.example.datamanagement.exam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datamanagement.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText productName;
    EditText price;
    EditText amount;
    ListView result;
    ExamDBHelper dbHelper; //데이터베이스 파일생성, 테이블생성, 업데이트....
    SQLiteDatabase db; // 로컬디비연동을 위한 핵심 클래스
    // HashMap (디비배열과 안겹치게 변수명 선언 해야함)
    ArrayList<HashMap<String,String>> ArrayList =
            new ArrayList<HashMap<String, String>>();
    public static final int INPUT_DATA_RESULT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        productName = findViewById(R.id.edtName);
        AutoCompleteTextView auto = findViewById(R.id.edtName);
        price = findViewById(R.id.edtPrice);
        amount = findViewById(R.id.edtSu);
        result = findViewById(R.id.list);

        // 1. DBHelper생성
        dbHelper = new ExamDBHelper(this);
        // 2. SQLiteDatabase 생성
        db = dbHelper.getWritableDatabase(); //읽기쓰기가 가능하도록

        // AutoComplete
        ArrayAdapter autoadapter = ArrayAdapter.createFromResource(this,
                                                                R.array.mylist_data,
                                        android.R.layout.simple_dropdown_item_1line);
        auto.setAdapter(autoadapter);




    }
    public void insert(View v){
        String sql = "insert into product(name,price,su,totPrice) values(?,?,?,?)";
        //sql을 실행하고 ?는 Object[]로 처리
        db.execSQL(sql, new Object[]{productName.getText().toString(),
                                    Integer.parseInt(price.getText().toString()),
                                    Integer.parseInt(amount.getText().toString()),
                                    Integer.parseInt(price.getText().toString()) * Integer.parseInt(amount.getText().toString()) });
        productName.setText("");
        price.setText("");
        amount.setText("");
        showToast("삽입성공");
    }
    public void selectAll(View v) {
        result.setAdapter(result.getAdapter());
        String sql = "select * from product";
        //Cursor가 jdbc의 ResultSet과 동일
        //=> Cursor는 조회한 레코드들을 저장하고 있는 객체
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount(); //레코드의 갯수를 반환
        showToast("조회된 row : " + count);
        String[] dataList = new String[count];
        int i = 0;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            //데이터 조회해서 배열로 만들기
            dataList[i] = _id + name + price;
            i++;

        }
        //만들어진 배열과 어댑터를 만들어서 리스트뷰에 연결하기
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                dataList);
        result.setAdapter(adapter);
        //상세페이지
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                intent.putExtra("resultPage",dataList[position]);
                startActivityForResult(intent,INPUT_DATA_RESULT);
                Log.d("park",dataList[position]);
            }
        });
    }
   public void selectAll2(View v) {

        result.setAdapter(result.getAdapter());
        String sql = "select * from product";
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        showToast("조회된 row : " + count);
        String[] dataList = new String[count];
        int i = 0;
        while (cursor.moveToNext()){
            HashMap<String,String> item = new HashMap<>();
            String name = cursor.getString(1);
            item.put("name",name);
            int price = cursor.getInt(2);
            item.put("price",price+"");
            ArrayList.add(item);

            dataList[i] = name + price;
            i++;


        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                ArrayList , // HashMap으로 구성된 데이터가 저장된 리스트
                android.R.layout.simple_list_item_2, // row 디자인
                new String[]{"name","price"} , // HashMap에 저장된 key목록
                new int[]{android.R.id.text1,android.R.id.text2});
        result.setAdapter(adapter);
       //상세페이지
       result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(MainActivity.this, ReadActivity.class);
               intent.putExtra("resultPage",dataList[position]);
               startActivityForResult(intent,INPUT_DATA_RESULT);
               Log.d("park",dataList[position]);
           }
       });

    }
    public void search (View v) {
        result.setAdapter(result.getAdapter());
        String sql = "select * from product where name like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{productName.getText().toString()});
        showToast("조회된 data : " + cursor.toString());
        int count = cursor.getCount();
        int i = 0;
        String[] dataList = new String[count];
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);

            dataList[i] = _id + name + price;
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                dataList);
        result.setAdapter(adapter);
    }



    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}