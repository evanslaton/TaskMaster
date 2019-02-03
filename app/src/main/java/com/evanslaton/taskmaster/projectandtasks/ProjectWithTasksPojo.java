//package com.evanslaton.taskmaster.projectandtasks;
//
//import com.evanslaton.taskmaster.project.Project;
//import com.evanslaton.taskmaster.task.Task;
//
//import java.util.List;
//
//import androidx.room.Embedded;
//import androidx.room.Relation;
//
//// https://stackoverflow.com/questions/44330452/android-persistence-room-cannot-figure-out-how-to-read-this-field-from-a-curso/44424148#44424148
//public class ProjectWithTasksPojo {
//    @Embedded
//    public Project project;
//
//    @Relation(parentColumn = "id", entityColumn = "projectId", entity = Task.class)
//    public List<Task> tasks;
//}
