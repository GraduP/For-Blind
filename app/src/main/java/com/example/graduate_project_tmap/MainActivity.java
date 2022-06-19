package com.example.graduate_project_tmap;

import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduate_project_tmap.bus.arrival.Arrival;
import com.example.graduate_project_tmap.bus.arrival.ArrivalItem;
import com.example.graduate_project_tmap.bus.arrival.RetrofitArrivalClient;
import com.example.graduate_project_tmap.bus.station.RetrofitAPI;
import com.example.graduate_project_tmap.bus.station.RetrofitClient;
import com.example.graduate_project_tmap.bus.station.Station;
import com.example.graduate_project_tmap.bus.station.StationItem;
import com.example.graduate_project_tmap.bus.transfer.PathItem;
import com.example.graduate_project_tmap.bus.transfer.RetrofitTransferClient;
import com.example.graduate_project_tmap.bus.transfer.Transfer;
import com.example.graduate_project_tmap.bus.transfer.TransferItem;
import com.example.graduate_project_tmap.routes.Route;
import com.example.graduate_project_tmap.routes.RouteApi;
import com.example.graduate_project_tmap.routes.RouteClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    public List<StationItem> startStationItems; //startstation 정보를 받아오는  list (ArsId정보를 포함)
    private List<TransferItem> mTransferItems; // (routeNm=버스 번호 , fid = 타야하는 버스 정류소 id  = arrivalItems의 stid 와 비교해서 도착 시간을 갖고올 때 사용, Tname 하차지를 갖고 있음)
    private List<ArrivalItem> arrivalItems; // arrmsg1의 정보를 갖고 있는 list (버스의 도착시간 정보를 갖고옴)
    private TextView station_message; //TextView
    String Transfer_key = "uJVPZ36cG4TAmsXg9mpWZHtlod+uxSREmceXmb8+hOU2NDP2G2XcyW4KOua4/PMe+I1P5/MemCn1pNVoNQS8Iw=="; //환승시 사용하는 요청키 (Transfer_Info)에서 사용
    String Station_key = "uJVPZ36cG4TAmsXg9mpWZHtlod+uxSREmceXmb8+hOU2NDP2G2XcyW4KOua4/PMe+I1P5/MemCn1pNVoNQS8Iw=="; // 정류소 고유아이디와 도착정보를 갖고 오는데 사용하는 요청키 (Station_InFo , arriveInfo)
    String type = "json"; // 요청타입
    String targetfName;// 타야하는 정류장 이름
    String targetfId; // 타야하는 정류장의 고유 번호"113000422";
    String targetArsId;// 타야하는 버스 노선의 고유 번호
    String targetRouteNm; // 타야하는 버스 노선의 이름
    String targetArrival; // 타야하는 버스의 도착 예정 시간
    String API_Key = "l7xxa43b1576b23c4f758752e05efa62f36c";
    String startX = "126.9252620559"; // 홍대입구역
    String startY = "37.5577221581";
    String endX = "126.9773320672"; // 연세대앞
    String endY = "37.5722264424";


    private RetrofitAPI retrofitAPI;
    private com.example.graduate_project_tmap.bus.transfer.TransferAPI TransferAPI;
    private com.example.graduate_project_tmap.bus.arrival.ArrivalAPI ArrivalAPI;
    private RouteApi routeApi;

    TMapView tMapView = null;
    TMapGpsManager tMapGPS = null;

    private static final int REQUEST_CODE_SPEECH_INPUT = 10;
    TextView mTextTv;
    ImageButton mVoiceBtn;
    Button mTransferBtn;
    Button mRouteBtn;
    Button mPathBtn;
    TMapPoint Destination_Point;
    TMapPoint stationPoint;
    TMapMarkerItem startItem;

    String result;
    JSONObject obj;
    ArrayList<Datalist> list;
    Datalist item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mTextTv = findViewById(R.id.textTv);
        mVoiceBtn = findViewById(R.id.voiceBtn);
        mTransferBtn = findViewById(R.id.transferBtn);
        mRouteBtn = findViewById(R.id.route);
        mPathBtn = findViewById(R.id.path);


        showTmap();
        forGps();

        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mTextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathGuide();
            }
        });

        mTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transfer_InFo();
            }
        });

        mRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteInfo();
            }
        });

        mPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTheResult();
            }
        });

    }

    void Transfer_InFo() {

        TMapPoint p1 = tMapView.getLocationPoint();
        TMapPoint p2 = Destination_Point;


        RetrofitTransferClient retrofitTransferClient = RetrofitTransferClient.getInstance();
        if (retrofitTransferClient != null) {
            TransferAPI = retrofitTransferClient.getTransferAPI();
            TransferAPI.getTransfer(Transfer_key,String.valueOf(p1.getLongitude()), String.valueOf(p1.getLatitude()), String.valueOf(p2.getLongitude()), String.valueOf(p2.getLatitude()), type).enqueue(new Callback<Transfer>() { //비동기식으로 API를 호출함
                @Override
                public void onResponse(Call<Transfer> call, Response<Transfer> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        List<TransferItem> transferItems = Optional.ofNullable(response.body().getTmsgBody().getItemList()).orElseGet(null);
                        List<PathItem> pathItemList = Optional.ofNullable(transferItems.get(0).getPathItemList()).orElseGet(null);

                        List<Double> fx = pathItemList.stream().map(pathItem -> pathItem.getFx()).collect(Collectors.toList());
                        List<Double> fy = pathItemList.stream().map(pathItem -> pathItem.getFy()).collect(Collectors.toList());
                        List<String> fName = pathItemList.stream().map(pathItem -> pathItem.getFname()).collect(Collectors.toList());
                        List<String> busNum = pathItemList.stream().map(pathItem -> pathItem.getRouteNm()).collect(Collectors.toList());
                        List<Double> tx = pathItemList.stream().map(pathItem -> pathItem.getTx()).collect(Collectors.toList());
                        List<Double> ty = pathItemList.stream().map(pathItem -> pathItem.getTy()).collect(Collectors.toList());
                        List<String> tName = pathItemList.stream().map(pathItem -> pathItem.getTname()).collect(Collectors.toList());

                        stationPoint = new TMapPoint(fy.get(0), fx.get(0));

                        for (int i = 0; i < tx.size(); i++) {
                            TMapMarkerItem destItem = new TMapMarkerItem();


                            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.markerline_yellow);

                            destItem.setIcon(bitmap2);
                            destItem.setPosition(0.5f, 1.0f);
                            destItem.setName(tName.get(i));

                            destItem.setCanShowCallout(true);
                            destItem.setCalloutTitle(tName.get(i));
                            destItem.setAutoCalloutVisible(false);
                            destItem.setTMapPoint(new TMapPoint(ty.get(i), tx.get(i)));

                            tMapView.addMarkerItem("transfer" + i, destItem);
                        }


                        for (int i = 0; i < fx.size(); i++) {

                            startItem = new TMapMarkerItem();


                            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.markerline_blue);

                            startItem.setIcon(bitmap1);
                            startItem.setPosition(0.5f, 1.0f);
                            startItem.setName(fName.get(i));

                            startItem.setCanShowCallout(true);
                            startItem.setCalloutTitle(fName.get(i) + " -> " + busNum.get(i));
                            startItem.setAutoCalloutVisible(false);
                            startItem.setTMapPoint(new TMapPoint(fy.get(i), fx.get(i)));

                            tMapView.addMarkerItem("start" + i, startItem);

                        }

                        for (int i = 0; i < fName.size(); i++){
                            Station_InFo(fName.get(i));
                        }

                    }
                }

                @Override
                public void onFailure(Call<Transfer> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Station_InFo(String s) {


        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        if (retrofitClient != null) {
            retrofitAPI = RetrofitClient.getRetrofitAPI();
            retrofitAPI.getData(Station_key, s, type).enqueue(new Callback<Station>() {
                @Override
                public void onResponse(Call<Station> call, Response<Station> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<String> targetArgsId = response.body().getMsgBody().getItemList().stream().map(stationItem -> stationItem.getArsId()).collect(Collectors.toList());
                        for (String target : targetArgsId) {
                            arriveInFo(target);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Station> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void arriveInFo(String s) { //ArsId


        RetrofitArrivalClient retrofitArrivalClient = RetrofitArrivalClient.getInstance();
        if (retrofitArrivalClient != null) {
            ArrivalAPI = RetrofitArrivalClient.getArrivalAPI();
            ArrivalAPI.getArrival(Station_key, s, type).enqueue(new Callback<Arrival>() {
                @Override
                public void onResponse(Call<Arrival> call, Response<Arrival> response) {
                    if (response.isSuccessful() && response.body() != null) {


                        List<ArrivalItem> arrivalItemList = Optional.ofNullable(response.body().getAMsgBody().getArrivalItemList()).orElseGet(ArrayList::new);


                        List<String> arrivalMessages = arrivalItemList.stream().map(arrivalItem -> arrivalItem.getArrmsg1()).collect(Collectors.toList());
                        Log.d(TAG, arrivalMessages.toString());
                        if (!arrivalMessages.isEmpty()) {
                            String message = arrivalMessages.get(0);
                            startItem.setCalloutSubTitle(message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Arrival> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void RouteInfo() { //ArsId

        RouteClient routeClient = RouteClient.getInstance();

        if (routeClient != null) {
            routeApi = RouteClient.getRouteApi();
            routeApi.getRoutes("1", startX, startY, endX, endY, "출발지", "도착지").enqueue(new Callback<Route>() {
                @Override
                public void onResponse(Call<Route> call, Response<Route> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getType());
                    }
                }

                @Override
                public void onFailure(Call<Route> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private void forGps() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        tMapGPS = new TMapGpsManager(this);
        tMapGPS.setMinTime(1000);
        tMapGPS.setMinDistance(5);
        tMapGPS.setProvider(TMapGpsManager.NETWORK_PROVIDER);
        tMapGPS.setProvider(TMapGpsManager.GPS_PROVIDER);
        tMapGPS.OpenGps();

    }

    private void showTmap() {
        // T Map View
        tMapView = new TMapView(this);

        // API Key
        tMapView.setSKTMapApiKey(API_Key);

        tMapView.setHttpsMode(true);

        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        linearLayoutTmap.addView(tMapView);
    }


    @Override
    public void onLocationChange(Location location) {

        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
        TMapMarkerItem markerItem = new TMapMarkerItem();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.markerline_orange);

        markerItem.setIcon(bitmap);
        markerItem.setTMapPoint(new TMapPoint((double) location.getLatitude(), (double) location.getLongitude()));
        markerItem.setPosition(0.5f, 1.0f);
        tMapView.addMarkerItem("My location", markerItem);

    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "목적지를 말해주세요");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    mTextTv.setText(result.get(0));

                    Geocoder geocoder = new Geocoder(MainActivity.this);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(result.get(0), 1);
                        Destination_Point = new TMapPoint(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
        }
    }


    public void pathGuide() {

        TMapPoint point1 = tMapView.getLocationPoint();
        TMapPoint point2 = Destination_Point;


        TMapData tMapData = new TMapData();


        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, point1, point2, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(Color.RED);
                tMapView.addTMapPath(tMapPolyLine);
            }
        });


        Bitmap startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poi_start);
        Bitmap endBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poi_end);
        tMapView.setTMapPathIcon(null, endBitmap);

    }

    String getPathData() {
        TMapPoint p1 = tMapView.getLocationPoint();
        TMapPoint p2 = Destination_Point;
        String queryUrl = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result";
        org.json.JSONObject responseJson = null;
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

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((conn.getOutputStream())))) {
                JsonObject commands = new JsonObject();
                JsonArray jsonArray = new JsonArray();

                params.addProperty("startX", String.valueOf(p1.getLongitude()));
                params.addProperty("startY", String.valueOf(p1.getLatitude()));
                params.addProperty("endX", String.valueOf(stationPoint.getLongitude()));
                params.addProperty("endY", String.valueOf(stationPoint.getLatitude()));
                params.addProperty("reqCoordType", "WGS84GEO");
                params.addProperty("resCoordType", "EPSG3857");
                params.addProperty("startName", "출발지");
                params.addProperty("endName", "도착지");
                jsonArray.add(params);
                commands.add("data", params);

            /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(commands);*/

                //리퀘스트 파라미터
                Log.i("API", params.toString());

                bw.write(params.toString());
                bw.flush();
                bw.close();
            }


            int responseCode = conn.getResponseCode();
            Log.i("API", String.valueOf(responseCode));
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                responseJson = new org.json.JSONObject(sb.toString());
                //리스폰스
                Log.i("API", sb.toString());

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

    void getTheResult() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("thread", "thread is running");
                result = getPathData();
                JSONParser jsonParser = new JSONParser();
                try {
                    org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonParser.parse(result);
                    org.json.simple.JSONArray parse_item = (JSONArray) jsonObj.get("features");
                    Log.i("item", String.valueOf(parse_item.size()));
                    list = new ArrayList<Datalist>();
                    for (int i = 0; i < parse_item.size(); i++) {
                        obj = (org.json.simple.JSONObject) parse_item.get(i);
                        org.json.simple.JSONObject geo = (org.json.simple.JSONObject) obj.get("properties");
                        item = new Datalist();
                        item.setIndex(String.valueOf(geo.get("index")));
                        item.setDescription((String) geo.get("description"));
                        //Log.i("item", String.valueOf(geo.get("index")));
                        //Log.i("item", (String) geo.get("description"));
                        list.add(item);
                    }
                    //org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parse_item.get(0);
                    //Object value = obj.get("type");
                    for (int i = 0; i < parse_item.size(); i++) {
                        if(String.valueOf(list.get(i).description).length() > 10) {
                            Log.i("path item", String.valueOf(list.get(i).description));
                        }
                    }
                    for (int i = 0; i < parse_item.size(); i++) {
                        Log.i("all item", String.valueOf(list.get(i).description));
                    }

                    //Log.i("tag", (String) value);

                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

