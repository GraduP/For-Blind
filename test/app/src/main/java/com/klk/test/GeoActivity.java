package com.klk.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class GeoActivity extends AppCompatActivity implements SensorEventListener{
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    TextView textGeo;
    TextView textGeo2;
    TextView Step;
    TextView mGeo;
    Task<LocationSettingsResponse> task;
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private int mStep;
    private int stepToGo;
    private Location location1 = null;
    private Location location2 = null;
    private double lat1, lon1, lat2, lon2;
    private double dist, realD;
    private TextToSpeech tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo);
        mStep=0;
        dist=0;
        realD=0;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepCountSensor != null){
            sensorManager.registerListener((SensorEventListener) this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        textGeo = (TextView)findViewById(R.id.idGeo);
        //textGeo2 = (TextView)findViewById(R.id.idGeo2);
        Step = (TextView)findViewById(R.id.idStep);
        //mGeo = (TextView)findViewById(R.id.mGeo);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0] == 1.0f){
                mStep++;
                Step.setText("걸음수: " + mStep);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(12000);
        locationRequest.setFastestInterval(10000);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(GeoActivity.this);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        //checkLocationPermission();
    }


    /*private void checkLocationPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            checkLocationSetting();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION}, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i])) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        checkLocationSetting();
                    } else {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                        builder.setTitle("위치 권한이 필요합니다.");
                        builder.setMessage("[권한] 설정에서 위치 권한을 허용해야 합니다.");
                        builder.setPositiveButton("설정으로 가기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
                        .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        androidx.appcompat.app.AlertDialog alert = builder.create();
                        alert.show();
                    }
                    break;
                }
            }
        }
    }

    private void checkLocationSetting() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        //locationRequest.setFastestInterval(500);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true);
        settingsClient.checkLocationSettings(builder.build())
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(GeoActivity.this);
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    }
                })
                .addOnFailureListener(GeoActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(GeoActivity.this, 2000);
                                } catch (IntentSender.SendIntentException sie) {
                                    //Log.w(TAG, "unable to start resolution for result due to " + sie.getLocalizedMessage());
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                //String errorMessage = "location settings are inadequate, and cannot be fixed here. Fix in Settings.";
                                //Log.e(TAG, errorMessage);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == RESULT_OK) {
                checkLocationSetting();
            } else {
                finish();
            }
        }
    }*/

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(locationResult != null) {
                for (Location location : locationResult.getLocations()) {
                    //textGeo.setText("위도: " + locationResult.getLastLocation().getLongitude() + "경도: " + locationResult.getLastLocation().getLatitude());
                    //textGeo2.setText("위도1: " + location.getLongitude() + " / 경도1: " + location.getLatitude());
                    if (location1 == null) {
                        location1 = location;
                        lat1 = location.getLatitude();
                        lon1 = location.getLongitude();
                    } else {
                        location2 = location;
                        lat2 = location.getLatitude();
                        lon2 = location.getLongitude();

                        stepToGo = (int)(100/(realD/mStep));
                        textGeo.setText("다음 구간까지 " + stepToGo + "걸음 남았습니다");
                        //textGeo.setText(realD + "m 이동하였습니다\n 위도1: " + lat1 + " / 경도1: " + lon1 + "\n 위도2: " + lat2 + " / 경도2: " + lon2);
                        tts.speak(textGeo.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);


                        double theta = lon1 - lon2;
                        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
                        dist = Math.acos(dist);
                        dist = rad2deg(dist);
                        dist = dist * 60 * 1.1515 * 1609.344;
                        realD += dist;
                        //textGeo.setText(realD + "m 이동하였습니다\n 위도1: " + lat1 + " / 경도1: " + lon1 + "\n 위도2: " + lat2 + " / 경도2: " + lon2);
                        //textGeo.setText("위도1: " + lat1 + " / 경도1: " + lon1);
                        //textGeo2.setText("위도2: " + lat2 + " / 경도2: " + lon2);

                        lat1 = lat2;
                        lon1 = lon2;
                    }

                }
            }
            //super.onLocationResult(locationResult);

            //fusedLocationClient.removeLocationUpdates(locationCallback);


        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            //Log.i(TAG, "onLocationAvailability - " + locationAvailability);
        }
    };

    private static double deg2rad(double deg){
        return (deg*Math.PI/180.0);
    }
    private static double rad2deg(double rad){
        return (rad*180/Math.PI);
    }
        /*textGeo = (TextView)findViewById(R.id.idGeo);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            textGeo.setText("위도: " + location.getLatitude() + " / 경도: " + location.getLongitude());
                        } else textGeo.setText("null");
                    }
                });*/

}

