package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings extends AppCompatActivity {

    private static final String[] currency = {"VND", "USD", "Ruble"};
    private SessionManagement sessionManagement;
    Spinner changeCurr;
    DatabaseHelper dbcenter;
    Cursor cursor;
    private ArrayList<Integer> dataAmount,dataAmountBackUp;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        changeCurr = findViewById(R.id.changCurrSpinner);
        dbcenter = new DatabaseHelper(this);
        adapter = new ArrayAdapter<String>(Settings.this,
                android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCurr.setAdapter(adapter);
        sessionManagement = new SessionManagement(Settings.this);
        dataAmount = new ArrayList<Integer>();
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        changeCurr.setSelection(getselected(user));
        changeCurr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                double tempm = 0;
                if(i == 0 && !user.get(sessionManagement.KEY_CURRENCY).equals(changeCurr.getSelectedItem().toString())) {
                    if (MainActivity.CURRENCY.equals("USD")) {
                        tempm = 24990;
                        runcalc(tempm);
                    }
                    if (MainActivity.CURRENCY.equals("Ruble")) {
                        tempm = 267.64;
                        runcalc(tempm);
                    }
                }
                if(i == 1 && !user.get(sessionManagement.KEY_CURRENCY).equals(changeCurr.getSelectedItem().toString())){
                    if(MainActivity.CURRENCY.equals("VND")){
                        tempm = 0.000040;
                        runcalc(tempm);
                    }
                    else if(MainActivity.CURRENCY.equals("Ruble")){
                        tempm = 0.011;
                        runcalc(tempm);
                    }
                }
                if(i == 2 && !user.get(sessionManagement.KEY_CURRENCY).equals(changeCurr.getSelectedItem().toString())){
                    if(MainActivity.CURRENCY.equals("VND")){
                        tempm = 0.0037;
                        runcalc(tempm);
                    }
                    else if(MainActivity.CURRENCY.equals("USD")){
                        tempm = 92.90;
                        runcalc(tempm);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public int getselected(HashMap<String, String> user){
        if(user.get(sessionManagement.KEY_CURRENCY).equals("VND")){
            return 0;
        }
        else if(user.get(sessionManagement.KEY_CURRENCY).equals("USD")){
            return 1;
        }
        else{
            return 2;
        }
    }

    public void changepasswd(View view){
        Intent i = new Intent(getApplicationContext(),ChangePasswd.class);
        startActivity(i);
    }

    public void runcalc(double tempm){
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery(String.format("SELECT * FROM transactions where id_user = %s", user.get(sessionManagement.KEY_ID_USER)),null);
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            dataAmount.add(Integer.parseInt(cursor.getString(5).toString()));
        }
        dataAmountBackUp = new ArrayList<Integer>();
        for (int da:dataAmount) {
            int temp = (int) Math.ceil(da*tempm);
            dataAmountBackUp.add(temp);
        }
        dataAmount.clear();
        for (int da:dataAmountBackUp) {
            dataAmount.add(da);
        }
        MainActivity.CURRENCY = changeCurr.getSelectedItem().toString();
        sessionManagement.updatecurr(MainActivity.CURRENCY);
        dbcenter.updateCurrency(Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),dataAmountBackUp);
        dbcenter.editUserCurrency(Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),MainActivity.CURRENCY);
        MainActivity.CURRENCY = changeCurr.getSelectedItem().toString();
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
    }
}