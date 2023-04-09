package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityBlindFormBinding;
import com.example.myapplication.dto.Blind;
import com.example.myapplication.tts.SingleTonTTS;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BlindFormActivity extends AppCompatActivity {

    ActivityBlindFormBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser fbUser;
    EditText blindName;
    EditText blindAge;
    TextView blindSex;
    RadioButton blindMan;
    RadioButton blindWoman;
    String uid;


    SingleTonTTS tts;
    private boolean waitD = true;
    private long DOUBLE_CLICK_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlindFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        fbUser = auth.getCurrentUser();

        uid = fbUser.getUid();


        //tts 실행
        tts = tts.getInstance();
        tts.init(getApplicationContext());


        blindName = binding.blindName;
        blindAge = binding.blindAge;
        blindSex = binding.blindSex;

        blindMan = binding.manBox;
        blindWoman = binding.womanBox;


        Button blindFormBtn = binding.blindFormBtn;

        blindFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waitD==true){
                    waitD = false;
                    Thread thread = new Thread(){
                        @Override
                        public void run(){
                            try{
                                sleep(DOUBLE_CLICK_TIME);
                                if(waitD==false){   //single click
                                    waitD=true;
                                    tts.speak("제출");
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
                    storeBlind(); // 시각장애우분들 정보 저장
                }
            }
        });

        blindName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tts.speak("이름을 입력하세요");
                }
            }
        });
        blindAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tts.speak("나이를 입력하세요");
                }
            }
        });
        blindSex.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tts.speak("성별을 선택하세요");
            }
        });
        blindMan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tts.speak("남성");
            }
        });
        blindWoman.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tts.speak("여성");
            }
        });
    }

    private void storeBlind() {
        String gender = "";

        if (blindMan.isChecked()){
            gender = "남성";
        }else if(blindWoman.isChecked()){
            gender = "여성";
        }

        String name = blindName.getText().toString();
        String age = blindAge.getText().toString();


        if (name.isEmpty() || age.isEmpty() || gender.isEmpty()){
            tts.speak("빈칸을 채워주세요");
            Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
            return;
        } // 입력을 안 했을 경우 확인

        Blind blind = new Blind(name, age, gender, uid);

        db.collection("blinds")
                .add(blind)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        tts.speak("제출에 성공하였습니다");
                        switchToMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tts.speak("제출에 실패하였습니다");
                        Toast.makeText(BlindFormActivity.this, "제출에 실패하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void switchToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}