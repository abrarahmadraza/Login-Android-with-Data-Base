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

public class Register extends AppCompatActivity {
    MyDbHelper helper;
    EditText name,username,pass,conf_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.register);
        helper=new MyDbHelper(this);
        name=findViewById(R.id.name_register);
        username=findViewById(R.id.username_register);
        pass=findViewById(R.id.password_register);
        conf_pass=findViewById(R.id.confirm_password);
    }

    public void open_login(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void register(View view) {
        SQLiteDatabase read=helper.getReadableDatabase();
        SQLiteDatabase write=helper.getWritableDatabase();
        String name_,username_,pass_,conf_pass_;
        name_=name.getText().toString();
        username_=username.getText().toString();
        pass_=pass.getText().toString();
        conf_pass_=conf_pass.getText().toString();
        if(name_.equals("") || username_.equals("") || pass_.equals("") || conf_pass_.equals("")){
            Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
        }else if(!pass_.equals(conf_pass_)){
            Toast.makeText(getApplicationContext(),"Password doesn't match!",Toast.LENGTH_LONG).show();
        }
        else{
            Cursor c=read.rawQuery("select * from detail where username=?",new String[]{username_});
            if(c.getCount()>0){
                Toast.makeText(getApplicationContext(),"Username already exists!",Toast.LENGTH_LONG).show();
            }else {
                ContentValues values=new ContentValues();
                values.put("name",name_);
                values.put("username",username_);
                values.put("password",pass_);
                write.insert("detail",null,values);
                Toast.makeText(getApplicationContext(),"Registered Successfull : "+name_,Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
            }
        }
    }
}
