package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.search.ItemsEntity;
import com.example.myapplication.search.RetrofitSearchClient;
import com.example.myapplication.search.Search;
import com.example.myapplication.search.SearchAPI;
import com.example.myapplication.tts.SingleTonTTS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//이 액티비티에선 stt로 검색하여서 다음 액티비티로 좌표 전송
//추가할 것 stt
//
//http://api.vworld.kr/req/search?request=search&query=홍익대학교&type=place&format=json&errorformat=json&key=080DB290-90C8-3D7F-92D1-4394E4B023CC
//받고 싶은 정보 title , point x , y
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION = 1;

    /*검색 API에 사용되는 값*/
    private String search = "search"; //search
    private String text = "연세대"; //장소명
    private String type = "place"; //place
    private String format = "json"; //json
    private String errorformat = "json"; //json
    private String bbox = "126.76487604016523,37.428067807,127.18416090045505,37.70130441174812"; //서울지역내에서만 검색
    private String key = "080DB290-90C8-3D7F-92D1-4394E4B023CC"; //080DB290-90C8-3D7F-92D1-4394E4B023CC
    private ArrayList<ItemsEntity> item;  //검색 API 결과를 받아들일 어레이리스트
    private SearchAPI searchAPI; //검색 API 호출에 사용하는 클라이언트

    private String startX; // 홍익대학교 앞
    private String startY;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    private RecyclerView mRecyclerView;

    private TextView sttText; //STT 검색 결과
    private ImageButton sttButton; //STT 검색 버튼

    private Intent RecognizeIntent; //SST를 위한 인텐트
    private SpeechRecognizer mRecognizer;

    SingleTonTTS tts;
    private SoundPool soundPool; //btn 사운드
    int soundPlay;
    private ArrayList<SearchRecyclerViewItem> Rylist; //리사이클러뷰에 사용할 아이템
    private SearchRecyclerViewAdapter myAdapter; //리사이클러뷰 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
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

                startX = String.valueOf(location.getLongitude());
                startY = String.valueOf(location.getLatitude());

                Log.e("??", startX);
            }
        });

        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }// 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크

        tts = tts.getInstance();
        tts.init(getApplicationContext());


        sttText = findViewById(R.id.STTText);
        sttButton = findViewById(R.id.STTButton);
        tts.speak("화면 상단의 마이크 버튼을 클릭하여" + sttText.getText());

        RecognizeIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //recognizeIntent 생성 stt시 사용
        RecognizeIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        RecognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        mRecyclerView = findViewById(R.id.searchView);

        RecyclerViewDeco itemDeco = new RecyclerViewDeco(10);
        mRecyclerView.addItemDecoration(itemDeco);



        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC , 0);
        soundPlay = soundPool.load(this, R.raw.sound, 1);


        sttButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
                soundPool.play(soundPlay,1f,1f,0,0,1f);
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);// 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(RecognizeIntent); // 듣기 시작
            }
        });

    }

    private void addItem(String title, String endX , String endY){ //rylist에 데이터 넣기 위한 함수 transferInfo 내에서 선언됨
        SearchRecyclerViewItem item = new SearchRecyclerViewItem();

        item.setTitle(title);
        item.setX(endX);
        item.setY(endY);
        Rylist.add(item);
    }



    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
            tts.stop();
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출

        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {// 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간이 초과되었습니다.";
                    break;
                default:
                    message = "버튼을 다시 눌러서 말씀해주세요";
                    break;
            }
            tts.speak(message);
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            soundPool.play(soundPlay,1f,1f,0,0,1f);
            Rylist = new ArrayList<>(); //리사이클러뷰에 사용되는 ArrayList
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); //셋 매니저
            myAdapter = new SearchRecyclerViewAdapter(Rylist); //뷰와 데이터를 연결해주는 어댑터 데이터로 Rylist가 사용
            myAdapter.setStartX(startX);
            myAdapter.setStartY(startY);
            mRecyclerView.setAdapter(myAdapter);

            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                    sttText.setText(matches.get(i) + "로 검색한 결과입니다.");
                    text = matches.get(i);
            }
            tts.speak(text + "로 검색합니다");

            text = text.replaceAll(" ", "");
            //어뎁터 셋팅

            SearchAPI();
           //Log.d("text" , text);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

            // 향후 이벤트를 추가하기 위해 예약
        }
    };


    void SearchAPI() {
        RetrofitSearchClient retrofitSearchClient = RetrofitSearchClient.getInstance();
        if (retrofitSearchClient != null) {
            searchAPI = RetrofitSearchClient.getSearchAPI();
            searchAPI.getSearch(search, text, type, format, errorformat, bbox,key).enqueue(new Callback<Search>() {
                @Override
                public void onResponse(@NonNull Call<Search> call, @NonNull Response<Search> response) {
                    if (response.isSuccessful()) {
                        String status = response.body().getResponse().getStatus().trim();
                        if (status.equals("OK")) {
                            item = response.body().getResponse().getResult().getItems();
                            for (int i = 0; i < item.size(); i++) {
                                String title = item.get(i).getTitle();
                                String x = item.get(i).getPoint().getX();
                                String y = item.get(i).getPoint().getY();
                                //Log.d("items" , title + x + " "+ y +" " + road);
                                addItem(title, x, y );
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                        else if(status.equals("NOT_FOUND")){
                            tts.speak("검색 결과가 없습니다.");
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "onResponse", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Search> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "network failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}