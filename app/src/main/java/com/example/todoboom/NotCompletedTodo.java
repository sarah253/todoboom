package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NotCompletedTodo extends AppCompatActivity {
    int position;
    TextView todo_content;
    TextView editTime;
    TextView createTime;
    EditText editTodo;
    Button DoneButton;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra("position", -1);
        setContentView(R.layout.activity_not_completed_todo);
        todo_content = findViewById(R.id.currentTask);
        createTime = findViewById(R.id.creationTime_1);
        editTodo = findViewById(R.id.editTODO);
        editTime = findViewById(R.id.editTime_1);
        DoneButton = findViewById(R.id.donebutton);
        saveButton = findViewById(R.id.savebutton);
        todo_content.setText(Todo.todoList.get(position).getDescription());
        createTime.setText(String.format("Todo task created: "+Todo.todoList
                .get(position).getCreation_timestamp()));
        editTime.setText(String.format("Modified at:"+Todo.todoList
                .get(position).getEdit_timestamp()));
        editTodo.setText(Todo.todoList.get(position).getDescription());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getDataManager().EditTask(editTodo.getText().toString(),position);
                todo_content.setText(editTodo.getText().toString());
                //notify changes to user
                Toast.makeText( getApplicationContext(),"Changes has been saved",
                        Toast.LENGTH_SHORT).show();
                MainActivity.task_changed = true;
            }
        });
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getDataManager().MarkAsDone(position);
                MainActivity.task_done = true;
                finish();
            }
        });

    }
}
