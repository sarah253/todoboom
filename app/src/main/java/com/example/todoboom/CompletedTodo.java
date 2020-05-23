package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class CompletedTodo extends AppCompatActivity {
    int position;
    TextView todo_content;
    TextView editTime;
    TextView createTime;
    Button UnDoneButton;
    Button deleteButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);
        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra("position", -1);
        context = this;
        todo_content = findViewById(R.id.doneTask);
        editTime = findViewById(R.id.editTime_2);
        createTime = findViewById(R.id.creationTime_2);
        UnDoneButton = findViewById(R.id.unMarkButton);
        deleteButton = findViewById(R.id.deleteButton);
        todo_content.setText(Todo.todoList.get(position).getDescription());
        createTime.setText(String.format("Todo task created: "+Todo.todoList
                .get(position).getCreation_timestamp()));
        editTime.setText(String.format("Modified at:"+Todo.todoList
                .get(position).getEdit_timestamp()));

        UnDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getDataManager().UnMarkTask(position);
                MainActivity.task_changed = true;
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle(R.string.remove).
                        setMessage(R.string.assurance).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DataManager.getDataManager().deleteTask(position);
                            MainActivity.task_deleted = true;
                            finish();
                    }
                }).create().show();
            }
        });
    }
}
