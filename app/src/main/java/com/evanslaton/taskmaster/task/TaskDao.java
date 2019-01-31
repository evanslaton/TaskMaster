//package com.evanslaton.taskmaster.task;
//
//import java.util.List;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//@Dao
//public interface TaskDao {
//    // Inserts a task into the database
//    @Insert
//    void insertTask(Task task);
//
//    // Gets the task with the inputted id from the database
//    @Query("SELECT * FROM task WHERE id=:id")
//    Task getById(long id);
//
//    // Gets the task(s) with the inputted projectId from the database
////    @Query("SELECT * FROM task WHERE projectId=:projectId")
////    List<Task> getByProjectId(long projectId);
//
//    // Gets all the tasks from the database
//    @Query("SELECT * FROM task")
//    List<Task> getAll();
//}
