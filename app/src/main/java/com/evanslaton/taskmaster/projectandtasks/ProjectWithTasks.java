package com.evanslaton.taskmaster.projectandtasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.project.Project;
import com.evanslaton.taskmaster.task.Task;
import com.evanslaton.taskmaster.task.TaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectWithTasks extends AppCompatActivity {
    // Variable passed to the activity
    protected String projectId;
    protected String projectTitle;
    protected Project project;

    // Database variables
    private static final String TAG = "ProjectWithTasks";
    protected List<Task> tasks = new ArrayList<>();

    // Recycler View variables
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_with_tasks);

        // Gets project id and title from the intent that directed the user to this activity
        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        projectId = getIntent().getStringExtra("PROJECT_ID");
        projectTitle = getIntent().getStringExtra("PROJECT_TITLE");

        // Get's the project from Firebase
        getProjectFromFirebase();

        // Saves the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.tasks);

        // Inserts the project's title at the top of the activity8
        TextView projectLabel = findViewById(R.id.projectWithTaskLabel);
        projectLabel.setText(projectTitle);

        updateRecyclerView();
    }

    public void getProjectFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("projects").document(projectId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            project = new Project(document.get("title").toString());
                            project.setTask((Map<String, Boolean>) document.get("tasks"));
                            project.setNumberOfTasks(project.getTasks().size());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
    }

    // Adds a new task to the Task database
    public void createTask(View v) {
        TextView taskTextView = findViewById(R.id.createTaskTitle);
        String taskTitle = taskTextView.getText().toString();
        final Task newTask = new Task(taskTitle, projectId);

        // Saves task to firebase
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .add(newTask)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        project.addTask(documentReference.getId());
                        Log.i("Tasks", String.valueOf(project.getTasks()));
                        updateProjectTasks(db);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        taskTextView.setText(""); // Empties the input field

        // https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232 (second answer)
        // Hides the keyboard
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception exception) {
            // Do nothing
        }
    }

    // Updates the project's tasks in Firebase
    // https://dzone.com/articles/cloud-firestore-read-write-update-and-delete
    public void updateProjectTasks(FirebaseFirestore db) {
        DocumentReference projectToUpdate = db.collection("projects").document(projectId);
        projectToUpdate.update("tasks", project.getTasks())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Success", String.valueOf(project.getTasks().size()));
                    }
                });
    }

    // Updates the recycler view when new tasks are added
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MainActivity.java
    public void updateRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.tasks);
//        recyclerView.setHasFixedSize(true);

        // Creates a layout manager and assigns it to the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifies which adapter the recycler view should use
        adapter = new TaskAdapter(new ArrayList<Task>());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
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
                                    if (projectId.equals(dc.getDocument().toObject(Task.class).getProjectFirebaseId())) {
                                        adapter.add(dc.getDocument().toObject(Task.class));
                                    }
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
