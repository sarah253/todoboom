package com.example.todoboom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Todo_item_Adapter extends RecyclerView.Adapter<Todo_item_Adapter.TodoViewHolder> {
    private ArrayList<Todo> todoList = new ArrayList<>();
    private MainActivity mainActivity;

    Todo_item_Adapter(MainActivity mainActivity)

    {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, final int position) {
        final Todo item = todoList.get(position);
        holder.description.setText(todoList.get(position).getDescription());
        holder.isDone.setChecked(item.getIsDone());
       holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                mainActivity.startnewactivity(position);
               //when clicked open new activity for the task (completed or not completed)
           }
       });
    }

    void setTodoList(ArrayList<Todo> arrayList, boolean dataChanged){
        todoList.clear();
        todoList.addAll(arrayList);
        if(dataChanged){
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount()
    {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        CheckBox isDone;
        ConstraintLayout constraintLayout;
        TodoViewHolder(@NonNull View view) {
            super(view);
            description = view.findViewById(R.id.description);
            isDone = view.findViewById(R.id.is_done);
            constraintLayout = view.findViewById(R.id.main_layout);
        }
    }
}
