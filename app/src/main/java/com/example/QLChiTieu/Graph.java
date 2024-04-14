package com.example.QLChiTieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.QLChiTieu.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph extends AppCompatActivity {
    DatabaseHelper dbcenter;
    SessionManagement sessionManagement;
    Button back;
    Cursor cursor;
    PieDataSet pieDataSet;
    PieData pieData;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        back = findViewById(R.id.back);
        pieChart = findViewById(R.id.pieChart);
        dbcenter = new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {//back to menu
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

        ArrayList<PieEntry> visitors = new ArrayList<>();
        sessionManagement = new SessionManagement(Graph.this);
        if (sessionManagement.isLoggedIn()) {
            final HashMap<String, String> user = sessionManagement.getUserInformation();

            SQLiteDatabase db = dbcenter.getReadableDatabase();
            cursor = db.rawQuery("SELECT SUM(amount) as total, category FROM transactions WHERE id_user = '" + user.get(sessionManagement.KEY_ID_USER) + "' and income = 'Income' group by category", null);
            cursor.moveToFirst();

            for (int cc = 0; cc < cursor.getCount(); cc++) {
                cursor.moveToPosition(cc);
                int amount = Integer.parseInt(cursor.getString(0).toString());
                String category = cursor.getString(1).toString();
                //create different colors
                if(cc == 0){
                    visitors.add(new PieEntry(amount, category));
                }else if (cc == 1){
                    visitors.add(new PieEntry(amount, category));
                }else if(cc == 2){
                    visitors.add(new PieEntry(amount, category));
                }else if(cc == 3){
                    visitors.add(new PieEntry(amount, category));
                }else if(cc == 4){
                    visitors.add(new PieEntry(amount, category));
                }
            }
        }

        pieDataSet = new PieDataSet(visitors, "INCOME GRAPHIC");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("INCOME GRAPHIC BAR");
        pieChart.animate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pieChart.clearAnimation();
        pieChart.clear();
        pieDataSet.clear();
        pieData.clearValues();
        dbcenter.close();
    }
}