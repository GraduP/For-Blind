package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.tts.SingleTonTTS;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {

    SingleTonTTS tts;
    private long delay = 0;
    private boolean waitD = true;
    private long DOUBLE_CLICK_TIME = 300;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        //tts 실행
        tts = tts.getInstance();
        tts.init(getApplicationContext());

        tts.speak("시각장애인이라면 우측 화면을, 버스기사라면 좌측 화면을 두 번 클릭해주세요");

        Button busLogin = (Button) findViewById(R.id.buslogin);
        Button blindLogin = (Button) findViewById(R.id.blindlogin);

        busLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(waitD==true){
                    waitD = false;
                    Thread thread = new Thread(){
                        @Override
                        public void run(){
                            try{
                                sleep(DOUBLE_CLICK_TIME);
                                if(waitD==false){   //single click
                                    waitD=true;
                                    tts.speak("버스기사");
                                }
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }else{
                    waitD = true;   //double click
                    tts.stop();
                    Intent intent = new Intent(getApplicationContext(), BusFormActivity.class);
                    startActivity(intent);
                }
                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.stop();
                    tts.speak("버스기사");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    Intent intent = new Intent(getApplicationContext(), BusFormActivity.class);
                    startActivity(intent);
                }*/
            }
        });

        blindLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(waitD==true){
                    waitD = false;
                    Thread thread = new Thread(){
                        @Override
                        public void run(){
                            try{
                                sleep(DOUBLE_CLICK_TIME);
                                if(waitD==false){   //single click
                                    waitD=true;
                                    tts.speak("시각장애인");
                                }
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }else{
                    waitD = true;   //double click
                    tts.stop();
                    Intent intent = new Intent(getApplicationContext(), BlindFormActivity.class);
                    startActivity(intent);
                }
                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.stop();
                    tts.speak("시각장애인");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    Intent intent = new Intent(getApplicationContext(), BlindFormActivity.class);
                    startActivity(intent);
                }*/
            }
        });
    }


}
