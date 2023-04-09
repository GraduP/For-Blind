package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySignUpBinding;
import com.example.myapplication.dto.User;
import com.example.myapplication.tts.SingleTonTTS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivitySignUpBinding binding;

    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;

    SingleTonTTS tts;
    private long delay = 0;
    private boolean waitD = true;
    private long DOUBLE_CLICK_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // 뷰 바인딩 활용

        //tts 실행
        tts = tts.getInstance();
        tts.init(getApplicationContext());


        mAuth = FirebaseAuth.getInstance();

        Button btnSignUp = binding.btnSignUp;
        TextView txtAlreadyHaveAccount = binding.txtAlreadyHaveAccount;

        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                                    tts.speak("회원가입");
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
                    registerUser(); // 회원가입 기능
                }
                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.speak("회원가입");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    registerUser(); // 회원가입 기능
                }*/
            }
        });


        txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
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
                                    tts.speak("이미 계정이 있으신가요?");
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
                    tts.speak("로그인 화면으로 넘어갑니다");
                    switchToLogin(); // 이미 계정이 있으므로 로그인 페이지로 이동
                }
                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.speak("이미 계정이 있으신가요?");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    tts.speak("로그인 화면으로 넘어갑니다");
                    switchToLogin(); // 이미 계정이 있으므로 로그인 페이지로 이동
                }*/
            }
        });


        if (mAuth.getCurrentUser() != null){
            //1102 이미 로그인 된 경우 코드 추가 - 금예인
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        } // 이미 인증이 되어 있을 시 다음 화면으로


        txtUsername = binding.txtUsername;
        txtEmail = binding.txtEmail;
        txtPassword = binding.txtPassword;

        txtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tts.speak("이름을 입력해주세요");
                }
            }
        });
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tts.speak("이메일을 입력해주세요");
                }
            }
        });
        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tts.speak("비밀번호를 입력해주세요");
                }
            }
        });
    }


    private void registerUser(){

        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(username, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            tts.speak("회원가입에 성공하였습니다");
                                            startLogin2Activity(); // 회원가입 성공 시 메인 화면으로 이동
                                        }
                                    });

                        }else{
                            tts.speak("회원가입에 실패하였습니다");
                            Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    } // 회원가입 기능 구현

    private void startLogin2Activity() {
        Intent intent = new Intent(this, Login2.class);
        startActivity(intent);
        finish();
    }

    private void switchToLogin() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

}