package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText edtEmail, edtPassword,edtUser, edtPass;
    DatabaseHelper dbcenter;
    Button tonReg;
    Cursor cursor;

    //We don't actually use email here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbcenter = new DatabaseHelper(this);
        edtEmail = (EditText) findViewById(R.id.editemail);
        edtPassword = (EditText) findViewById(R.id.editpassword);
        edtPass = (EditText) findViewById(R.id.editconfirmpass);
        edtUser =(EditText) findViewById(R.id.edituser);
        tonReg=(Button) findViewById(R.id.buttonregister);
        tonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUser.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confPass = edtPass.getText().toString();
                if(email.equals("") || email.trim().isEmpty() || password.equals("") || password.trim().isEmpty() ||
                        username.equals("") || username.trim().isEmpty() || confPass.equals("") || confPass.trim().isEmpty())
                {
                    Toast.makeText(Register.this,"All fields must be filled in",Toast.LENGTH_LONG).show();
                }else if(!confPass.equals(password)){
                    Toast.makeText(Register.this,"Password must be the same",Toast.LENGTH_LONG).show();
                } else if (checkIfUserExisted(email)) {
                    Toast.makeText(Register.this, "This user is already existed", Toast.LENGTH_SHORT).show();
                } else{
                    dbcenter.addUser(email,username,password,"VND");
                    Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_LONG).show();
                    Intent mIntent = new Intent(getApplicationContext(),Login.class);
                    startActivity(mIntent);}
            }
        });
    }
    public boolean checkIfUserExisted(String user){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        //get data
        cursor = db.rawQuery("SELECT * FROM user WHERE email = '" + user +"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            return true;
            } else {
            return false;
            }
    }
}