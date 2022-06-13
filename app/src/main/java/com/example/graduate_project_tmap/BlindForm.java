package com.example.graduate_project_tmap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduate_project_tmap.databinding.BlindformBinding;
import com.example.graduate_project_tmap.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BlindForm extends AppCompatActivity {

    Map<String, Object> user;
    BlindformBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BlindformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        user = new HashMap<>();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(BlindForm.this);
        progressDialog.setTitle("계정 생성 중입니다.");
        progressDialog.setMessage("잠시만 기다려주세요. ");

        Button blindFormBtn = (Button) findViewById(R.id.blindFormBtn);
        blindFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.blindName.getText().toString().isEmpty() && !binding.blindAge.getText().toString().isEmpty()
                        && !binding.blindSex.getText().toString().isEmpty() && !binding.blindEmail.getText().toString().isEmpty()
                        && !binding.blindPassword.getText().toString().isEmpty()) {

                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(binding.blindEmail.getText().toString(), binding.blindPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {

                                        User user = new User(binding.blindName.getText().toString(), binding.blindEmail.getText().toString(),
                                                binding.blindPassword.getText().toString(), binding.blindAge.getText().toString(), binding.blindSex.getText().toString());

                                        String uid = task.getResult().getUser().getUid();
                                        database.getReference().child("Blind").child(uid).setValue(user);


                                        Toast.makeText(BlindForm.this, "회원가입이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(BlindForm.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(BlindForm.this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.blindAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlindForm.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}






