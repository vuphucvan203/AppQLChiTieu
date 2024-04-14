package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddNote extends AppCompatActivity {

    private Uri UrlGambar;
    Button addincome, addexp, save, camera;
    Context context;
    Spinner category;
    ImageView imgnote;
    final int sdk = Build.VERSION.SDK_INT;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tvDateResult;
    private Button btDatePicker;
    EditText date, note, amount;
    String mCurrentPhotoPath;
    String income = "Income";
    SessionManagement sessionManagement;
    DatabaseHelper dbcenter;


    private static final String[] pathIncome = {"Salary", "Bonus", "Allowance", "Petty cash", "Other"};
    private static final String[] pathExpenses = {"Food", "Social Life", "Transportation", "Gift", "Health", "Other"};
    private final int version = 1;
    private static final int CAMERA = 1;
    private static final int FILE = 2;
    Button back;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        sessionManagement = new SessionManagement(AddNote.this);
        final HashMap<String, String> user = sessionManagement.getUserInformation();
        dbcenter = new DatabaseHelper(this);
        addincome = (Button) findViewById(R.id.addincome);
        addexp = (Button) findViewById(R.id.addexp);
        imgnote = (ImageView) findViewById(R.id.imgnote);
        date = (EditText) findViewById(R.id.date);
        note = (EditText) findViewById(R.id.edtnote);
        amount = (EditText) findViewById(R.id.edtamount);
        category = findViewById(R.id.spinnerCategory);
        adapter = new ArrayAdapter<String>(AddNote.this,
                android.R.layout.simple_spinner_item, pathIncome);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                income = "Income";
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(context, R.color.white));
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.white));
                } else {
                    addincome.setBackground(ContextCompat.getDrawable(AddNote.this, R.color.black));
                    addincome.setTextColor(ContextCompat.getColor(AddNote.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(AddNote.this, R.color.white));
                    addexp.setBackground(ContextCompat.getDrawable(AddNote.this, R.color.white));
                    addexp.setTextColor(ContextCompat.getColor(AddNote.this, R.color.black));
                    adapter = new ArrayAdapter<String>(AddNote.this,
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
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(context, R.color.white));
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.white));


                } else {
                    addexp.setBackground(ContextCompat.getDrawable(AddNote.this, R.color.black));
                    addexp.setTextColor(ContextCompat.getColor(AddNote.this, R.color.white));
                    addincome.setBackground(ContextCompat.getDrawable(AddNote.this, R.color.white));
                    addincome.setTextColor(ContextCompat.getColor(AddNote.this, R.color.black));
                    adapter = new ArrayAdapter<String>(AddNote.this,
                            android.R.layout.simple_spinner_item, pathExpenses);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(adapter);

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


        save = (Button) findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().trim().equals("")|| date.getText().toString().trim().equals("")) {
                    Toast.makeText(AddNote.this, "Fill all the neccesary field", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbcenter.addtransaction(date.getText().toString(), income, category.getSelectedItem().toString(), Integer.parseInt(user.get(sessionManagement.KEY_ID_USER)),
                            Integer.parseInt(amount.getText().toString()),note.getText().toString());
                    Intent m = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(m);
                }

            }
        });

        camera = (Button) findViewById(R.id.btncamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, version);

            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if (requestCode == REQUEST_IMAGE_CAPTURE) {
        //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent

        // If you are using Glide.
        //}

        super.onActivityResult(requestCode, resultCode, data);
        if (this.version == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgnote.setImageBitmap(bitmap);
            imgnote.setMinimumHeight(170);
            imgnote.setMinimumWidth(200);



            //  Glide.with(this).load(imageFilePath).into(mImageView);
        }
    }

    private void showDateDialog() {

        /**
         * Calendar to get the current date
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * This method is called when we finish selecting the date in the DatePicker
                 */

                /**
                 * Set Calendar to hold the selected date
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update the TextView with the chosen date
                 */
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * DatePicker dialog
         */
        datePickerDialog.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.malakdianadewi.moneymanager",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgnote.getWidth();
        int targetH = imgnote.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgnote.setImageBitmap(bitmap);
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}