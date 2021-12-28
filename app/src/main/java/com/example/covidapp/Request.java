package com.example.covidapp;

import java.util.Date;

public class Request {
    String patient;
    boolean closed;
    String request;
    String reply;
    String date;
   // String Name;
    //int age;
    public Request(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Request(String patient , String request, boolean closed ) {
        this.patient = patient;
        this.closed=closed;
        this.request=request;
        this.reply="";
        this.date="";

        //this.Name=
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
