package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    ListView lsTopic;
    ArrayList<Topic> arrayTopic;
    TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        lsTopic = (ListView) findViewById(R.id.listviewTopic);
        arrayTopic = new ArrayList<>();
        arrayTopic.add(new Topic("Địa lý", "Chủ đề liên quan đến vùng đất, địa hình, dân cư trên Trái Đất", R.drawable.geography));
        arrayTopic.add(new Topic("Lịch sử", "Chủ đề liên quan đến các sự kiện lớn trong việc hình thành lên xã hội hiện tại", R.drawable.history));
        arrayTopic.add(new Topic("Khoa học", "Chủ đề liên quan đến các môn Khoa học, bao gồm cả Khoa học tự nhiên và Khoa học xã hội", R.drawable.science));
        arrayTopic.add(new Topic("Nghệ thuật", "Chủ đề liên quan đến các mô hình nghệ thuật giải trí như: Âm nhạc, Điện ảnh", R.drawable.art));
        arrayTopic.add(new Topic("Công nghệ", "Chủ đề liên quan đến các thông tin về thế giới công nghệ hiện nay, các xu hướng cũng như các sự kiện quan trọng", R.drawable.technology));

        adapter = new TopicAdapter(this, R.layout.line_topic, arrayTopic);
        lsTopic.setAdapter(adapter);

    }
}