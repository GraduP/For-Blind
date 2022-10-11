package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.transfer.TransferItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GeoActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private int nStep;  //측정 걸음 수 (node to node) -> 보폭 계산에 사용
    private int stepToGo;   //남은 걸음 수

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private double lat, lon; //위도, 경도 초기값 (출발지 위도, 경도)
    private Location location1 = null;  //이전 위치
    private Location location2 = null;  //현재 위치
    private double lat1, lon1;  //이전 위치 위도, 경도
    private double lat2, lon2;  //현재 위치 위도, 경도

    TMapView tmapview;
    String result;  //경로 정보 원본
    org.json.simple.JSONObject obj;
    ArrayList<com.example.myapplication.geo.datalist> list;
    com.example.myapplication.geo.datalist item;
    String str = "Point";
    String fName = "일반보행자도로";
    String carA = "22";
    private int totalIndex = 0; //목적지까지 총 인덱스 수
    private int curIndex = -1;
    private int nextIndex = 0;
    private double nextLat;
    private double nextLon;

    double distance = 0; //이동해야할 거리 (TMAP API를 통해 계산한 누적값)
    double realD = 0; //실제 이동한 거리 (하버사인 공식을 통해 계산한 누적값)

    String roadType;    //Line - roadType (도로타입정보 / 차도 인도 분리 O, 차도 인도 분리 X, 차량 통행 불가 등)
    String facilityType;    //Line - facilityType (구간시설물 / 일반보행자도로, 횡단보도, 계단 등)
    String facilityName;
    String turnType;    //Point - turnType (직진, 우회전, 좌회전, 출발지, 목적지, 육교, 계단, 경사로 등)

    String guideText;   //방향 및 걸음 수 안내
    String roadText;    //도로 타입 안내
    String infoText;    //시설물 안내
    String turnText;    //회전 정보 안내
    TextView guide;
    TextView road;
    TextView info;
    ImageButton refresh;

    Intent intent;
    private TransferItem transferlist;

    //거리 확인을 위한 임의 텍스트뷰
    TextView cur;
    TextView next;
    TextView dist;
    TextView nodeend;

    //이전 엑티비티에서 값을 받아옴

    //transferlist = (TransferItem) intent.getSerializableExtra("transferItem"); //transferlist추출 -> 이게 주 데이터


    ArrayList<String> station = new ArrayList<>();  //정류장 좌표

    String end_lon; //목적지 좌표
    String end_lat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo);
        guide = findViewById(R.id.guide);
        road = findViewById(R.id.road);
        info = findViewById(R.id.info);
        refresh = findViewById(R.id.refresh);

        //거리 확인을 위한 임의 텍스트뷰
        cur = findViewById(R.id.cur);
        next = findViewById(R.id.next);
        dist = findViewById(R.id.distance);
        nodeend = findViewById(R.id.nodeend);



        intent = getIntent();
       // end_lon = intent.getStringExtra("endX");
       // end_lat = intent.getStringExtra("endY");

        transferlist = (TransferItem) intent.getSerializableExtra("transferItem"); //transferlist추출 -> 이게 주 데이터
        end_lon = transferlist.getPathItemList().get(0).getFx();
        end_lat = transferlist.getPathItemList().get(0).getFy();
        for (int i = 0; i < transferlist.getPathItemList().size(); i++) {
            Log.d("버스번호", transferlist.getPathItemList().get(i).getRouteNm());
            Log.d("itemTransferItem fname", transferlist.getPathItemList().get(i).getFname());
            Log.d("fx", String.valueOf(transferlist.getPathItemList().get(i).getFx()));
            Log.d("fy", String.valueOf(transferlist.getPathItemList().get(i).getFy()));
        }

        //걸음 수 측정
        nStep = 0;
        stepToGo = 0;

        Log.i("거리계산", String.valueOf(calD(35.883557, 35.883530, 128.663303, 128.663289)));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepCountSensor != null) {
            sensorManager.registerListener((SensorEventListener) this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        //위치 받아오기 (1초마다 한 번씩)
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(GeoActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }













        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if(location != null){
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        });
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        //티맵 api
        tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("l7xx98299ca0fd2d41859cb522c8efcfc7c0");


        //새로고침
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(distance!=0){    //출발 이후라고  가정, 목적지가 있는 경우
                    guideText = "약 "+(int)calD(nextLat, lat2, nextLon, lon2)/(realD/nStep)+"걸음 남았습니다.";
                    guide.setText(guideText);
                    Log.i("걸음", guideText);
                }
            }
        });

    }

    //센서를 통한 걸음 수 측정 / nStep에 누적
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0f) {
                nStep++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //TMAP API를 통해 경로 정보 받아오기
    String getPathData() {
        String queryUrl = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result";
        JSONObject responseJson = null;
        JsonObject params = new JsonObject();

        try {
            URL url = new URL(queryUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("appKey", "l7xx98299ca0fd2d41859cb522c8efcfc7c0");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((conn.getOutputStream())));
            JsonObject commands = new JsonObject();
            JsonArray jsonArray = new JsonArray();

            params.addProperty("startX", lon);
            params.addProperty("startY", lat);
            params.addProperty("endX", end_lon);
            params.addProperty("endY", end_lat);
            // params.addProperty("endX", "128.664652");
            //params.addProperty("endY", "35.883026");
            params.addProperty("reqCoordType", "WGS84GEO");
            params.addProperty("resCoordType", "WGS84GEO");
            params.addProperty("startName", "출발지");
            params.addProperty("endName", "도착지");
            jsonArray.add(params);
            commands.add("data", params);

            bw.write(params.toString());
            bw.flush();
            bw.close();

            Log.i("request", params.toString());


            int responseCode = conn.getResponseCode();
            Log.i("API", String.valueOf(responseCode));
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                responseJson = new JSONObject(sb.toString());


                return sb.toString();
            }
            //에러 코드 미완성
        } catch (MalformedURLException e) {
            return queryUrl;
        } catch (IOException e) {
            return queryUrl;
        } catch (JSONException e) {
            return queryUrl;
        }
        return queryUrl;
    }

    //경로 정보 파싱
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = getPathData();
                JSONParser jsonParser = new JSONParser();

                Log.i("test", result);

                //item에 정보를 담아 list에 추가
                try {
                    org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonParser.parse(result);
                    JSONArray parse_item = (JSONArray) jsonObj.get("features");
                    totalIndex = parse_item.size();
                    list = new ArrayList<com.example.myapplication.geo.datalist>();
                    for(int i=0; i<parse_item.size(); i++){
                        obj = (org.json.simple.JSONObject) parse_item.get(i);
                        org.json.simple.JSONObject sample = (org.json.simple.JSONObject) obj.get("geometry");
                        org.json.simple.JSONObject geo = (org.json.simple.JSONObject) obj.get("properties");
                        item = new com.example.myapplication.geo.datalist();
                        item.setType((String) sample.get("type"));
                        if(sample.get("type").equals(str)){    //point data인 경우
                            item.setIndex(Integer.parseInt(String.valueOf(geo.get("index"))));
                            item.setDescription((String) geo.get("description"));
                            item.setTurnType(String.valueOf(geo.get("turnType")));

                            JSONArray coor = (JSONArray) sample.get("coordinates");
                            item.setLongitude((Double) coor.get(0));
                            item.setLatitude((Double) coor.get(1));

                            Log.i("type", (String) sample.get("type"));
                            Log.i("index", String.valueOf(geo.get("index")));
                            Log.i("경도", String.valueOf(coor.get(0)));
                            Log.i("위도", String.valueOf(coor.get(1)));
                            Log.i("turntype", (String.valueOf(geo.get("turnType"))));
                        }
                        else{   //line data인 경우
                            item.setIndex(Integer.parseInt(String.valueOf(geo.get("index"))));
                            item.setDescription((String) geo.get("description"));

                            item.setDistance(String.valueOf(geo.get("distance")));
                            item.setRoadType(String.valueOf(geo.get("roadType")));
                            item.setFacilityType((String) geo.get("facilityType"));

                            Log.i("type", (String) sample.get("type"));
                            Log.i("index", String.valueOf(geo.get("index")));
                            Log.i("road", String.valueOf(geo.get("roadType")));
                            Log.i("facility", String.valueOf(geo.get("facilityName")));
                            Log.i("거리", String.valueOf(geo.get("distance") + "m"));
                        }
                        list.add(item);
                    }

                    curIndex = 0;
                    nextIndex = 0;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //이동거리 계산 및 노드 도착 확인
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(locationResult != null) {
                for (Location location : locationResult.getLocations()) {

                    if (location1 == null && curIndex==0) {    //출발 (이전 위치가 null인 상태)
                        Log.i("출발", "start");

                        location1 = location;   //location1 = 출발지의 위도, 경도
                        lat1 = lat;
                        lon1 = lon;

                        curIndex = 0;   //첫 번째 point(노드)
                        nextIndex = 1;
                        distance = 0;

                        roadType = list.get(curIndex+1).getRoadType();
                        facilityType = list.get(curIndex+1).getFacilityType();

                        Log.i("현재 노드", String.valueOf(curIndex));

                        //다음 point(노드) 찾기
                        while(!list.get(nextIndex).getType().equals(str)){
                            nextIndex+=1;
                        }

                        Log.i("다음 노드", String.valueOf(nextIndex));

                        //다음 point의 좌표
                        nextLat = list.get(nextIndex).getLatitude();
                        nextLon = list.get(nextIndex).getLongitude();


                        cur.setText(lat1 + ", " + lon1);
                        next.setText(nextLat + ", " + nextLon);


                        //현재 노드에서 다음 노드까지 경로 길이 (line data 중 distance)
                        for(int i=curIndex+1; i<nextIndex; i++){
                            distance += Double.valueOf(list.get(i).getDistance());
                        }


                        dist.setText("실제: "+distance + " 계산: " + calD(lat1, nextLat, lon1, nextLon));


                        //출발지에서의 안내 - (거리 / 성별 평균 보폭)으로 걸음 수만 안내 => 방향은 추후
                        //성별이 남자인지, 여자인지에 따라 값 변화 필요

                        //n 걸음 이동
                        guideText = "약 "+(int)(distance/0.65)+"걸음 이동하세요";
                        Log.i("걸음", guideText);
                        guide.setText(guideText);

                        if(roadType.equals(carA)){ //차도와 인도가 분리되어 있지 않은 경우
                            roadText = "차량 통행 가능 구간입니다";
                            Log.i("도로 타입", roadText);
                            road.setText(roadText);
                        }

                        switch (facilityType) { //일반보행자도로가 아닌 경우 (터널, 육교, 횡단보도, 계단 등)
                            case "1":
                                facilityName = "교량";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "2":
                                facilityName = "터널";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "3":
                                facilityName = "고가도로";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "11":
                                facilityName = "일반보행자도로";
                                infoText = facilityName+"입니다";
                                break;
                            case "12":
                                facilityName = "육교";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "14":
                                facilityName = "지하보도";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "15":
                                facilityName = "횡단보도";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "16":
                                facilityName = "대형시설물이동통로";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            case "17":
                                facilityName = "계단";
                                infoText = facilityName+"이니 유의하시기 바랍니다";
                                break;
                            default:
                                infoText = " ";
                        }
                        Log.i("시설물 정보", infoText);
                        info.setText(infoText);

                    }
                    else {
                        location2 = location;   //location2 = 실시간 현재 위치의 위도, 경도
                        lat2 = location.getLatitude();
                        lon2 = location.getLongitude();


                        nodeend.setText("가는 중 ~");


                        //다음 노드 도착 시 (5m 이내 접근)
                        Log.i("노드까지의 거리", String.valueOf(calD(nextLat, lat2, nextLon, lon2)));
                        if(calD(nextLat, lat2, nextLon, lon2)<5){
                            //진동
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);

                            nodeend.setText("노드 도착");

                            if(nextIndex+1 == totalIndex){ //마지막 노드에 도착한 경우 //activity 전환
                                Log.i("도착", "end");
                                guideText = "도착 완료";
                            }
                            else{   //경유지인 경우
                                distance = 0;
                                curIndex = nextIndex;
                                roadType = list.get(curIndex+1).getRoadType();
                                facilityType = list.get(curIndex+1).getFacilityType();
                                turnType = list.get(curIndex).getTurnType();

                                //다음 point(노드) 찾기
                                nextIndex = nextIndex+1;
                                while(!list.get(nextIndex).getType().equals(str)){
                                    nextIndex+=1;
                                }
                                Log.i("다음 노드", String.valueOf(nextIndex));

                                //다음 point의 좌표
                                nextLat = list.get(nextIndex).getLatitude();
                                nextLon = list.get(nextIndex).getLongitude();

                                //현재 노드에서 다음 노드까지 경로 길이 (line data 중 distance)
                                for(int i=curIndex+1; i<nextIndex; i++){
                                    distance += Double.valueOf(list.get(i).getDistance());
                                }

                                //turnType
                                switch (turnType){
                                    case "11":
                                        turnText = "직진 후 ";
                                        break;
                                    case "12":
                                        turnText = "좌회전 후 ";
                                        break;
                                    case "13":
                                        turnText = "우회전 후 ";
                                        break;
                                    case "14":
                                        turnText = "유턴 후 ";
                                        break;
                                    case "16":
                                        turnText = "8시 방향 좌회전 후 ";
                                        break;
                                    case "17":
                                        turnText = "10시 방향 좌회전 후 ";
                                        break;
                                    case "18":
                                        turnText = "2시 방향 우회전 후 ";
                                        break;
                                    case "19":
                                        turnText = "4시 방향 우회전 후 ";
                                        break;
                                    case "125":
                                        turnText = "육교 진입 후 ";
                                        break;
                                    case "126":
                                        turnText = "지하보도 진입 후 ";
                                        break;
                                    case "127":
                                        turnText = "계단 진입 후 ";
                                        break;
                                    case "128":
                                        turnText = "경사로 진입 후 ";
                                        break;
                                    case "129":
                                        turnText = "계단 및 경사로 진입 후 ";
                                        break;
                                    case "211":
                                        turnText = "정면 횡단보도에서 ";
                                        break;
                                    case "212":
                                        turnText = "좌측 횡단보도에서 ";
                                        break;
                                    case "213":
                                        turnText = "우측 횡단보도에서 ";
                                        break;
                                    case "214":
                                        turnText = "8시 방향 횡단보도에서 ";
                                        break;
                                    case "215":
                                        turnText = "10시 방향 횡단보도에서 ";
                                        break;
                                    case "216":
                                        turnText = "2시 방향 횡단보도에서 ";
                                        break;
                                    case "217":
                                        turnText = "4시 방향 횡단보도에서 ";
                                        break;
                                    default:
                                        turnText = " ";
                                }

                                //n 걸음 이동
                                stepToGo = (int)(distance/(realD/nStep));
                                guideText = turnText + "약 "+stepToGo+"걸음 이동하세요";
                                realD = 0;  //노드 간 이동거리 초기화
                                nStep = 0;  //노드 간 걸음 수 초기화
                                Log.i("걸음", guideText);
                                guide.setText(guideText);

                                if(roadType.equals(carA)){ //차도와 인도가 분리되어 있지 않은 경우
                                    roadText = "차량 통행 가능 구간입니다";
                                    Log.i("도로 타입", roadText);
                                    road.setText(roadText);
                                }

                                switch (facilityType) { //일반보행자도로가 아닌 경우 (터널, 육교, 횡단보도, 계단 등)
                                    case "1":
                                        facilityName = "교량";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "2":
                                        facilityName = "터널";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "3":
                                        facilityName = "고가도로";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "11":
                                        facilityName = "일반보행자도로";
                                        infoText = facilityName+"입니다";
                                        break;
                                    case "12":
                                        facilityName = "육교";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "14":
                                        facilityName = "지하보도";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "15":
                                        facilityName = "횡단보도";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "16":
                                        facilityName = "대형시설물이동통로";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    case "17":
                                        facilityName = "계단";
                                        infoText = facilityName+"이니 유의하시기 바랍니다";
                                        break;
                                    default:
                                        infoText = " ";
                                }
                                Log.i("시설물 정보", infoText);
                                info.setText(infoText);
                            }
                            guide.setText(guideText);

                        }

                        realD += calD(lat1, lat2, lon1, lon2);  //실제 이동한 거리 누적


                        cur.setText(lat1 + ", " + lon1);
                        next.setText(nextLat + ", " + nextLon);
                        dist.setText("실제: "+distance + " 계산: " + calD(lat2, nextLat, lon2, nextLon));


                        lat1 = lat2;
                        lon1 = lon2;
                    }
                }
            }

        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            //Log.i(TAG, "onLocationAvailability - " + locationAvailability);
        }
    };


    //하버사인 공식으로 두 점 사이 거리 계산
    private static double calD(double lat1, double lat2, double lon1, double lon2) {
        double dist;
        double radius = 6371;
        double toRadian = Math.PI/180;

        double deltaLatitude = Math.abs(lat2 - lat1)*toRadian;
        double deltaLongitude = Math.abs(lon2 - lon1)*toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude/2);
        double sinDeltaLng = Math.sin(deltaLongitude/2);
        double squareRoot = Math.sqrt(sinDeltaLat*sinDeltaLat+Math.cos(lat1*toRadian)*Math.cos(lat2*toRadian)*sinDeltaLng*sinDeltaLng);
        dist = 1000*2*radius*Math.asin(squareRoot);

        //원래 썼던 식 !!
        /*double theta = lon1 - lon2;
        double dist;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        private static double deg2rad(double deg){
            return (deg*Math.PI/180.0);
        }
        private static double rad2deg(double rad){
            return (rad*180/Math.PI);
        }*/

        return dist;
    }



}
















