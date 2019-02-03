package com.evanslaton.taskmaster.applicationuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.project.ProjectList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationUserProfile extends AppCompatActivity {
    protected FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_user_profile);
        setUserName();
    }

    // Puts the user's username on their profile
    public void setUserName() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView username = findViewById(R.id.username);
//        username.setText("Hi, " + user.getDisplayName().toString());
        username.setText(String.valueOf(user.getUid()));
    }

    // Takes the user to the project list activity
    public void goToProjectList(View v) {
        Intent goToProjectsIntent = new Intent(this, ProjectList.class);
        this.startActivity(goToProjectsIntent);
    }
}
