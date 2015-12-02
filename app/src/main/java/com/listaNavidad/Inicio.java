package com.listaNavidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Present;

public class Inicio extends AppCompatActivity {

    private EditText presentName, presentPrice;
    private Button submitButton, goToList;
    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        dba = new DatabaseHandler(Inicio.this);

        presentName = (EditText) findViewById(R.id.presentEditText);
        presentPrice = (EditText) findViewById(R.id.priceEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        goToList = (Button) findViewById(R.id.goToListButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(saveDataToDB())
                    startActivity(new Intent(Inicio.this, DisplayPresentsActivity.class));
            }
        });

        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inicio.this, DisplayPresentsActivity.class));
            }
        });
    }

    private boolean saveDataToDB() {

        Present present = new Present();
        String name = presentName.getText().toString().trim();
        String calsString = presentPrice.getText().toString().trim();

        if (name.equals("") || calsString.equals("")) {

            Toast.makeText(getApplicationContext(), R.string.toastBegin, Toast.LENGTH_LONG).show();
            return false;
        }else {
            double price = Double.parseDouble(calsString);
            present.setPresentName(name);
            present.setPresentPrice(price);

            dba.addPresent(present);
            dba.close();


            //clear the form
            presentName.setText("");
            presentPrice.setText("");
        }
        return true;
    }
}
