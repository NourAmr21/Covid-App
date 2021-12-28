package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Hello extends AppCompatActivity {
TextView hello;
Button location;
Button user_data;
Button b_map2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        location=(Button)findViewById(R.id.locationbutton);
        user_data=(Button)findViewById(R.id.userbutton);
        hello=(TextView) findViewById(R.id.textView3);
        b_map2=(Button)findViewById(R.id.button_map2);
        b_map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hello.this , Map2.class));
            }
        });
        location.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
              // if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                //  if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                  //     Toast.makeText(Hello.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(Hello.this , MapActivity.class));
                    }
               // }else{
               //     requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            //   }
           // }


        });
    }

  //  @Override
   // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       // if(requestCode==101){
         //   if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
           //     Toast.makeText(Hello.this, "Permission Granted", Toast.LENGTH_SHORT).show();
             //   startActivity(new Intent(Hello.this , MapActivity.class));


            //}
            //else{
              //  Toast.makeText(Hello.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            //}
       //}

    //  public void onRequestPermissionResult)(int requestCode , @NonNull String[]permissions , )
}
