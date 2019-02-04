package com.evanslaton.taskmaster.project;

import java.util.HashMap;
import java.util.Map;

public class Project {
    protected String firebaseId;
    protected String title;
    protected long numberOfTasks;
    protected Map<String, Boolean> tasks;

    // No args constructor
    public Project() {}

    // Constructor
    public Project(String title) {
        this.title = title;
        this.firebaseId = "";
        this.numberOfTasks = 0;
        this.tasks = new HashMap<String, Boolean>();
    }

    // Getters
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
    public Map<String, Boolean> getTasks() {
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
    public void setTask(Map<String, Boolean> tasks) { this.tasks = tasks; }

    // Adds a task's id to the project's tasks
    public void addTask(String taskId) {
        this.tasks.put(taskId, true);
    }
}
