package com.evanslaton.taskmaster.projectandtasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.project.Project;
import com.evanslaton.taskmaster.project.ProjectDatabase;
import com.evanslaton.taskmaster.task.Task;
import com.evanslaton.taskmaster.task.TaskAdapter;
//import com.evanslaton.taskmaster.task.TaskDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProjectWithTasks extends AppCompatActivity {
    // Variable passed to the activity
    protected String projectId;
    protected String projectTitle;

    // Database variables
//    protected TaskDatabase taskDatabase;
//    protected List<Task> tasks = new ArrayList<>();

    // Project variables
    protected ProjectDatabase projectDatabase;
    protected ProjectWithTasksPojo projectWithTasks;

    // Recycler View variables
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_with_tasks);

        // Saves the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.tasks);

        // 'Creates' the database
//        projectDatabase = Room.databaseBuilder(getApplicationContext(),
//                ProjectDatabase.class, "projectDatabase")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();

        // Gets project id and title from the intent that directed the user to this activity
        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        projectId = getIntent().getStringExtra("PROJECT_ID");
        projectTitle = getIntent().getStringExtra("PROJECT_TITLE");

        // Inserts the project's title at the top of the activity8
        TextView projectLabel = findViewById(R.id.projectWithTaskLabel);
        projectLabel.setText(projectTitle);

        // Gets the project and it's tasks from the database
//        tasks = taskDatabase.taskDao().getByProjectId(projectId);
//        projectWithTasks = projectDatabase.projectDao().getProjectWithTasks(projectId);

        // FOR TESTING ONLY
//        if (projectWithTasks.tasks.size() == 0) {
//            projectWithTasks.tasks.add(new Task("Task1", 1));
//            projectWithTasks.tasks.add(new Task("Task2", 2));
//            projectWithTasks.tasks.add(new Task("Task3", 3));
//        }

        // Creates a layout manager and assigns it to the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifies which adapter the recycler view should use
        adapter = new TaskAdapter(projectWithTasks.tasks);
        recyclerView.setAdapter(adapter);
    }

    // Adds a new task to the Task Database
    public void createTask(View v) {
        TextView taskTextView = findViewById(R.id.createTaskTitle);
        String taskTitle = taskTextView.getText().toString();
        projectDatabase.taskDao().insertTask(new Task(taskTitle, this.projectId));
        taskTextView.setText(""); // Empties the input field

        // Updates the recycler view
        projectWithTasks = projectDatabase.projectDao().getProjectWithTasks(projectId);
//        tasks = taskDatabase.taskDao().getAll();
        adapter.setTasks(projectWithTasks.tasks);

        // https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232 (second answer)
        // Hides the keyboard
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception exception) {
            // Do nothing
        }
    }
}
