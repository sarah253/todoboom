package com.example.todoboom;

import com.google.gson.Gson;
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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button createbutton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter item_adapter;
    private ArrayList<Todo> todoList = new ArrayList<>();
    private static final String list_size = "LIST_SIZE";
    private SharedPreferences sp;

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
        //get todo list
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        todoList.clear();
        getTodoList();
    }
    private void getTodoList(){
        Gson gson = new Gson();
        int size = sp.getInt(list_size,0);
        for(int i =0; i < size;i++){
            String stored_item = sp.getString("place"+i,null);
            Todo item = gson.fromJson(stored_item,Todo.class);
            todoList.add(item);
            item_adapter.notifyDataSetChanged();
        }
    }
    private void saveTodoList(){
        Gson gson = new Gson();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt(list_size,todoList.size()).apply();
        for(int i = 0; i < todoList.size();i++){
            prefs.edit().putString("place"+i, gson.toJson(todoList.get(i))).apply();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("Edit_Text", editText.getText());
        saveTodoList();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getCharSequence("Edit_Text"));

    }
}