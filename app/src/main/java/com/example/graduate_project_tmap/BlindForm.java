package com.example.graduate_project_tmap;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduate_project_tmap.databinding.BlindformBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class BlindForm extends AppCompatActivity {

    Map<String, Object> user;
    BlindformBinding binding;
    FirebaseFirestore db;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BlindformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        Button blindFormBtn = (Button) findViewById(R.id.blindFormBtn);
        blindFormBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!binding.blindName.getText().toString().isEmpty() && !binding.blindAge.getText().toString().isEmpty() && !binding.blindSex.getText().toString().isEmpty()) {
                    user.put("username", binding.blindName.getText().toString());
                    user.put("age", binding.blindAge.getText().toString());
                    user.put("gender", binding.blindSex.getText().toString());

                    db.collection("users")
                            .add(user)
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
                    Toast.makeText(BlindForm.this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
