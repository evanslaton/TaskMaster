package com.evanslaton.taskmaster.project;

import com.evanslaton.taskmaster.task.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import static androidx.room.ForeignKey.CASCADE;


// https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
// http://androidkt.com/database-relationships/
//@Entity(foreignKeys = @ForeignKey(entity = Task.class,
//        parentColumns = "id",
//        childColumns = "projectId",
//        onDelete = CASCADE))

public class Project {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String title;

    public Project(String title) {
        this.title = title;
    }

}
