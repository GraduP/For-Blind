package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.transfer.TransferItem;

public class MainActivity3 extends AppCompatActivity {
    private TextView fnametext;
    private TextView timetext;
    private Intent intent;
    private String time;
    private String fname;
    private TransferItem transferlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        intent = getIntent(); //엑티비티 2에서 받아올 정보를 위해 선언된 인텐트
        time = intent.getStringExtra("time"); //시간추출
        fname = intent.getStringExtra("station"); //정류장이름 추출
        transferlist = (TransferItem) intent.getSerializableExtra("transferItem"); //transferlist추출 -> 이게 주 데이터
        timetext = findViewById(R.id.textView3);
        fnametext = findViewById(R.id.textView4);

        timetext.setText(time + "을 버스타고 이동합니다.");
        fnametext.setText(fname + "에서 탑승합니다.");

        Log.d("transferItem 내부 time", String.valueOf(transferlist.getTime())); //데이터 확인용
        for (int i = 0; i < transferlist.getPathItemList().size(); i++) {
                Log.d("버스번호", transferlist.getPathItemList().get(i).getRouteNm());
                Log.d("itemTransferItem fname", transferlist.getPathItemList().get(i).getFname());
                Log.d("fx", String.valueOf(transferlist.getPathItemList().get(i).getFx()));
                Log.d("fy", String.valueOf(transferlist.getPathItemList().get(i).getFy()));
        }
    }
}