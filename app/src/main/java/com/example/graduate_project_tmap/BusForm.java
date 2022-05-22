package com.example.graduate_project_tmap;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduate_project_tmap.databinding.ActivitySignInBinding;
import com.example.graduate_project_tmap.databinding.BusformBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BusForm extends AppCompatActivity {

    Map<String, Object> driver;
    BusformBinding binding;
    FirebaseFirestore db;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BusformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        Button busFormBtn = (Button) findViewById(R.id.busFormBtn);

        driver = new HashMap<>();

        busFormBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (binding.busName.getText().toString().isEmpty() && !binding.busage.getText().toString().isEmpty()
                        && !binding.busSex.getText().toString().isEmpty() && !binding.busNum.getText().toString().isEmpty()) {

                    driver.put("username", binding.busName.getText().toString());
                    driver.put("age", binding.busage.getText().toString());
                    driver.put("gender", binding.busSex.getText().toString());
                    driver.put("busNumber", binding.busNum.getText().toString());

                    db.collection("drivers")
                            .add(driver)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "onSuccess: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });

                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(BusForm.this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
