package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySendNotificationBinding;
import com.example.myapplication.dto.Blind;
import com.example.myapplication.dto.Driver;
import com.example.myapplication.dto.Notification;
import com.example.myapplication.dto.User;
import com.example.myapplication.fcm.FcmApi;
import com.example.myapplication.fcm.FcmClient;
import com.example.myapplication.fcm.FcmRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ActivitySendNotificationBinding binding;
    Driver driver;
    String fcmToken;
    FirebaseAuth auth;
    FirebaseUser fbUser;
    Blind blind;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        fbUser = auth.getCurrentUser();


        uid = fbUser.getUid();

        Button btnGetData = binding.btnGetData;

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("986521");// 버스 번호로 query 하여 db에서 운전자 정보 가져오는 함수
            }
        });
    }

    private void fcmPostRequest(String token, String gender, String age) {

        Notification notification = new Notification();
        notification.setTitle("신촌오거리 정거장");
        notification.setBody(age + "세 " + gender + " 시각 장애인분이 탑승하실 예정입니다");
        FcmRequest fcmRequest = new FcmRequest();
        fcmRequest.setTo(token);
        fcmRequest.setNotification(notification);

        //"body": "45세 여성 시각 장애인분이 탑승하실 예정입니다."


        FcmClient fcmClient = FcmClient.getInstance();

        if (fcmClient != null){
            FcmApi fcmApi = fcmClient.getFcmApi();

            fcmApi.pushNotification(fcmRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Failed");
                }
            });
        }
    }

    private void fetchData(String busNumber) {
        db.collection("drivers")
                .whereEqualTo("busNum", busNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                driver = document.toObject(Driver.class); // 가져온 정보를 다시 DTO에 담음

                                fcmToken = driver.fcmToken; // DTO에서 fcm Token을 가져옴

                                db.collection("blinds")
                                        .whereEqualTo("uid", uid)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    blind = document.toObject(Blind.class);

                                                    String age = blind.age;
                                                    String username = blind.username;
                                                    String gender = blind.gender;

                                                    fcmPostRequest(fcmToken, gender, age);
                                                }
                                            }
                                        });




                            }
                        } else {
                            System.out.println("정보 가져오기 실패");
                        }
                    }
                });
    }
}