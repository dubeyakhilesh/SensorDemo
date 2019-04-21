package com.example.sensorexample.presenter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.example.sensorexample.model.Items;

import java.util.List;

public class ListPresenter {
    Context context;

    public ListPresenter(Context context){
        this.context = context;
    }

    float Rot[]=null; //for gravity rotational data
    //don't use R because android uses that for other stuff
    float I[]=null; //for magnetic rotational data
    float accels[]=new float[3];
    float mags[]=new float[3];
    float[] values = new float[3];

    float azimuth;
    float pitch;
    float roll;
    private long lastTime = 0;
    private float lastX, lastY, lastZ;
    private static final int THRESHOLD = 600; //used to see whether a shake gesture has been detected or not.

    public Items onSensorResponse(SensorEvent event){
        switch (event.sensor.getType()){
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = event.values.clone();
                break;
        }

        if (mags != null && accels != null) {
            Rot = new float[9];
            I= new float[9];
            SensorManager.getRotationMatrix(Rot, I, accels, mags);
            // Correct if screen is in Landscape

            float[] outR = new float[9];
            SensorManager.remapCoordinateSystem(Rot, SensorManager.AXIS_X,SensorManager.AXIS_Z, outR);
            SensorManager.getOrientation(outR, values);

            azimuth = values[0] * 57.2957795f; //looks like we don't need this one
            pitch = values[1] * 57.2957795f;
            roll = values[2] * 57.2957795f;

            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastTime) > 100) {
                long diffTime = (currentTime - lastTime);
                lastTime = currentTime;
                float speed = Math.abs(azimuth + pitch + roll - lastX - lastY - lastZ)/ diffTime * 10000;
                if (speed > THRESHOLD) {
                    mags = null; //retrigger the loop when things are repopulated
                    accels = null; ////retrigger the loop when things are repopulated
                    Items item = new Items(azimuth, pitch, roll);
                    return item;
                }
                lastX = azimuth;
                lastY = pitch;
                lastZ = roll;
            }
        }
        return null;
    }

}
