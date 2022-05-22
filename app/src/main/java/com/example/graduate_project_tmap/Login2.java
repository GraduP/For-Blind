package com.example.graduate_project_tmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        Button busLogin = (Button) findViewById(R.id.buslogin);
        Button blindLogin = (Button) findViewById(R.id.blindlogin);

        busLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BusForm.class);
                startActivity(intent);
            }
        });

        blindLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), BlindForm.class);
                startActivity(intent);
            }
        });
    }

}
