package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class FinalActivity extends AppCompatActivity {

    TextView txtQues, txtLevel, txtans1, txtans2, txtans3, txtans4, txtCrt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Mapping();

        String Ques = getIntent().getStringExtra("Question");
        String Lev = getIntent().getStringExtra("Level");
        String Ans1 = getIntent().getStringExtra("Answer1");
        String Ans2 = getIntent().getStringExtra("Answer2");
        String Ans3 = getIntent().getStringExtra("Answer3");
        String Ans4 = getIntent().getStringExtra("Answer4");
        String Crt = getIntent().getStringExtra("Correct");

        if (Lev.equals("easy")) Lev = "Dễ";
        else Lev = "Khó";

        txtQues.setText(Ques);
        txtLevel.setText(Lev);
        txtans1.setText(Ans1);
        txtans2.setText(Ans2);
        txtans3.setText(Ans3);
        txtans4.setText(Ans4);
        txtCrt.setText("Đáp án đúng: " + Crt);

    }

    private void Mapping(){
        txtQues = (TextView) findViewById(R.id.textViewQues);
        txtLevel = (TextView) findViewById(R.id.textViewLevel);
        txtans1 = (TextView) findViewById(R.id.textViewans1);
        txtans2 = (TextView) findViewById(R.id.textViewans2);
        txtans3 = (TextView) findViewById(R.id.textViewans3);
        txtans4 = (TextView) findViewById(R.id.textViewans4);
        txtCrt = (TextView) findViewById(R.id.textViewCrt);

    }


}
