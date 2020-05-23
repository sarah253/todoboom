package com.example.todoboom;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button createbutton;
    private RecyclerView recyclerView;
    private Todo_item_Adapter item_adapter;
    private ArrayList<Todo> todoList = new ArrayList<>();
    private static final String list_size = "LIST_SIZE";
    MainActivity mainActivity;
    private boolean launch = true;
    static boolean task_changed = false;
    static boolean task_done = false;
    static boolean task_deleted = false;
    static int current_position = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        createbutton = findViewById(R.id.buttonSubmit);
        editText = findViewById(R.id.editString);
        item_adapter = new Todo_item_Adapter(this);
        item_adapter.setTodoList(Todo.todoList,true);
        recyclerView = findViewById(R.id.todo_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(item_adapter);
        item_adapter.notifyDataSetChanged();
        context = getApplicationContext();
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
                DataManager.getDataManager().addTask(task);
                editText.setText("");
                item_adapter.setTodoList(Todo.todoList,false);
                item_adapter.notifyItemInserted(Todo.todoList.size());
            }
        });
    }

    public void startnewactivity(int position){
        MainActivity.current_position = position;
        if(Todo.todoList.get(position).getIsDone()){
            Intent intent = new Intent(this, CompletedTodo.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, NotCompletedTodo.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        DataManager.getDataManager().getDb().collection("Todo_Items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d("ERROR","failed");
                            return;
                        }
                        assert  queryDocumentSnapshots != null;
                        ArrayList<Todo> todoArrayList = new ArrayList<>();
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Todo task = document.toObject(Todo.class);
                            todoArrayList.add(task);
                        }

                        Todo.todoList.clear();
                        Todo.todoList.addAll(todoArrayList);
                        item_adapter.setTodoList(Todo.todoList,true);
                        if(launch){
                            Log.d("Launch","Todo list size: "+list_size + Todo.todoList.size());
                            launch = false;
                        }
                    }
                });
    }

    private void remove_Task(){
        item_adapter.setTodoList(Todo.todoList, false);
        item_adapter.notifyItemRemoved(current_position);
        item_adapter.notifyItemRangeChanged(current_position, Todo.todoList.size());
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(task_changed){
            item_adapter.notifyItemChanged(current_position);
            task_changed = false;
        }
        if(task_done){
            item_adapter.notifyItemChanged(current_position);
            task_done = false;
        }
        if(task_deleted){
            remove_Task();
            task_deleted = false;
        }
    }

}