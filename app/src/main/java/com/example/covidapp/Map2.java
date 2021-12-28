package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Map2 extends AppCompatActivity {
    EditText new_lat;
EditText new_lang;
Button next;
String lang;
String lat;
String name;
EditText new_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        new_lat=(EditText) findViewById(R.id.b_lat2);
        new_lang=(EditText) findViewById(R.id.b_lang2);
        new_name=(EditText) findViewById(R.id.editTextTextPersonName);
        next=(Button) findViewById(R.id.b_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lat=new_lat.getText().toString();
                lang=new_lang.getText().toString();
                name=new_name.getText().toString();
                Intent x=new Intent(Map2.this , Tabs2.class);
                x.putExtra("lat",lat);
                x.putExtra("lang",lang);
                x.putExtra("name",name);
                startActivity(x);
            }
        });

    }

   // public double getLang() {
    //    return lang;
    //}

    //public EditText getNew_lat() {
     //   return new_lat;
   // }


}