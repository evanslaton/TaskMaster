package com.evanslaton.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.evanslaton.taskmaster.applicationuser.ApplicationUser;
import com.evanslaton.taskmaster.applicationuser.ApplicationUserProfile;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

// From Google's Android docs and http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class MainActivity extends AppCompatActivity {
    protected static final int RC_SIGN_IN = 9371;
    protected static final String TAG = "User Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    // Invoked after Firebase sign in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Checks to see if the user successfully signed in
            if (resultCode == RESULT_OK) {

                // Checks to see if the current user has a document in the "users" collection and creates one if not
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference docRef = db.collection("users").document(String.valueOf(user.getUid()));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document, creating user");
                                createUser(user.getDisplayName(), user.getUid(), db);
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

                // Takes the user to the project list activity
                Intent goToProfileIntent = new Intent(this, ApplicationUserProfile.class);
                this.startActivity(goToProfileIntent);
            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using the back button.
                // Otherwise check response.getError().getErrorCode() and handle the error.
            }
        }
    }

    public void createUser(String username, String firebaseId, FirebaseFirestore db) {
        final ApplicationUser newUser = new ApplicationUser(username);

        // Saves project to firebase
        db.collection("users").document(firebaseId)
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    // Takes the user to their profile page
    public void goToProfile() {
        Intent goToProfileIntent = new Intent(this, ApplicationUserProfile.class);
        this.startActivity(goToProfileIntent);
    }
}
