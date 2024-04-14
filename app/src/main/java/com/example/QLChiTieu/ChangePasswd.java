package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ChangePasswd extends AppCompatActivity {

    EditText edtcurrentPass, edtPass, edtcfPass;
    private SessionManagement sessionManagement;
    DatabaseHelper dbcenter;
    Cursor cursor;
    String username, currentpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        sessionManagement = new SessionManagement(ChangePasswd.this);
        dbcenter = new DatabaseHelper(this);
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        username = user.get(sessionManagement.KEY_EMAIL);
        currentpass = user.get(sessionManagement.KEY_PASSWOrD);
        edtcurrentPass = findViewById(R.id.currpassword);
        edtPass = findViewById(R.id.newpassword);
        edtcfPass = findViewById(R.id.confirmnewpass);
        //todo:Currency exchange

    }
    public void changePasswd(View view){
        String currPass = edtcurrentPass.getText().toString();
        String password = edtPass.getText().toString();
        String confPass = edtcfPass.getText().toString();
        if(password.equals("") || password.trim().isEmpty() ||
                currPass.equals("") || currPass.trim().isEmpty() || confPass.equals("") || confPass.trim().isEmpty())
        {
            Toast.makeText(ChangePasswd.this,"All fields must be filled in",Toast.LENGTH_LONG).show();
        } else if (!currPass.equals(currentpass)) {
            Toast.makeText(ChangePasswd.this,"Current password is incorrect",Toast.LENGTH_LONG).show();
        } else if(!confPass.equals(password)){
            Toast.makeText(ChangePasswd.this,"Password must be the same",Toast.LENGTH_LONG).show();
        } else{
            dbcenter.editUser(username,password);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_LONG).show();}
    }
}