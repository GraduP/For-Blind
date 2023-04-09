package com.example.myapplication;

import static com.example.myapplication.tts.SingleTonTTS.tts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.transfer.RetrofitTransferClient;
import com.example.myapplication.transfer.Transfer;
import com.example.myapplication.transfer.TransferAPI;
import com.example.myapplication.transfer.TransferItem;
import com.example.myapplication.tts.SingleTonTTS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//이 엑티비티에선 메인 엑티비티에서 전송 받은 좌표로 api를 호출하고 listview에 띄워줌
//메인액티비티 3으로 넘어가는 intent는 RecyclerViewAdapter Class에서 선언됨
//서울특별시_정류소정보조회 서비스
public class MainActivity2 extends AppCompatActivity {
    private Intent intent;
    private ArrayList<TransferItem> mylist;
    private RecyclerViewAdapter myAdapter;
    private ArrayList<RecyclerViewItem> Rylist;
    private TransferAPI transferAPI;
    private String Transfer_key = "uJVPZ36cG4TAmsXg9mpWZHtlod+uxSREmceXmb8+hOU2NDP2G2XcyW4KOua4/PMe+I1P5/MemCn1pNVoNQS8Iw=="; //환승시 사용하는 요청키
    private String type = "json"; // 요청타입
    private String endX;; // 연세대앞
    private String endY;
    private String startX; //= "126.9243"; // 홍익대학교 앞
    private String startY; // = "37.5528";
    SingleTonTTS tts;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tts = tts.getInstance();
        tts.init(getApplicationContext());
        intent = getIntent(); //인텐트 신호를 받음
        // 인텐트에서 좌표 추출
        endX = intent.getStringExtra("endX");
        endY = intent.getStringExtra("endY");
        startX = intent.getStringExtra("startX");
        startY = intent.getStringExtra("startY");
        Log.i("start", startX);
        //Button button = findViewById(R.id.button); // 데이터 확인용



        mRecyclerView = findViewById(R.id.recyclerview); // 리사이클러뷰 참조
        RecyclerViewDeco itemDeco = new RecyclerViewDeco(20);
        mRecyclerView.addItemDecoration(itemDeco);
        Rylist = new ArrayList<>(); //리사이클러뷰에 사용되는 ArrayList
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this.getApplicationContext()); //리사이클러뷰에서 사용되는 레이아웃 매니저
        mRecyclerView.setLayoutManager(linearLayoutManager); //셋 매니저
        myAdapter = new RecyclerViewAdapter(Rylist); //뷰와 데이터를 연결해주는 어댑터 데이터로 Rylist가 사용
        myAdapter.setEndX(endX);
        myAdapter.setEndY(endY);
        mRecyclerView.setAdapter(myAdapter); //어뎁터 셋팅

        Transfer_Info(); //환승 정보를 받아옴

    }
    private void addItem(String time, String station , ArrayList<TransferItem> transferItems){ //rylist에 데이터 넣기 위한 함수 transferInfo 내에서 선언됨
        RecyclerViewItem item = new RecyclerViewItem();

        item.setTime(time);
        item.setStation(station);
        item.setTransferItems(transferItems);

        Rylist.add(item);
    }


    void Transfer_Info() {
        RetrofitTransferClient retrofitTransferClient = RetrofitTransferClient.getInstance();
        if (retrofitTransferClient != null) {
            transferAPI = RetrofitTransferClient.getTransferAPI();
            Log.e("start", startX);
            Log.e("start", startY);
            transferAPI.getTransfer(Transfer_key, startX, startY, endX, endY, type).enqueue(new Callback<Transfer>() {
                @Override
                public void onResponse(@NonNull Call<Transfer> call,@NonNull Response<Transfer> response) {
                    if(response.isSuccessful()) {
                        mylist =  response.body().getTmsgBody().getItemList();
                        Collections.sort(mylist); //TransferItem 클래스 내에 comparable to 선언 sort 환승이 적은 순으로 정렬하고 같을 때는 시간순으로 정렬
                        for(int i = 0 ; i <mylist.size() ; i++) { //받아온 itemlist의 크기 만큼 반복
                            if(mylist.get(i).getPathItemList().size() <= 3) { //환승 2번까지만
                                ArrayList<TransferItem> temp = new ArrayList<>(); //데이터 추가에 사용됨
                                temp.add(mylist.get(i)); //get(i)는 i번째 itemlist로 모든 환승 정보를 갖고 있음
                                String time = String.valueOf(mylist.get(i).getTime()); // 수정필요
                                String Fname = mylist.get(i).getPathItemList().get(0).getFname(); //수정필요
                                addItem(time , Fname , temp) ;
                            }
                        }

                        myAdapter.notifyDataSetChanged(); //어뎁터에 있는 데이터 셋이 바뀌면 화면에 바뀐 데이터를 띄워줌
                        if(myAdapter.getItemCount() == 0){
                            tts.speak("검색 결과가 없습니다.다시 검색해주세요");
                            finish();
                        }

                    }
                    else{
                        Log.d("onResponse 실패",  "onResponse 실패");
                        Toast.makeText(MainActivity2.this ,"onResponse" , Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<Transfer> call, Throwable t) {

                }
            });
        }
    }
}