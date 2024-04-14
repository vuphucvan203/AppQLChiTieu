package com.example.QLChiTieu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class CheckNote extends AppCompatActivity {
    DatabaseHelper dbcenter;
    Cursor cursor;
    TextView text1, text2, text3, text4, text5, text6, text7;
    private SessionManagement sessionManagement;
    String idtran ="", income="", amount= "", date="", note="",category="";
    Button del,upd,back;
    private ImageView imgnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_note);
        sessionManagement = new SessionManagement(CheckNote.this);
        dbcenter = new DatabaseHelper(this);
        text1 = (TextView) findViewById(R.id.lhtcategory);
        text2 = (TextView) findViewById(R.id.cat);
        text3 = (TextView) findViewById(R.id.mon);
        text4 = (TextView) findViewById(R.id.date);
        text5 = (TextView) findViewById(R.id.not);
        imgnote = (ImageView) findViewById(R.id.imgnote2);
        del = (Button) findViewById(R.id.delete);
        upd = (Button) findViewById(R.id.update);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        final HashMap<String, String> user = sessionManagement.getUserInformation();

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM transactions WHERE id_transaction = '" + getIntent().getStringExtra("id") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) //if the query results are not empty
        {
            //fill the textview variable with query results according to the index
            cursor.moveToPosition(0);
            idtran = cursor.getString(0).toString();
            date =  cursor.getString(1).toString();
            income = cursor.getString(2).toString();
            category = cursor.getString(3).toString();
            amount = cursor.getString(5).toString();
            note =  cursor.getString(6).toString();

            text1.setText(cursor.getString(2).toString().toUpperCase(Locale.ROOT));
            text2.setText(cursor.getString(3).toString());
            text3.setText(cursor.getString(5).toString());
            text4.setText(cursor.getString(1).toString());
            text5.setText(cursor.getString(6).toString());

        }

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getApplicationContext(), UpdateNote.class);
                //passes data from this class to the UpdateNote class
                m.putExtra("idTran",idtran);
                m.putExtra("income",income);
                m.putExtra("category",category);
                m.putExtra("amount", amount);
                m.putExtra("date", date);
                m.putExtra("note",note);
                startActivity(m);
            }
        });
    }

    //create a confirmation alert if you want to delete a note
    public void delete(View view){
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(CheckNote.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete this one?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                        db.execSQL("delete from transactions where id_transaction = "+idtran);
                        Intent m = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(m);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog ale = alert.create();
                ale.show();;

            }
        });

    }
}