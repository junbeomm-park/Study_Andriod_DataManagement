package com.example.datamanagement.exam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class DBHandler {
    private Context context;
    private ExamDBHelper helper;
    private SQLiteDatabase db;
    private DBHandler(Context context){
        this.context = context;
        helper = new ExamDBHelper(context);
        db = helper.getWritableDatabase(); //DB파일이 open

    }
    public static DBHandler open(Context context){
        DBHandler handler = new DBHandler(context);
        return handler;
    }
    public void close(){
        helper.close();
    }
    public Cursor search(String name) {
        String sql = "";
        Cursor cursor = null;
        if(name=="all"){
            sql = "select * from product";
            cursor = db.rawQuery(sql,null);
        }else{
            sql = "select * from product where name like ?";
            cursor = db.rawQuery(sql,new String[]{"%"+name+"%"});
        }
        return cursor;
    }
    public String[] selectAllData() {
        //만약 일반 ArrayAdapter나 커스트마이징된 Adapter를 쓴다면
        //Cursor객체를 List나 배열로 변환
        String sql = "select * from product";
        Cursor cursor = db.rawQuery(sql,null);
        String[] datas = new String[cursor.getCount()]; //row의 개수가 리턴
        int count = 0;
        while(cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int su = cursor.getInt(3);
            int totPrice = cursor.getInt(4);
            datas[count] = "상품번호 : "+_id + " 상품명 : " + name + " 가격 : " + price +" 수량 : "+ su +" TotalPrice : "+ totPrice;
            count++;
        }
        return datas;
    }
    public Bundle readData(int id) {
        Bundle data = new Bundle();
        String sql = "select * from product where _id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id+""});
        if(cursor.moveToNext()){
            data.putInt("_id", cursor.getInt(0));
            data.putString("name", cursor.getString(1));
            data.putInt("price", cursor.getInt(2));
            data.putInt("su", cursor.getInt(3));
            data.putInt("totPrice", cursor.getInt(4));
        }
        Log.d("park", data.toString());
        return data;
    }
    public  void insertData(String name,int price, int su, int totPrice){
        try{
            String sql = "insert into product(name,price,su,totPrice)" +
                        "values(?,?,?,?)";
                    db.execSQL(sql,new String[]{name,price+"",su+"",totPrice+""});

                    Log.d("park","정상처리");
        }catch (Exception e){
                    Log.d("park","에러메시지 "+e);
        }
    }
}
