package com.example.sensorexample.model;

/*
 * Date 21st April 2019
 * App:- SensorDemo
 * Module:- Sensor list data
 * PageName:- Items.this
 * Description:- List item getter setter class
 * Author:- Akhilesh Dubey
 * */

public class Items {
    // define variable
    public float azimuth;
    public float pitch;
    public float roll;

    // constructor
    public Items(float azimuth, float pitch, float roll){
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.roll = roll;
    }
}
