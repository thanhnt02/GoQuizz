package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnStart = (Button) findViewById(R.id.buttonStart);

        int point = getIntent().getIntExtra("TotalScore", 0);
        if (point == 0){
            SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int savedTotalScore = preferences.getInt("totalScore", 0);

            // Kiểm tra nếu giá trị đã được lưu trước đó
            if (savedTotalScore != 0) {
                point = savedTotalScore;
            }
        }

        // Lưu vào trong SharePreference
        SharedPreferences preferencess = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencess.edit();
        editor.putInt("totalScore", point);
        editor.commit();

        int finalPoint = point;
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, TopicActivity.class);
                intent.putExtra("TotalScore", finalPoint);
                startActivity(intent);
            }
        });

    }
}