package com.listaNavidad;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Present;
import util.Utils;

public class DisplayPresentsActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Present> dbPresents = new ArrayList<>();
    private CustomListviewAdapter presentAdapter;
    private ListView listView;

    private Present myPresent;
    private TextView totalPrize, totalPresents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_presents);

        listView = (ListView) findViewById(R.id.list);
        totalPrize = (TextView) findViewById(R.id.totalAmountTextView);
        totalPresents = (TextView) findViewById(R.id.totalItemsTextView);

        refreshData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayPresentsActivity.this,Inicio.class));
            }
        });
    }


    private void refreshData() {
        dbPresents.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Present> presentFromDB = dba.getPresent();

        double priceValue = (double)Math.round(dba.totalPrize() * 100) / 100;
        int totalItems = dba.getTotalItems();


        String formattedValue = priceValue+"";
        String formattedItems = totalItems+"";
        totalPrize.setText("Total Prizes: " + formattedValue + "€");
        totalPresents.setText("Total Present: " + formattedItems);
        if(presentFromDB.size()>0) {
            for (int i = 0; i < presentFromDB.size(); i++) {

                String name = presentFromDB.get(i).getPresentName();
                String dateText = presentFromDB.get(i).getRecordDate();
                double price = presentFromDB.get(i).getPresentPrice();
                int foodId = presentFromDB.get(i).getPresentId();
                Log.d("ASD", price + "----------------");
                Log.v("PRESENT IDS: ", String.valueOf(foodId));


                myPresent = new Present();
                myPresent.setPresentName(name);
                myPresent.setRecordDate(dateText);
                myPresent.setPresentPrice(price);
                myPresent.setPresentId(foodId);

                dbPresents.add(myPresent);

            }
            dba.close();

            //setup adapter
            presentAdapter = new CustomListviewAdapter(DisplayPresentsActivity.this, R.layout.list_row, dbPresents);
            listView.setAdapter(presentAdapter);
            presentAdapter.notifyDataSetChanged();
        } else {
            finish();
        }
    }

}
