package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.tts.SingleTonTTS;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    ActivityLoginBinding binding;

    SingleTonTTS tts;
    private long delay = 0;

    EditText txtEmail;
    EditText txtPassword;


    private boolean waitD = true;
    private long DOUBLE_CLICK_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tts 실행
        tts = tts.getInstance();
        tts.init(getApplicationContext());

        txtEmail = binding.txtEmail;
        txtPassword = binding.txtPassword;

        Button btnSignIn = binding.btnSignIn;
        TextView txtClickSignup = binding.txtClickSignUp;

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            showMainActivity();
        } // 인증이 되어 있을 시 화면 이동

        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                                    tts.speak("로그인");
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
                    authenticateUser(); // 계정이 있는 지 확인 후 로그인 승인
                }

                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.speak("로그인");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    authenticateUser(); // 계정이 있는 지 확인 후 로그인 승인
                }*/
            }
        });

        txtClickSignup.setOnClickListener(new View.OnClickListener() {
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
                                    tts.speak("회원가입 하기");
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
                    tts.speak("회원가입 화면으로 넘어갑니다");
                    switchToRegister(); // 계정이 없을 시 회원가입 페이지로 이동
                }
                /*if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.speak("회원가입 하기");
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    tts.speak("회원가입 화면으로 넘어갑니다");
                    switchToRegister(); // 계정이 없을 시 회원가입 페이지로 이동
                }*/
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


    private void authenticateUser() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            tts.speak("빈칸을 채워주세요");
            Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
            return;
        } // 입력을 안 했을 경우 확인

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            tts.speak("로그인에 성공하였습니다");
                            showMainActivity(); // 로그인 성공 시 메인 화면으로 이동
                        } else {
                            tts.speak("로그인에 실패하였습니다");
                            Toast.makeText(SignInActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToRegister() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}