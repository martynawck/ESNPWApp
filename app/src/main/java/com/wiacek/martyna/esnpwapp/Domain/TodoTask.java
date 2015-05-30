package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-03-23.
 */
public class TodoTask {

    private int todo_id;
    private String description;
    private int value;
    private int type;

    public TodoTask () { }

    public TodoTask(int todo_id, String description, int value, int type) {

        this.value = value;
        this.description = description;
        this.todo_id = todo_id;
        this.type = type;

    }
    @Override
    public String toString() {
        return description;

    }


    public int getTodo_id(){
        return todo_id;
    }

    public int getType() {
        return type;
    }

    public String getDescription(){
        return description;
    }

    public int getValue() {
        return value;
    }

    public void setTodo_id(int todo_id) { this.todo_id = todo_id; }

    public void setDescription (String description) { this.description = description; }

    public void setValue (int value) { this.value = value; }

    public void setType (int type) { this.type = type; }
}
