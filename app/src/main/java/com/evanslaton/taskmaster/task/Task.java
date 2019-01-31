package com.evanslaton.taskmaster.task;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String firebaseId;
    protected long projectId; // Id of the project the task belongs to
    protected String projectFirebaseId;
    protected String title;
    protected String state;

    // Constructor for room
    public Task() {}

    // Constructor
//    @Ignore
    public Task(String title, String projectFirebaseId) {
        this.title = title;
        this.firebaseId = "";
        this.projectFirebaseId = "";
        this.state = "Available";
    }

    // Getters
    // Gets the task id
    public long getId() {
        return this.id;
    }

    // Gets the task's firebase id
    public String getFirebaseId() {
        return this.firebaseId;
    }

    // Gets the id of the project the task belongs to
    public long getProjectId() {
        return this.projectId;
    }

    // Gets the firebase id of the project that task belongs to
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
    // Sets the task id
    public void setId(long id) { this.id = id; }

    // Sets the task's firebase id
    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    // Sets the id of the project the task belongs to (for room DO NOT USE)
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    // Sets the firebase id of the project the task belongs to (for room DO NOT USE)
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
