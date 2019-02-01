package com.evanslaton.taskmaster.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.project.Project;
import com.evanslaton.taskmaster.project.ProjectAdapter;
//import com.evanslaton.taskmaster.project.ProjectDatabase;
import com.evanslaton.taskmaster.task.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectList extends AppCompatActivity {
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;
    private static final String TAG = "ProjectListActivity";

    // Database variables
    // protected ProjectDatabase projectDatabase;
     protected List<Project> projects = new ArrayList<>();

    // Recycler View variables
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        updateRecyclerView();

        // Gets the Firebase database
//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Gets all projects from the database adds them to a list
//        List<DocumentSnapshot> projectsSnapshot = new ArrayList<>();
//        projectsSnapshot.get

        // 'Creates' the database
        // projectDatabase = Room.databaseBuilder(getApplicationContext(),
                // ProjectDatabase.class, "projectDatabase")
                // .allowMainThreadQueries()
                // .fallbackToDestructiveMigration()
                // .build();

        // FOR TESTING ONLY
//        if (projects.size() == 0) {
//            projects.add(new Project("DoTheNeedful"));
//            projectDatabase.projectDao().insertProject(projects.get(0));
//            projects.add(new Project("DoTheNeedful2"));
//            projectDatabase.projectDao().insertProject(projects.get(1));
//            projects.add(new Project("DoTheNeedful3"));
//            projectDatabase.projectDao().insertProject(projects.get(2));
//            projects = projectDatabase.projectDao().getAll();
//        }

        // Creates a layout manager and assigns it to the recycler view
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        // Specifies which adapter the recycler view should use
//        adapter = new ProjectAdapter(projects);
//        recyclerView.setAdapter(adapter);
    }

    // Adds a new project to the Project Database
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/AddProject.java
    public void createProject(View v) {
        TextView projectTextView = findViewById(R.id.createProjectTitle);
        String projectTitle = projectTextView.getText().toString();
        final Project newProject = new Project(projectTitle);

        // Saves project to firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("projects")
                .add(newProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        newProject.setFirebaseId(documentReference.getId());
                        documentReference.set(newProject);
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        // Saves project to room database
//        projectDatabase.projectDao().insertProject(project);
        projectTextView.setText(""); // Empties the input field

        // Updates the recycler view
//        projects = projectDatabase.projectDao().getAll();
//        adapter. setProjects(projects);

        // https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232 (second answer)
        // Hides the keyboard
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception exception) {
            // Do nothing
        }
    }

    // Updates the recycler view when new projects are added
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MainActivity.java
    public void updateRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.projects);
        recyclerView.setHasFixedSize(true);

        // Creates a layout manager and assigns it to the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifies which adapter the recycler view should use
        adapter = new ProjectAdapter(new ArrayList<Project>());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("projects")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New project: " + dc.getDocument().getData());
                                    adapter.add(dc.getDocument().toObject(Project.class));
                                    break;
                                case MODIFIED:
                                    Log.d(TAG, "Modified project: " + dc.getDocument().getData());
                                    //TODO: Update the project
                                    break;
                                case REMOVED:
                                    Log.d(TAG, "Removed project: " + dc.getDocument().getData());
                                    //TODO: Remove the project
                                    break;
                            }
                        }

                    }
                });
    }
}
