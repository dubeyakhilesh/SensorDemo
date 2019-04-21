package com.example.sensorexample.view;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.hardware.SensorEvent;

import com.example.sensorexample.R;
import com.example.sensorexample.model.Items;
import com.example.sensorexample.presenter.ListPresenter;

import java.util.ArrayList;

/*
* Date 21st April 2019
* App:- SensorDemo
* Module:- Sensor list data
* PageName:- MainActivity.this
* Description:- List of sensor events
* Author:- Akhilesh Dubey
* */

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    /*-- Define variable here --*/
    private SensorManager sManager;
    RecyclerView rvList;
    ArrayList<Items> items;
    ListAdapter listAdapter;
    ListPresenter listPresenter;

    /*-- Default activity method --*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        /*-- Initialize adapter --*/
        listPresenter = new ListPresenter(this);
        initialize();
    }

    /*-- Initialize data --*/
    public void initialize() {
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rvList = (RecyclerView)findViewById(R.id.rvList);
        items = new ArrayList<>();
        listAdapter = new ListAdapter(this, items);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvList.setAdapter(listAdapter);
    }

    /*-- Trigger Sensor events --*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        Items item = listPresenter.onSensorResponse(event);
        if (item != null) {
            items.add(0, item);
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /*
        It's a good practice to unregister the sensor when the application hibernates to save battery power.
     */
    protected void onPause() {
        super.onPause();
        sManager.unregisterListener(this);
    }

    /*
    register the sensor listener to listen to the gyroscope sensor, use the
    callbacks defined in this class, and gather the sensor information as quick
    as possible
    */
    protected void onResume() {
        super.onResume();
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
    }
}