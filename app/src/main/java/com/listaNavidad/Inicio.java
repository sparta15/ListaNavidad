package com.listaNavidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Present;

public class Inicio extends AppCompatActivity {

    private EditText presentName, presentPrize;
    private Button submitButton;
    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        dba = new DatabaseHandler(Inicio.this);

        presentName = (EditText) findViewById(R.id.presentEditText);
        presentPrize = (EditText) findViewById(R.id.priceEditText);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }
        });
    }

    private void saveDataToDB() {

        Present present = new Present();
        String name = presentName.getText().toString().trim();
        String calsString = presentPrize.getText().toString().trim();

        int prize = Integer.parseInt(calsString);

        if (name.equals("") || calsString.equals("")) {

            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_LONG).show();

        }else {

            present.setPresentName(name);
            present.setPresentPrize(prize);

            dba.addPresent(present);
            dba.close();


            //clear the form
            presentName.setText("");
            presentPrize.setText("");

            //take users to next screen (display all entered items)
            startActivity(new Intent(Inicio.this, DisplayPresentsActivity.class));
        }

    }
}
