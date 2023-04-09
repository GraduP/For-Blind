package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.transfer.TransferItem;
import com.example.myapplication.tts.SingleTonTTS;

public class PopIn extends AppCompatActivity {
    private long delay = 0;
    private Intent intent;
    private TransferItem transferlist;
    private String endX;
    private String endY;
    private String tName;
    private TextView tNameView;
    SingleTonTTS tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_in);
        View view = findViewById(R.id.popRootView);
        intent = getIntent();
        transferlist = (TransferItem) intent.getSerializableExtra("transferItem");
        endX = intent.getStringExtra("endX");
        endY = intent.getStringExtra("endY");
        tName = transferlist.getPathItemList().get(0).getTname();
        tNameView = findViewById(R.id.tNameView);
        tNameView.setText("내리실 정류장의 이름은 " + tName + " 입니다.");

        transferlist.getPathItemList().remove(0);

        tts = tts.getInstance();
        tts.init(getApplicationContext());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() > delay){//1번클릭
                    delay = System.currentTimeMillis() + 300;
                    tts.speak(tNameView.getText().toString());
                    return;
                }

                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    Intent intent =  new Intent(PopIn.this, GeoActivity.class);
                    intent.putExtra("endX" , endX);
                    intent.putExtra("endY", endY);
                    intent.putExtra("transferItem", transferlist); //이게 대부분이 정보를 갖고 있음
                    startActivity(intent);
                }

            }
        });
    }
}