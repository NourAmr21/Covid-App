package com.example.covidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
   private EditText email2;
   private EditText password2;
   private Button register;
   private String email;
   private String password;
   private EditText phone_text;
   private EditText age_text;
   private EditText name_text;
   private String name;
   private int age;
   private String phone;
   private FirebaseDatabase rootnode;
   private DatabaseReference reference;
   public FirebaseAuth mAuth;
   private String type;
   private String uID;
    public String getUID(){
        return uID;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         email2= (EditText) findViewById(R.id.emailfield3);
         password2= (EditText) findViewById(R.id.passfield3);
         register=(Button) findViewById(R.id.regbutton2);
         mAuth = FirebaseAuth.getInstance();
         phone_text=(EditText) findViewById(R.id.editTextPhone1);
         age_text=(EditText) findViewById(R.id.editTextNumber1);
         name_text=(EditText) findViewById(R.id.editTextTextPersonName1);
         register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toregister();
            }
        });
    }
    public void toregister() {
        uID=mAuth.getUid();
        email= email2.getText().toString();
        password= password2.getText().toString();
        name=name_text.getText().toString();
        phone=phone_text.getText().toString();
        age=Integer.parseInt(age_text.getText().toString());
        String type1= getIntent().getStringExtra("type");
//        if(type1.equals("Patient")){
//            type="Patient";
//        }
//        else{
//            type="Doctor";
//        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(type1!=null){
                        if (task.isSuccessful()&& MainActivity.type.equals("Doctor")) {
                           rootnode=FirebaseDatabase.getInstance();
                           reference=rootnode.getReference("Doctors");
                           Doctor doctor=new Doctor(name,phone,age);
                          // User user = new User(name,phone,age);
                          // user.setType(type1);
                           reference.child(mAuth.getUid()).setValue(doctor);//Nour*****
                            Intent intent=new Intent(Register.this , Tabs2.class);
//                            intent.putExtra("type",type1);
                            startActivity(intent);
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        if (task.isSuccessful()&& MainActivity.type.equals("Patient")){
                            rootnode=FirebaseDatabase.getInstance();
                            reference=rootnode.getReference("Patients");
                            Patient patient=new Patient(name,phone,age);
                            // User user = new User(name,phone,age);
                            // user.setType(type1);
                            reference.child(mAuth.getUid()).setValue(patient);//Nour*****
                            Intent intent=new Intent(Register.this , Tabs.class);
//                            intent.putExtra("type",type1);
                            startActivity(intent);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                }});
    }
}