package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListQuestionActivity extends AppCompatActivity {

    Button btnSelect;
    ListView lsQuestion;
    ArrayList<String> arrayQuestion, arrayLevel, arrayAnswer1, arrayAnswer2, arrayAnswer3, arrayAnswer4, arrayCorrect;

    List<QuestionList> questionList;
    String topicName, Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);

        btnSelect = (Button) findViewById(R.id.buttonSelect);
        lsQuestion = (ListView) findViewById(R.id.listviewQuestion);
        arrayQuestion = new ArrayList<>();
        arrayLevel = new ArrayList<>();
        arrayAnswer1 = new ArrayList<>();
        arrayAnswer2 = new ArrayList<>();
        arrayAnswer3 = new ArrayList<>();
        arrayAnswer4 = new ArrayList<>();
        arrayCorrect = new ArrayList<>();

        ShowAllQuestion();

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                arrayQuestion
        );

        lsQuestion.setAdapter(adapter);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });

        lsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position: là biến trả về vị trí của mảng, bắt đầu từ 0
                Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
                // Gửi toàn bộ dữ kiện về Câu hỏi, level, các đáp án, đáp án đúng qua màn hình cuối cùng
                intent.putExtra("Question", arrayQuestion.get(position));
                intent.putExtra("Level", arrayLevel.get(position));
                intent.putExtra("Answer1", arrayAnswer1.get(position));
                intent.putExtra("Answer2", arrayAnswer2.get(position));
                intent.putExtra("Answer3", arrayAnswer3.get(position));
                intent.putExtra("Answer4", arrayAnswer4.get(position));
                intent.putExtra("Correct", arrayCorrect.get(position));
                startActivity(intent);
            }
        });

    }
    private void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), btnSelect);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                arrayQuestion.clear();
                arrayLevel.clear();
                arrayAnswer1.clear();
                arrayAnswer2.clear();
                arrayAnswer3.clear();
                arrayAnswer4.clear();
                arrayCorrect.clear();

                int id = item.getItemId();
                if (id == R.id.geography){
                    btnSelect.setText("Địa lý");
                    loadAllQuestions("geography", "easy");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("easy");
                        insertArray(i);
                    }
                    loadAllQuestions("geography", "hard");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("hard");
                        insertArray(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arrayQuestion
                    );

                    lsQuestion.setAdapter(adapter);
                } else if (id == R.id.history){
                    btnSelect.setText("Lịch sử");
                    loadAllQuestions("history", "easy");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("easy");
                        insertArray(i);
                    }
                    loadAllQuestions("history", "hard");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("hard");
                        insertArray(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arrayQuestion
                    );

                    lsQuestion.setAdapter(adapter);
                } else if (id == R.id.science){
                    btnSelect.setText("Khoa học");
                    loadAllQuestions("science", "easy");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("easy");
                        insertArray(i);
                    }
                    loadAllQuestions("science", "hard");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("hard");
                        insertArray(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arrayQuestion
                    );

                    lsQuestion.setAdapter(adapter);
                } else if (id == R.id.art){
                    btnSelect.setText("Nghệ thuật");
                    loadAllQuestions("art", "easy");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("easy");
                        insertArray(i);
                    }
                    loadAllQuestions("art", "hard");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("hard");
                        insertArray(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arrayQuestion
                    );

                    lsQuestion.setAdapter(adapter);
                } else if (id == R.id.techonoly){
                    btnSelect.setText("Công nghệ");
                    loadAllQuestions("technology", "easy");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("easy");
                        insertArray(i);
                    }
                    loadAllQuestions("technology", "hard");
                    for (int i = 0; i < questionList.size(); i++){
                        arrayLevel.add("hard");
                        insertArray(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            arrayQuestion
                    );

                    lsQuestion.setAdapter(adapter);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private void loadAllQuestions(String topicName, String Level){
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

    private void ShowAllQuestion(){
        loadAllQuestions("geography", "easy");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("easy");
            insertArray(i);
        }
        loadAllQuestions("geography", "hard");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("hard");
            insertArray(i);
        }
        loadAllQuestions("history", "easy");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("easy");
            insertArray(i);
        }
        loadAllQuestions("history", "hard");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("hard");
            insertArray(i);
        }
        loadAllQuestions("science", "easy");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("easy");
            insertArray(i);
        }
        loadAllQuestions("science", "hard");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("hard");
            insertArray(i);
        }
        loadAllQuestions("art", "easy");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("easy");
            insertArray(i);
        }
        loadAllQuestions("art", "hard");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("hard");
            insertArray(i);
        }
        loadAllQuestions("technology", "easy");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("easy");
            insertArray(i);
        }
        loadAllQuestions("technology", "hard");
        for (int i = 0; i < questionList.size(); i++){
            arrayLevel.add("hard");
            insertArray(i);
        }
    }

    private void insertArray(int i){
        arrayQuestion.add(questionList.get(i).getQuestion());
        arrayAnswer1.add(questionList.get(i).getAnswer1());
        arrayAnswer2.add(questionList.get(i).getAnswer2());
        arrayAnswer3.add(questionList.get(i).getAnswer3());
        arrayAnswer4.add(questionList.get(i).getAnswer4());
        arrayCorrect.add(questionList.get(i).getCorrect());
    }
}