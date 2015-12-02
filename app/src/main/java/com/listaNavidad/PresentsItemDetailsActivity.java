package com.listaNavidad;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import model.Present;

public class PresentsItemDetailsActivity extends AppCompatActivity {

    private TextView presentName, presentPrize, dateTaken;
    private int presentId;
    private Present present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePresents();
            }
        });

        presentName = (TextView) findViewById(R.id.detsPresentName);
        presentPrize = (TextView) findViewById(R.id.detsPriceValue);
        dateTaken = (TextView) findViewById(R.id.detsDateText);


        present = (Present) getIntent().getSerializableExtra("userObj");

        presentName.setText(present.getPresentName());
        presentPrize.setText(String.valueOf(present.getPresentPrice()));
        dateTaken.setText(present.getRecordDate());

        presentId = present.getPresentId();

        presentPrize.setTextSize(34.9f);
        presentPrize.setTextColor(Color.RED);


    }


    public void sharePresents() {

        StringBuilder dataString = new StringBuilder();

        String name = presentName.getText().toString();
        String prize = presentPrize.getText().toString();
        String date = dateTaken.getText().toString();

        dataString.append(" Present: " + name + "\n");
        dataString.append(" Prize: " + prize + "\n");
        dataString.append(" Buy on: " + date);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Caloric Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"recipient@example.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{

            startActivity(Intent.createChooser(i, "Send mail..."));

        }catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Please install email client before sending",
                    Toast.LENGTH_LONG).show();
        }
    }

}
