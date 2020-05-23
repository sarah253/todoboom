package com.example.todoboom;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Todo  {

    public static ArrayList<Todo> todoList = new ArrayList<>();
    private String description;
    private boolean isDone;
    private String creation_timestamp;
    private String edit_timestamp;
    private String id;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

    public Todo () {}

    public Todo(String message){
        this.description = message;
        this.isDone = false;
        this.creation_timestamp = simpleDateFormat.format(new Date());
        this.edit_timestamp = this.creation_timestamp;
        this.id = UUID.randomUUID().toString();
        todoList.add(this);
    }

    public void setEditNow(){this.edit_timestamp = simpleDateFormat.format(new Date());}
    //getters
    public boolean getIsDone(){ return this.isDone; }
    public String getDescription() {return description;}
    public String getCreation_timestamp(){return  creation_timestamp;}
    public String getEdit_timestamp(){return edit_timestamp;}
    public String getId(){return id;}
    //setters
    public void setDescription(String new_description){this.description = new_description;}
    public void setIsDone(boolean bool){this.isDone = bool;}

}
