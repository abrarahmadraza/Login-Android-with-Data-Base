package com.example.login_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user,pass;
    MyDbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.username_login);
        pass=findViewById(R.id.password_login);
        helper=new MyDbHelper(this);
        load();
    }

    public void open_register(View view) {
        Intent i=new Intent(this,Register.class);
        startActivity(i);
    }
    private void load() {
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.rawQuery("select name,username from detail where status=?",new String[]{"1"});
        if(c.getCount()!=0){
            c.moveToFirst();
            Intent i=new Intent(this,Logout.class);
            i.putExtra("name",c.getString(0));
            i.putExtra("username",c.getString(1));
            startActivity(i);
        }
    }

    public void login(View view) {
        SQLiteDatabase db=helper.getReadableDatabase();
        SQLiteDatabase write=helper.getWritableDatabase();
        String username,password;
        username=user.getText().toString();
        password=pass.getText().toString();
        if(username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
        }else{
            Cursor c=db.rawQuery("Select password from detail where username=?",new String[]{username});
            if(c.getCount()==0){
                Toast.makeText(getApplicationContext(),"Username doesn't exist!",Toast.LENGTH_LONG).show();
            }else{
                c.moveToFirst();
                if(password.equals(c.getString(0))){
                    ContentValues values=new ContentValues();
                    values.put("status","1");
                    write.update("detail",values,"username=?",new String[]{username});
                    load();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Password!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
