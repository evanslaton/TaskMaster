package com.evanslaton.taskmaster.project;

import com.evanslaton.taskmaster.task.Task;
//import com.evanslaton.taskmaster.task.TaskDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Project.class, Task.class}, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {
//    abstract public ProjectDao projectDao();
//    abstract public TaskDao taskDao();
}
