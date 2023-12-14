package com.example.goquizz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class TopicActivity extends AppCompatActivity {

    Button btnList;

    ListView lsTopic;
    ArrayList<Topic> arrayTopic;
    TopicAdapter adapter;
    Switch swLevel;
    TextView txtTotal;
    int totalScore = 0;
    int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);


        Mapping();

        adapter = new TopicAdapter(this, R.layout.line_topic, arrayTopic); // setup adapter
        lsTopic.setAdapter(adapter); //show ra listview trên màn hình

        int point = getIntent().getIntExtra("TotalScore", 0);

//        // Lấy dữ liệu từ SharePreference
//        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        int savedTotalScore = preferences.getInt("totalScore", 0);
//
//        // Kiểm tra nếu giá trị đã được lưu trước đó
//        if (savedTotalScore != 0) {
//            point = savedTotalScore;
//        }





        txtTotal.setText(point + " pts");


        lsTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position: trả về vị trí click trên listview, bắt đầu từ 0
                if (swLevel.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                    intent.putExtra("TopicName", arrayTopic.get(position).getTen());
                    intent.putExtra("Level", "hard");
                    intent.putExtra("TotalScore", point);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                    intent.putExtra("TopicName", arrayTopic.get(position).getTen());
                    intent.putExtra("Level", "easy");
                    intent.putExtra("TotalScore", point);
                    startActivity(intent);
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListQuestionActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Mapping(){
        swLevel = (Switch) findViewById(R.id.switchLevel);
        lsTopic = (ListView) findViewById(R.id.listviewTopic);
        txtTotal = (TextView) findViewById(R.id.textViewTotal);
        btnList = (Button) findViewById(R.id.buttonList);
        arrayTopic = new ArrayList<>();
        arrayTopic.add(new Topic("Địa lý", "Chủ đề liên quan đến vùng đất, địa hình, dân cư trên Trái Đất", R.drawable.geography));
        arrayTopic.add(new Topic("Lịch sử", "Chủ đề liên quan đến các sự kiện lớn trong việc hình thành lên xã hội hiện tại", R.drawable.history));
        arrayTopic.add(new Topic("Khoa học", "Chủ đề liên quan đến các môn Khoa học, bao gồm cả Khoa học tự nhiên và Khoa học xã hội", R.drawable.science));
        arrayTopic.add(new Topic("Nghệ thuật", "Chủ đề liên quan đến các mô hình nghệ thuật giải trí như: Âm nhạc, Điện ảnh", R.drawable.art));
        arrayTopic.add(new Topic("Công nghệ", "Chủ đề liên quan đến các thông tin về thế giới công nghệ hiện nay, các xu hướng cũng như các sự kiện quan trọng", R.drawable.technology));

    }
}