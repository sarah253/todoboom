package com.example.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Todo_item_Adapter extends RecyclerView.Adapter<Todo_item_Adapter.TodoViewHolder> {
    private ArrayList<Todo> todoList;

    public Todo_item_Adapter(ArrayList<Todo> list)
    {
        this.todoList = list;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int position) {
        final Todo item = todoList.get(position);
        holder.description.setText(todoList.get(position).getMessage());
        holder.isDone.setChecked(item.getisDone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.isDone.isChecked()){
                    item.setIdDone();
                    holder.isDone.setChecked(true);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(holder.itemView.getContext(),
                            "TODO " +  item.getMessage() +" is now DONE. BOOM!!", duration);
                    toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        CheckBox isDone;
        TodoViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.description);
            isDone = view.findViewById(R.id.is_done);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.remove).setMessage(R.string.assurance);
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            todoList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),todoList.size());
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(v.getContext(),
                                    "Item  " +  description.getText() +" was removed!!", duration);
                            toast.show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    AlertDialog alertdialog = builder.create();
                    alertdialog.show();
                    return false;
                }
            });
        }
    }

}
