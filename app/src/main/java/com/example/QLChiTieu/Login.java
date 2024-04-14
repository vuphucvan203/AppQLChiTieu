package com.example.QLChiTieu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QLChiTieu.R;

public class Login extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    TextView tvEmail;
    Button buttonLogin, buttonRegis;
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;
    protected Cursor cursor;

    //We don't actually use email here

    @Override
    protected void onCreate(Bundle   savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagement = new SessionManagement(Login.this);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnlogin);
        buttonRegis = findViewById(R.id.signup);
        dbcenter = new DatabaseHelper(this);
        if(sessionManagement.isLoggedIn()){
            goToActivity();;
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(email.equals("") || email.trim().isEmpty() || password.equals("") || password.trim().isEmpty()  ) {
                    Toast.makeText(Login.this,"Username Password must be entered",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SQLiteDatabase db = dbcenter.getReadableDatabase();
                    cursor = db.rawQuery("SELECT * FROM user WHERE email = '" + email + "' AND password = '"+ password+ "'",null);
                    cursor.moveToFirst();
                    if(cursor.getCount()>0){
                        sessionManagement.createLoginSession(cursor.getString(0).toString(),cursor.getString(1).toString(),cursor.getString(2).toString(), password,cursor.getString(4));
                        goToActivity();
                    }else {
                        Toast.makeText(Login.this, "Wrong information", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(),Register.class);
                startActivity(mIntent);
            }
        });
    }
    private void goToActivity(){
        Intent mIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mIntent);
    }

}