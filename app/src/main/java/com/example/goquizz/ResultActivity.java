package com.example.goquizz;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView numCorrect;

    Button btnComplete, btnReplay, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        numCorrect = (TextView) findViewById(R.id.NumberCorrect);
        btnComplete = (Button) findViewById(R.id.buttonComplete);
        btnReplay = (Button) findViewById(R.id.buttonReplay);
        btnShare = (Button) findViewById(R.id.buttonShare);

        int num = getIntent().getIntExtra("CorrectAnswer", 0);
        String level = getIntent().getStringExtra("savedLevel");
        String topic = getIntent().getStringExtra("savedTopicName");
        int point = getIntent().getIntExtra("Point", 0);
        int totalScore = getIntent().getIntExtra("TotalScore", 0);
        totalScore += point;


        int finalTotalScore = totalScore;
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("TotalScore", finalTotalScore);
                startActivity(intent);
                finish();
            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);

                // Đặt thông tin về cấp độ và lĩnh vực vào Intent mới để truyền cho PlayActivity
                intent.putExtra("savedLevel", level);
                intent.putExtra("savedTopicName", topic);
                intent.putExtra("TotalScore", finalTotalScore);
                startActivity(intent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "Chủ đề: " + topic + "\nSố điểm tại chủ đề này: " + point);
//                Intent shareIntent = Intent.createChooser(intent, null);
                intent.setData(Uri.parse("sms:"));
                startActivity(Intent.createChooser(intent, null));
            }
        });

        numCorrect.setText("" + num);

    }
}