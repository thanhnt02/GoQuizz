package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    TextView txtQuestion;
    RadioGroup rdg;
    RadioButton rdanswer1, rdanswer2, rdanswer3, rdanswer4;
    Button btnAnswer;
    String topicName, Level, crtQues;
    int currentQuestion = 0;
    int correct = 0;
    int point = 0;

    // hai biến có chức năng lưu lại topicName và level người dùng chọn (dùng để set sự kiện khi người dùng ấn Chơi Lại
    private String savedTopicName;
    private String savedLevel;
    int totalScore;

    List<QuestionList> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Mapping();

        totalScore = getIntent().getIntExtra("TotalScore", 0);

        savedTopicName = getIntent().getStringExtra("savedTopicName");
        savedLevel = getIntent().getStringExtra("savedLevel");

        if (savedTopicName == null || savedLevel == null){
            topicName = getIntent().getStringExtra("TopicName");
            if (topicName.equals("Địa lý")) topicName = "geography";
            else if (topicName.equals("Lịch sử")) topicName = "history";
            else if (topicName.equals("Khoa học")) topicName = "science";
            else if (topicName.equals("Nghệ thuật")) topicName = "art";
            else if (topicName.equals("Công nghệ")) topicName = "technology";
            Level = getIntent().getStringExtra("Level");
        }
        else{
            topicName = savedTopicName;
            Level = savedLevel;
        }


        //load toàn bộ câu hỏi
        loadAllQuestions();
        // load câu hỏi đầu tiên lên trước, theo topicName và Level đã chọn
        setQuestionScreen(currentQuestion);

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdanswer1.isChecked() && !rdanswer2.isChecked() && !rdanswer3.isChecked() && !rdanswer4.isChecked()){
                    Toast.makeText(PlayActivity.this, "Hãy chọn một đáp án", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (rdanswer1.isChecked()) crtQues = rdanswer1.getText().toString();
                    else if (rdanswer2.isChecked()) crtQues = rdanswer2.getText().toString();
                    else if (rdanswer3.isChecked()) crtQues = rdanswer3.getText().toString();
                    else if (rdanswer4.isChecked()) crtQues = rdanswer4.getText().toString();
                    if (crtQues.equals(questionList.get(currentQuestion).getCorrect())){
                        //correct
                        correct++;
                        if (Level.equals("easy")) point += 1;
                        else if (Level.equals("hard")) point += 2;
                    }else {
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("CorrectAnswer", correct);
                        intent.putExtra("savedTopicName", topicName);
                        intent.putExtra("savedLevel", Level);
                        intent.putExtra("Point", point);
                        intent.putExtra("TotalScore", totalScore);
                        startActivity(intent);
                        finish();
                    }
                    //load next question if any
                    if (currentQuestion<questionList.size() - 1){
                        currentQuestion++;
                        setQuestionScreen(currentQuestion);
                        rdg.clearCheck();
                    }else{
                        //game over
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("CorrectAnswer", correct);
                        intent.putExtra("savedTopicName", topicName);
                        intent.putExtra("savedLevel", Level);
                        intent.putExtra("Point", point);
                        intent.putExtra("TotalScore", totalScore);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

    }

    private void setQuestionScreen(int number){
        txtQuestion.setText(questionList.get(number).getQuestion());
        rdanswer1.setText(questionList.get(number).getAnswer1());
        rdanswer2.setText(questionList.get(number).getAnswer2());
        rdanswer3.setText(questionList.get(number).getAnswer3());
        rdanswer4.setText(questionList.get(number).getAnswer4());
    }

    // sau khi load được file json, đi vào trong file để load câu hỏi và đáp án
    private void loadAllQuestions(){
        questionList = new ArrayList<>();
        // load toàn bộ question vào trong một string, đặt tên là jsonStr
        String jsonStr = loadJSONFromAsset("questionBank.json");
        //sắp xếp chúng thành một list
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject categoryObject = jsonObj.getJSONObject(topicName);
            JSONArray questions = categoryObject.getJSONArray(Level);
            for (int i = 0; i < questions.length(); i++){
                JSONObject question = questions.getJSONObject(i);
                String questionString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");

                questionList.add(new QuestionList(
                        questionString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString
                ));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    // load file json từ thư mục asset đã tạo sẵn
    private String loadJSONFromAsset (String file){
        String json = "";
        try{
            InputStream is = getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }

    private void Mapping(){
        txtQuestion =   (TextView) findViewById(R.id.textViewQuestion);
        rdg =           (RadioGroup) findViewById(R.id.radioGroup);
        rdanswer1 =     (RadioButton) findViewById(R.id.answer1);
        rdanswer2 =     (RadioButton) findViewById(R.id.answer2);
        rdanswer3 =     (RadioButton) findViewById(R.id.answer3);
        rdanswer4 =     (RadioButton) findViewById(R.id.answer4);
        btnAnswer =     (Button) findViewById(R.id.buttonAnswer);
    }

}