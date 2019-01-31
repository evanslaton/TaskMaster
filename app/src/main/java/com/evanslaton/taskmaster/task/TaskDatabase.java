package com.evanslaton.taskmaster.task;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Task.class}, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    abstract public TaskDao taskDao();
}
