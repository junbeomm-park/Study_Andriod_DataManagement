package com.example.datamanagement.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datamanagement.R;

public class DBMainActivity extends AppCompatActivity {
    EditText id;
    EditText name;
    EditText age;
    TextView result;
    DBHelper dbHelper; //데이터베이스 파일생성, 테이블생성, 업데이트....
    SQLiteDatabase db; // 로컬디비연동을 위한 핵심 클래스
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbjob_main);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        result = findViewById(R.id.result);

        // 1. DBHelper생성
        dbHelper = new DBHelper(this);
        // 2. SQLiteDatabase 생성
        db = dbHelper.getWritableDatabase(); //읽기쓰기가 가능하도록
    }
    public void insert(View v){
        String sql = "insert into member(id,name,age) values(?,?,?)";
        //sql을 실행하고 ?는 Object[]로 처리
        db.execSQL(sql, new String[]{id.getText().toString(),
                                   name.getText().toString(),
                                    age.getText().toString()});
        id.setText("");
        name.setText("");
        age.setText("");
        showToast("삽입성공");
    }
    public void selectAll(View v){
        result.setText("");
        String sql = "select * from member";
        //Cursor가 jdbc의 ResultSet과 동일
        //=> Cursor는 조회한 레코드들을 저장하고 있는 객체
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount(); //레코드의 갯수를 반환
        showToast("조회된 row : "+count);
        while (cursor.moveToNext()){
            int idx = cursor.getInt(0);
            String id = cursor.getString(1);
            String name = cursor.getString(2);
            int age = cursor.getInt(3);
            result.append("번호 : "+idx+"\n"+
                    "아이디 : "+id+"\n"+
                    "성명 : "+name+"\n"+
                    "나이 : "+age+"\n"+
                    "=========================\n" );

        }
    }

    public void update(View v){
        String sql = "update member set age = ? where id = ?";
        //Object[]은 ?를 셋팅하기 위한 객체 ?순서대로 값을 셋팅해야 한다.
        db.execSQL(sql,new String[]{age.getText().toString(), id.getText().toString()});
    }
    public void delete(View v){
        String sql = "delete from member where id = ?";
        db.execSQL(sql,new String[]{id.getText().toString()});
    }
    public void search(View v){
        result.setText("");
        String sql = "select * from member where id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id.getText().toString()});
        while (cursor.moveToNext()){
            int idx = cursor.getInt(0);
            String id = cursor.getString(1);
            String name = cursor.getString(2);
            int age = cursor.getInt(3);
            result.append("번호 : "+idx+"\n"+
                    "아이디 : "+id+"\n"+
                    "성명 : "+name+"\n"+
                    "나이 : "+age+"\n"+
                    "=========================\n" );

        }
    }
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}