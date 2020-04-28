package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button createbutton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter item_adapter;
    private ArrayList<Todo> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createbutton = findViewById(R.id.buttonSubmit);
        editText = findViewById(R.id.editString);
        item_adapter = new Todo_item_Adapter(todoList);
        recyclerView = findViewById(R.id.todo_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(item_adapter);
        item_adapter.notifyDataSetChanged();
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editText.getText().toString();
                if (task.equals("")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "can't create empty item!", duration);
                    toast.show();
                    return;
                }
                editText.setText("");
                todoList.add(new Todo(task));
                item_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("Edit_Text", editText.getText());
        outState.putParcelableArrayList("items",(ArrayList<? extends Parcelable>) todoList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getCharSequence("Edit_Text"));
        List<Todo> list = savedInstanceState.getParcelableArrayList("items");
        for (int i = 0 ; i <  list.size() ; i ++){
            Todo taskToAdd = list.get(i);
            todoList.add(taskToAdd);
            item_adapter.notifyDataSetChanged();
        }
    }
}