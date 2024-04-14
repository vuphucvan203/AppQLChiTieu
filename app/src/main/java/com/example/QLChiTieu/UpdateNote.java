package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class UpdateNote extends AppCompatActivity {

    Button addincome, addexp, camera;
    Context context;
    Spinner category;
    ImageView imgnote;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tvDateResult;
    private Button btDatePicker , save;
    EditText date, note, amount;
    //String imageFilePath;
    String income = "Income";
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;
    Button back;
    ArrayAdapter<String> adapter;

    private static final String[] pathIncome = {"Salary", "Bonus", "Allowance", "Petty cash", "Other"};
    private static final String[] pathExpenses = {"Food", "Social Life", "Transportation", "Gift", "Health", "Other"};
    private final int versi = 1;
    String kat, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        sessionManagement = new SessionManagement(UpdateNote.this);
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        dbcenter = new DatabaseHelper(this);
        addincome = (Button) findViewById(R.id.addincome);
        addexp = (Button) findViewById(R.id.addexp);
        imgnote = (ImageView) findViewById(R.id.imgnote);
        date = (EditText) findViewById(R.id.date);
        note = (EditText) findViewById(R.id.edtnote);
        amount = (EditText) findViewById(R.id.edtamount);
        category = findViewById(R.id.spinnerCategory);
        save = (Button)  findViewById(R.id.btnsave);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        date.setText(getIntent().getStringExtra("date"));
        amount.setText(getIntent().getStringExtra("amount"));
        note.setText(getIntent().getStringExtra("note"));
        kat = getIntent().getStringExtra("category");
        id = getIntent().getStringExtra("idTran");
        income = getIntent().getStringExtra("income");


        if (income.equals("Income")) {
            income = "Income";
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            } else {

                //Change the color of the button
                addincome.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.black));
                addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                addexp.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.white));
                addexp.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.black));

                //Fill in adapter
                adapter = new ArrayAdapter<String>(UpdateNote.this,
                        android.R.layout.simple_spinner_item, pathIncome);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(adapter);
                int select = 0;
                for (int i = 0; i < pathIncome.length; i++) {
                    if (pathIncome[i].equals(category)) {
                        select = i;
                        break;
                    }
                }
                category.setSelection(select);
            }
        }else{
            income = "Expenses";
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            } else {
                addexp.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.black));
                addexp.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                addincome.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.white));
                addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.black));
                adapter = new ArrayAdapter<String>(UpdateNote.this,
                        android.R.layout.simple_spinner_item, pathExpenses);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(adapter);
                int select =0;
                for (int i=0 ; i < pathExpenses.length ; i++){
                    if (pathExpenses[i].equals(category)){
                        select=i;
                        break;
                    }
                }
                category.setSelection(select);

            }
        }

        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                income = "Income";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                } else {
                    addincome.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                    addexp.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.white));
                    addexp.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.black));
                    adapter = new ArrayAdapter<String>(UpdateNote.this,
                            android.R.layout.simple_spinner_item, pathIncome);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter);

                }
            }
        });
        addexp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                income = "Expenses";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(context, R.color.white));
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.white));


                } else {
                    addexp.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.white));
                    addincome.setBackground(ContextCompat.getDrawable(UpdateNote.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(UpdateNote.this, R.color.black));
                    adapter = new ArrayAdapter<String>(UpdateNote.this,
                            android.R.layout.simple_spinner_item, pathExpenses);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter);

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().trim().equals("")|| date.getText().toString().trim().equals("")) {
                    Toast.makeText(UpdateNote.this, "Fill all the necessary field", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbcenter.updatetransaction(Integer.parseInt(id),date.getText().toString(), income, category.getSelectedItem().toString(), Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),
                            Integer.parseInt(amount.getText().toString()), note.getText().toString());
                    Intent m = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(m);
                }

            }
        });
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tvDateResult = (EditText) findViewById(R.id.date);
        btDatePicker = (Button) findViewById(R.id.calendar);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        camera = (Button) findViewById(R.id.btncamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, versi);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (requestCode == REQUEST_IMAGE_CAPTURE) {
        //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent

        // If you are using Glide.
        //}
        super.onActivityResult(requestCode, resultCode, data);
        if (this.versi == requestCode && resultCode == RESULT_OK) { //menampilkan hasil foto di imageview
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgnote.setImageBitmap(bitmap);
            imgnote.setMinimumHeight(170);
            imgnote.setMinimumWidth(200);

        }
    }
    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}