package com.evanslaton.taskmaster.applicationuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.project.ProjectAdapter;
import com.evanslaton.taskmaster.task.Task;

import com.evanslaton.taskmaster.task.TaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    // Variable passed to the activity
    protected String taskId;
    protected String taskTitle;
    protected Task taskToAssign;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db;

    private static final String TAG = "UserListActivity";
    protected List<ApplicationUser> users = new ArrayList<>();

    // Recycler View variables
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Gets project id and title from the intent that directed the user to this activity
        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        taskId = getIntent().getStringExtra("TASK_ID");
        taskTitle = getIntent().getStringExtra("TASK_TITLE");

        // Get's the task from Firebase
        getTaskFromFirebase();

        // Saves the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.tasks);

        // Inserts the project's title at the top of the activity8
//        TextView projectLabel = findViewById(R.id.projectWithTaskLabel);
//        projectLabel.setText(taskTitle);

        updateRecyclerView();
    }

    public void getTaskFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.i("TASK ID", "here is the task id" + taskId);
        DocumentReference docRef = db.collection("tasks").document(taskId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        taskToAssign = new Task(taskTitle, taskId);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void updateRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.userList);

        // Creates a layout manager and assigns it to the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifies which adapter the recycler view should use
        adapter = new UserAdapter(new ArrayList<ApplicationUser>());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
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
//                                    if (projectId.equals(dc.getDocument().toObject(Task.class).getProjectFirebaseId())) {
                                        adapter.add(dc.getDocument().toObject(ApplicationUser.class));
//                                    }
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
