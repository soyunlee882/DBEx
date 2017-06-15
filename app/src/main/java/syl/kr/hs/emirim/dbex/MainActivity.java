package syl.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editName, editCount,editResultName, editResultCount;
    Button butInit, butInsert, butSelect,butupdate,butdelete;
    MyDBHlpe myHelper;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=(EditText)findViewById(R.id.edit_group_name);
        editCount=(EditText)findViewById(R.id.edit_group_count);
        editResultName=(EditText)findViewById(R.id.edit_result_name);
        editResultCount=(EditText)findViewById(R.id.edit_result_count);
        butInit=(Button)findViewById(R.id.but_init);
        butInsert=(Button)findViewById(R.id.but_insert);
        butSelect=(Button)findViewById(R.id.but_selecet);
        butupdate=(Button)findViewById(R.id.but_update);
        butdelete=(Button)findViewById(R.id.but_delete);

        //DB 생성
        myHelper=new MyDBHlpe(this);

        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        butInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sqldb=myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqldb,1,2);
                sqldb.close();
                selectTable();
            }
        });
        butInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sqldb=myHelper.getWritableDatabase();
                String sql="insert into idolTable values('"+editName.getText()+"',"+editCount.getText()+")";
                sqldb.execSQL(sql);
                sqldb.close();
                Toast.makeText(MainActivity.this,"저장되었습니다",Toast.LENGTH_LONG).show();
                selectTable();
            }
        });
        butSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTable();

            }
        });
        butupdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sqldb=myHelper.getWritableDatabase();
                String sql="update idolTable set idolcount="+editCount.getText()+" where idolName='"+editName.getText()+"'";//공백문자 꼭 필요
                sqldb.execSQL(sql);
                sqldb.close();
                Toast.makeText(MainActivity.this,"수정되었습니다",Toast.LENGTH_LONG).show();
                selectTable();
            }
        });
        butdelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sqldb=myHelper.getWritableDatabase();
                String sql="delete from idolTable where idolName='"+editName.getText()+"'";//공백문자 꼭 필요
                sqldb.execSQL(sql);
                sqldb.close();
                Toast.makeText(MainActivity.this,"삭제되었습니다",Toast.LENGTH_LONG).show();
                selectTable();
            }
        });
        selectTable();

    }
   public void selectTable(){
       sqldb=myHelper.getReadableDatabase();
       String sql="select*from idolTable";
       Cursor cursor=sqldb.rawQuery(sql,null);
       //rawQuary: select를 실행 수 커서 반환
       String names="Idol 이름"+"\r\n"+"======="+"\r\n";
       String count="Idol 인원수"+"\r\n"+"======="+"\r\n";
       while(cursor.moveToNext()){
           names +=cursor.getString(0)+"\r\n";
           count+=cursor.getInt(1)+"\r\n";

       }
       editResultName.setText(names);
       editResultCount.setText(count);

       cursor.close();
       sqldb.close();

   }
    class MyDBHlpe extends SQLiteOpenHelper{

        public MyDBHlpe(Context context) {
            //idolDb라 이름의 테이터 베이스가 생성된다.
            // public MyDBHlpe(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, "idolDB", null, 1);

        }

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
        String sql="drop table if exists idolTable";
                sqLiteDatabase.execSQL(sql);
                onCreate(sqLiteDatabase);
        }
    }

}
