package syl.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    class MyDBHlpe extends SQLiteOpenHelper{

        public MyDBHlpe(Context context) {
            //idolDb라 이름의 테이터 베이스가 생성된다.
            // public MyDBHlpe(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, "idolDB",null,1);

        //idolTavble 라는 이름의 테이블 생성
            //
            // @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql="create table idolTable(idolName text not null primary key,idolCount integer)";
         sqLiteDatabase.execSQL(sql);
        }

        //이미 idolTable 이 존재한다면 기존의 테이블을 삭제하고, 새로 만들 때 호출
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="drop table if exist idolTable";
                sqLiteDatabase.execSQL(sql);
                onCreate(sqLiteDatabase);
        }
    }







}
