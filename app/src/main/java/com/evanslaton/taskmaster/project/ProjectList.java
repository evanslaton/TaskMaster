package com.evanslaton.taskmaster.project;

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
import com.evanslaton.taskmaster.project.ProjectAdapter;
import com.evanslaton.taskmaster.project.ProjectDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProjectList extends AppCompatActivity {
    // Database variables
    protected ProjectDatabase projectDatabase;
    protected List<Project> projects = new ArrayList<>();

    // Recycler View variables
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        // Saves the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.projects);

        // 'Creates' the database
        projectDatabase = Room.databaseBuilder(getApplicationContext(),
                ProjectDatabase.class, "projectDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // Gets all projects from the database
        projects = projectDatabase.projectDao().getAll();

        // FOR TESTING ONLY
        if (projects.size() == 0) {
            projects.add(new Project("DoTheNeedful"));
            projectDatabase.projectDao().insertProject(projects.get(0));
            projects.add(new Project("DoTheNeedful2"));
            projectDatabase.projectDao().insertProject(projects.get(1));
            projects.add(new Project("DoTheNeedful3"));
            projectDatabase.projectDao().insertProject(projects.get(2));
            projects = projectDatabase.projectDao().getAll();
        }

        // Creates a layout manager and assigns it to the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifies which adapter the recycler view should use
        adapter = new ProjectAdapter(projects);
        recyclerView.setAdapter(adapter);
    }

    // Adds a new project to the Project Database
    public void createProject(View v) {
        TextView projectTextView = findViewById(R.id.createProjectTitle);
        String projectTitle = projectTextView.getText().toString();
        projectDatabase.projectDao().insertProject(new Project(projectTitle));
        projectTextView.setText(""); // Empties the input field

        // Updates the recycler view
        projects = projectDatabase.projectDao().getAll();
        adapter. setProjects(projects);

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
