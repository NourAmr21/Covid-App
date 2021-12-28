package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button doctor;
    Button patient;
    static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doctor=(Button) findViewById(R.id.dbutton);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Sign_in.class);
                type="Doctor";
                intent.putExtra("type","Doctor");
                startActivity(intent);

            }
        });
        patient=(Button) findViewById(R.id.pbutton);
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Sign_in.class);
                type="Patient";
                intent.putExtra("type","Patient");
                startActivity(intent);
            }
        });

    }

   // public void toSignIn() {
       // Intent intent = new Intent(getApplicationContext() , Sign_in.class);
     //   startActivity(intent);
    //}
}