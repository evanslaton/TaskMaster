package com.evanslaton.taskmaster.project;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Project {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String firebaseId;
    protected String title;
//    protected List<String> tasks;

    // Constructor for room
    public Project() {}

    // Constructor
//    @Ignore
    public Project(String title) {
        this.title = title;
        this.firebaseId = "";
//        this.tasks = new ArrayList<String>();
    }

    // Gets the project id
    public long getId() {
        return this.id;
    }

    // Gets the project firebase id
    public String getFirebaseId() {
        return this.firebaseId;
    }

    // Gets the project title
    public String getTitle() {
        return this.title;
    }

    // Gets the project's tasks
//    public List<String> getTasks() {
//        return this.tasks;
//    }

    // Setters
    // Sets the project id
    public void setId(long id) { this.id = id; }

    // Sets the project firebase id
    public void setFirebaseId(String firebaseId) { this.firebaseId = firebaseId; }

    // Sets the project title
    public void setTitle(String title) { this.title = title; }

    // Adds a task to the project's tasks
//    public void setTask(String taskId) { this.tasks.add(taskId); }
}
