package com.evanslaton.taskmaster.applicationuser;

import java.util.HashMap;
import java.util.Map;

public class ApplicationUser {
    protected String username;
    protected String firebaseId;
    protected Map<String, Boolean> assignedTasks;
    protected Map<String, Boolean> acceptedTasks;

    // No args constructor
    public ApplicationUser() {}

    // Constructor
    public ApplicationUser(String username) {
        this.username = username;
        this.firebaseId = "";
        this.assignedTasks = new HashMap<>();
        this.acceptedTasks = new HashMap<>();
    }

    // Getters
    // Gets the username
    public String getUsername() {
        return this.username;
    }

    // Gets the user's Firebase Id
    public String getFirebaseId() {
        return this.firebaseId;
    }

    // Gets the list of assigned tasks
    public Map<String, Boolean> getAssignedTasks() {
        return this.assignedTasks;
    }

    // Gets the list of accepted tasks
    public Map<String, Boolean> getAcceptedTasks() {
        return this.acceptedTasks;
    }

    // Setters
    // ets the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Sets the user's Firebase Id
    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    // Sets the list of assigned tasks
    public void setAssignedTasks(HashMap<String, Boolean> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    // Sets the list of accepted tasks
    public void setAcceptedTasks(HashMap<String, Boolean> aacceptedTasks) {
        this.acceptedTasks = acceptedTasks;
    }

    // Adds a task's id to the user's assigned tasks
    public void addAssignedTask(String taskId) {
        this.assignedTasks.put(taskId, true);
    }

    // Removes a task's id from the user's assigned tasks
    public void removeAssignedTask(String taskId) {
        this.assignedTasks.remove(taskId);
    }

    // Adds a task's id to the user's accepted tasks
    public void addAcceptedTask(String taskId) {
           this.acceptedTasks.put(taskId, true);
    }

    // Removes a task's id from the user's accepted tasks
    public void removeAcceptedTask(String taskId) {
        this.acceptedTasks.remove(taskId);
    }

}
