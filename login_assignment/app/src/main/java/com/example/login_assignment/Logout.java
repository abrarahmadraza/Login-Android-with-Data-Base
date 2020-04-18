package com.example.login_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Logout extends AppCompatActivity {
    MyDbHelper helper;
    TextView name_,username_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.logout);
        name_=findViewById(R.id.name_logout);
        username_=findViewById(R.id.username_logout);
        helper=new MyDbHelper(this);
        load();
    }
    public void open_login_(View view) {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("status","");
        db.update("detail",values,null,new String[]{});
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void load(){
        Intent i=getIntent();
        String name,username;
        name=i.getStringExtra("name");
        username=i.getStringExtra("username");
        name_.setText("Name : "+name);
        username_.setText("Username : "+username);
    }
}
