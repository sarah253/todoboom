package com.example.todoboom;

import com.google.firebase.firestore.FirebaseFirestore;

public class DataManager {
    static  private DataManager dataManager = new DataManager();
    private FirebaseFirestore db;
    private DataManager()
    {
        db = FirebaseFirestore.getInstance();
    }
     public static DataManager getDataManager(){
        return dataManager;
     }

     public FirebaseFirestore getDb(){
        return db;
     }

     public void addTask(String description){
        Todo task = new Todo(description);
        db.collection("Todo_Items").document(task.getId()).set(task);
     }

     public void EditTask(String newDescription, int position){
        Todo.todoList.get(position).setDescription(newDescription);
        Todo.todoList.get(position).setEditNow();
        db.collection("Todo_Items").document(Todo.todoList.get(position).getId())
                .update("description",newDescription,"edit_timestamp",
                        Todo.todoList.get(position).getEdit_timestamp());
    }

    public void MarkAsDone(int position){
        Todo.todoList.get(position).setIsDone(true);
        Todo.todoList.get(position).setEditNow();
        db.collection("Todo_Items").document(Todo.todoList.get(position).getId())
                .update("isDone",true,"edit_timestamp",
                        Todo.todoList.get(position).getEdit_timestamp());
    }

    void UnMarkTask(int position){
        Todo.todoList.get(position).setIsDone(false);
        Todo.todoList.get(position).setEditNow();
        db.collection("Todo_Items").document(Todo.todoList.get(position).getId())
                .update("isDone",false,"edit_timestamp",
                        Todo.todoList.get(position).getEdit_timestamp());
    }

    void deleteTask(int position){
        db.collection("Todo_Items").document(Todo.todoList.get(position).getId())
                .delete();
        Todo.todoList.remove(position);
    }
}
