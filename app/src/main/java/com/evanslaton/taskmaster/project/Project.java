package com.evanslaton.taskmaster.project;

import java.util.HashMap;
import java.util.Map;

//import androidx.room.Entity;
//import androidx.room.Ignore;
//import androidx.room.PrimaryKey;

//@Entity
public class Project {
//    @PrimaryKey(autoGenerate = true)
//    protected long id;
    protected String firebaseId;
    protected String title;
    protected long numberOfTasks;
    protected Map<String, String> tasks;

    // Constructor for room
    public Project() {}

    // Constructor
//    @Ignore
    public Project(String title) {
        this.title = title;
        this.firebaseId = "";
        this.numberOfTasks = 0;
        this.tasks = new HashMap<String, String>();
    }

//    public long getId() {return this.id;}

    // Gets the project firebase id
    public String getFirebaseId() {
        return this.firebaseId;
    }

    // Gets the project title
    public String getTitle() {
        return this.title;
    }

    // Gets the number of tasks the project has
    public long getNumberOfTasks() {
        return this.numberOfTasks;
    }

    // Gets the project's tasks
    public Map<String, String> getTasks() {
        return this.tasks;
    }

    // Setters
    // Sets the project firebase id
    public void setFirebaseId(String firebaseId) { this.firebaseId = firebaseId; }

    // Sets the project title
    public void setTitle(String title) { this.title = title; }

    // Increments the number of tasks a project has
    public void setNumberOfTasks(long numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    // Sets the tasks
    public void setTask(Map<String, String> tasks) { this.tasks = tasks; }

    // Adds a task's id to the project's tasks
    public void addTask(String taskId) {
        long numberOfTasks = this.getNumberOfTasks();
        numberOfTasks++;
        this.setNumberOfTasks(numberOfTasks);
        String numberOfTasksString = String.valueOf(this.getNumberOfTasks());
        this.tasks.put(numberOfTasksString, taskId); }
}
