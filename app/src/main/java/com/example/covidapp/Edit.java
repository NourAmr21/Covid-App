package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit extends AppCompatActivity {
private EditText name;
private EditText age;
private EditText phone;
private String name1;
private String age1;
private String phone1;
DatabaseReference reference;
Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        name=(EditText) findViewById(R.id.name_edit);
        age=(EditText) findViewById(R.id.age_edit);
        phone=(EditText) findViewById(R.id.phone_edit);

        done=(Button) findViewById(R.id.button3);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=name.getText().toString();
                age1=age.getText().toString();
                phone1=phone.getText().toString();
                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                if(MainActivity.type.equals("Patient")){
                    reference= FirebaseDatabase.getInstance().getReference().child("Patients").child(mAuth.getUid().toString());
                    reference.child("name").setValue(name1);
                    reference.child("age").setValue(Integer.parseInt(age1));
                    reference.child("phone").setValue(phone1);
                }
                else{
                    reference= FirebaseDatabase.getInstance().getReference().child("Doctors").child(mAuth.getUid().toString());
                    reference.child("name").setValue(name1);
                    reference.child("age").setValue(Integer.parseInt(age1));
                    reference.child("phone").setValue(phone1);
                }
                if(MainActivity.type.equals("Patient")){
                Intent intent=new Intent(Edit.this , Tabs.class);
                startActivity(intent);
                }
                else{
                    Intent intent=new Intent(Edit.this , Tabs2.class);
                    startActivity(intent);
                }
            }
        });
    }
}