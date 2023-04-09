package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityBusFormBinding;
import com.example.myapplication.dto.Driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class BusFormActivity extends AppCompatActivity {

    private String token;
    ActivityBusFormBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getFcmToken(); // Firebase Cloud Messaging 토큰 받기

        db = FirebaseFirestore.getInstance();

        Button busFormBtn = binding.busFormBtn;

        busFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeDrivers(); // 운전 기사분들의 정보 저장
            }
        });

    }

    private void storeDrivers() {
        EditText busName = binding.busName;
        EditText busage = binding.busage;
        EditText busNum = binding.busNum;
        RadioButton busMan = binding.manBox;
        RadioButton busWoman = binding.womanBox;

        String gender = "";

        if (busMan.isChecked()){
            gender = "남성";
        }else if(busWoman.isChecked()){
            gender = "여성";
        }

        String name = busName.getText().toString();
        String age = busage.getText().toString();
        String busNumber = busNum.getText().toString();

        if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || busNumber.isEmpty()){
            Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
            return;
        } // 입력을 안 했을 경우 확인

        Driver driver = new Driver(name, age, gender, busNumber, token);

        db.collection("drivers")
                .add(driver)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      swithToMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BusFormActivity.this, "실패하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println( "Fetching FCM registration token failed");
                            return;
                        }


                        token = task.getResult(); // token을 여기서 받음


                        System.out.println(token); // test용 출력
                        Toast.makeText(BusFormActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void swithToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}