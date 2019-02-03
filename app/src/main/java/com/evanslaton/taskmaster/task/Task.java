package com.evanslaton.taskmaster.task;

public class Task {
    protected String firebaseId;
    protected String projectFirebaseId;
    protected String title;
    protected String state;

    // No args constructor
    public Task() {}

    // Constructor
    public Task(String title, String projectFirebaseId) {
        this.title = title;
        this.firebaseId = "";
        this.projectFirebaseId = projectFirebaseId;
        this.state = "Available";
    }

    // Getters
    // Gets the task's Firebase id
    public String getFirebaseId() {
        return this.firebaseId;
    }

    // Gets the Firebase id of the project that task belongs to
    public String getProjectFirebaseId() {
        return this.projectFirebaseId;
    }

    // Gets the task title
    public String getTitle() {
        return this.title;
    }

    // Gets the task state
    public String getState() {
        return this.state;
    }

    // Setters

    // Sets the task's Firebase id
    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    // Sets the Firebase id of the project the task belongs to (for room DO NOT USE)
    public void setFirebaseProjectId(String projectFirebaseId) {
        this.projectFirebaseId = projectFirebaseId;
    }

    // Sets the task title
    public void setTitle(String title) {
        this.title = title;
    }

    // Sets the task state
    public void setState(String state) {
        this.state = state;
    }
}
