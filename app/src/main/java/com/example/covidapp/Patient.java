package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Patient  {
    String Name;
    String Phone;
    int Age;
    public Patient() {
    }

    public Patient(String name, String phone, int age) {
        Name = name;
        Phone = phone;
        // Email = email;
        //Password = password;
        Age = age;
        //Type=getIntent().getStringExtra("type"));
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
    public String toString(){
       return this.Name+" "+this.Age+" "+this.Phone;
    }

}