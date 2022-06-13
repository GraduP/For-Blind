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

import com.example.graduate_project_tmap.databinding.BusformBinding;
import com.example.graduate_project_tmap.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BusForm extends AppCompatActivity {

    Map<String, Object> driver;
    BusformBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = BusformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(BusForm.this);
        progressDialog.setTitle("계정 생성 중입니다.");
        progressDialog.setMessage("잠시만 기다려주세요. ");

        Button busFormBtn = (Button) findViewById(R.id.busFormBtn);

        driver = new HashMap<>();

        busFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.busName.getText().toString().isEmpty() && !binding.busage.getText().toString().isEmpty()
                        && !binding.busSex.getText().toString().isEmpty() && !binding.busEmail.getText().toString().isEmpty()
                        && !binding.busPassword.getText().toString().isEmpty() && !binding.busNum.getText().toString().isEmpty()) {

                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(binding.busEmail.getText().toString(), binding.busPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {

                                        User user = new User(binding.busName.getText().toString(), binding.busEmail.getText().toString(),
                                                binding.busPassword.getText().toString(), binding.busNum.getText().toString(), binding.busage.getText().toString(), binding.busSex.getText().toString());

                                        String uid = task.getResult().getUser().getUid();
                                        database.getReference().child("Driver").child(uid).setValue(user);


                                        Toast.makeText(BusForm.this, "회원가입이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(BusForm.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(BusForm.this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.busAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusForm.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
