package com.example.covidapp;

public class Hospital {
    private String name;
    private double lat;
    private double lang;
    private String date;

    public Hospital(String name, double lat, double lang) {
        this.name = name;
        this.lat = lat;
        this.lang = lang;
        this.date="";
    }
    public Hospital(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  String toString(){
        String s=new String("Name:"+name+" "+"Lat:"+lat+" "+"Lang:"+" "+lang);
        return  s;
    }
}
