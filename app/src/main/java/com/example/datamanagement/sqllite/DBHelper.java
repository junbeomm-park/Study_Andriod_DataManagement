package com.example.datamanagement.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//데이터베이스가 업데이트되거나 DB를 처음 생성하는 경우 사용할 클래스
public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public DBHelper(Context context){
        //파일의 형태로 데이터가 저장되고 관리된다. - 이 코드가 데이터베이스를 오픈하고 연결
        super(context,"test.db",null,DB_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
