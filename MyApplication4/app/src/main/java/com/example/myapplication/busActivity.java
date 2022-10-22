package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bus.Arrival;
import com.example.myapplication.bus.ArrivalAPI;
import com.example.myapplication.bus.ArrivalItem;
import com.example.myapplication.bus.RetrofitArrivalClient;
import com.example.myapplication.station.RetrofitAPI;
import com.example.myapplication.station.RetrofitClient;
import com.example.myapplication.station.Station;
import com.example.myapplication.transfer.TransferItem;
import com.example.myapplication.tts.SingleTonTTS;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class busActivity extends AppCompatActivity {
    private Intent intent;
    private String stationName; //fname
    private String fid; //일치하는 arsID찾기 위해
    private String busNm;
    private String station_key = "uJVPZ36cG4TAmsXg9mpWZHtlod+uxSREmceXmb8+hOU2NDP2G2XcyW4KOua4/PMe+I1P5/MemCn1pNVoNQS8Iw==";
    private String type = "json";
    private String arsId;
    private TransferItem transferlist;
    private String arrmsg1 = "";
    private long time1 = 0;
    private String before1 = "";
    private TextView tv1;
    private CountDownTimer CDT;
    private long delay = 0;
    private String endX;
    private String endY;
    SingleTonTTS tts;

    //arrmsg textview에 띄우기 ㅇ
    //초마다 textview 바꿔주기 ㅇ
    //tts 읽어주기
    //view 1번 클릭시 읽기 2번클릭시 도착

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        View view = findViewById(R.id.busRootView);
        intent = getIntent();
        tts = tts.getInstance();
        tts.init(getApplicationContext());

        transferlist = (TransferItem) intent.getSerializableExtra("transferItem");
        endX = intent.getStringExtra("endX");
        endY = intent.getStringExtra("endY");



        Log.i("transferList" , transferlist.getPathItemList().size() + "ㄱㅐ");
        stationName = transferlist.getPathItemList().get(0).getFname();
        Log.i("stationName" ,stationName);
        fid = transferlist.getPathItemList().get(0).getFid();
        busNm = transferlist.getPathItemList().get(0).getRouteNm();
        Station_InFo(stationName);

        CDT = new CountDownTimer(180 * 1000, 1000 ) {
            public void onTick(long millisUntilFinished) {
                if(time1 < 0){
                    arriveInFo(arsId);
                }
                Log.i("timer 시작", time1 + "분");
                time1--;
                long min1 = time1 / 60;
                long sec1 = time1 % 60;
                tv1 = findViewById(R.id.textView);
                if (!before1.equals("곧 도착")) {
                    tv1.setText(busNm + "번 버스가 " + min1 + "분" + sec1 + "초 후 도착합니다. 현재 " + before1 + " 정류장에 있습니다.");
                } else {
                    tv1.setText(busNm + "번 버스가" + before1 + "합니다.");
                }
                //반복실행할 구문
            }

            public void onFinish() {
                arriveInFo(arsId);
            }
        };
            //마지막에 실행할 구문




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() > delay){//1번클릭
                    delay = System.currentTimeMillis() + 300;
                    tts.speak(tv1.getText().toString());
                    return;
                }

                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    CDT.cancel();// 타이머 종료
                    CDT = null;
                    Intent intent =  new Intent(busActivity.this, PopIn.class);
                    intent.putExtra("endX" , endX);
                    intent.putExtra("endY", endY);
                    intent.putExtra("transferItem", transferlist);
                    startActivity(intent);

                }

            }
        });
    }

    private void Station_InFo(String s) { //도착 예정 버스를 찾기 위하여 arsID를 갖고옴
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        if (retrofitClient != null) {
            RetrofitAPI retrofitAPI = RetrofitClient.getRetrofitAPI();
            retrofitAPI.getData(station_key, s, type).enqueue(new Callback<Station>() {
                @Override
                public void onResponse(Call<Station> call, Response<Station> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        for(int i = 0 ; i < response.body().getMsgBody().getItemList().size(); i++){
                            if(fid.equals(response.body().getMsgBody().getItemList().get(i).getStId()))
                            {
                                arsId = response.body().getMsgBody().getItemList().get(i).getArsId();
                                Log.i("arsId" ,arsId );
                                arriveInFo(arsId);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Station> call, Throwable t) {
                    Toast.makeText(busActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void arriveInFo(String s) { //ArsId
        RetrofitArrivalClient retrofitArrivalClient = RetrofitArrivalClient.getInstance();
        if (retrofitArrivalClient != null) {
            ArrivalAPI arrivalAPI = RetrofitArrivalClient.getArrivalAPI();
            arrivalAPI.getArrival(station_key, s, type).enqueue(new Callback<Arrival>() {
                @Override
                public void onResponse(Call<Arrival> call, Response<Arrival> response) {
                    if (response.isSuccessful()) {
                        for(int i = 0 ; i < response.body().getAMsgBody().getArrivalItemList().size() ; i++){
                            if(busNm.equals(response.body().getAMsgBody().getArrivalItemList().get(i).getRtNm())){
                                arrmsg1 = response.body().getAMsgBody().getArrivalItemList().get(i).getArrmsg1();
                                //arrmsg2 = response.body().getAMsgBody().getArrivalItemList().get(i).getArrmsg2();
                                time1 = response.body().getAMsgBody().getArrivalItemList().get(i).getTraTime1();
                                //time2 = response.body().getAMsgBody().getArrivalItemList().get(i).getTraTime2();
                                if(arrmsg1.trim().equals("곧 도착")){
                                   before1 = arrmsg1;
                                }else{
                                    Pattern p = Pattern.compile("\\[(.*?)\\]");
                                    Matcher m1 = p.matcher(arrmsg1);
                                    before1 = "";
                                    while(m1.find()){
                                        before1 += m1.group(1);
                                    }
                                }
                               /*Pattern p = Pattern.compile("\\[(.*?)\\]");
                                Matcher m2 = p.matcher(arrmsg2);
                                before2 = "";
                                while(m2.find()){
                                    before2 += m2.group(1);
                                }*/
                                CDT.start(); //CountDownTimer 실행
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Arrival> call, Throwable t) {
                    Toast.makeText( busActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK) {
            finish();
            CDT.cancel();
            CDT = null;
            return true;
        }
        else{
            return false;
        }
    }
}