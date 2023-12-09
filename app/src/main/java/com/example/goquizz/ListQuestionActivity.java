package com.example.goquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ListQuestionActivity extends AppCompatActivity {

    Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);

        btnSelect = (Button) findViewById(R.id.buttonSelect);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });

    }
    private void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), btnSelect);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.geography){
                    btnSelect.setText("Địa lý");
                } else if (id == R.id.history){
                    btnSelect.setText("Lịch sử");
                } else if (id == R.id.science){
                    btnSelect.setText("Khoa học");
                } else if (id == R.id.art){
                    btnSelect.setText("Nghệ thuật");
                } else if (id == R.id.techonoly){
                    btnSelect.setText("Công nghệ");
                }

                return false;
            }
        });
        popupMenu.show();
    }
}