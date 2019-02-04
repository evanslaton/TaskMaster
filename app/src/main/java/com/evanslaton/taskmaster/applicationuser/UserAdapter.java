package com.evanslaton.taskmaster.applicationuser;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

    // http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
        private List<ApplicationUser> users;

        // Provides a reference to the views for each Project
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView userId;
            public TextView username;

            public ViewHolder(View v) {
                super(v);
                mView = v;
                userId = v.findViewById(R.id.userId);
                username = v.findViewById(R.id.username);
            }
        }

        // Constructor
        public UserAdapter(List<ApplicationUser> users) {
            this.users = users;
        }

        // Adds a new project to projects
        // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
        public void add(ApplicationUser user) {
//            user.setFirebaseId(firebaseId);
            users.add(user);
            notifyItemInserted(users.size() - 1);
        }

//        public void setTasks(List<Task> tasks) {
//            this.tasks = tasks;
//            this.notifyDataSetChanged();
//        }

        // Create a new view (invoked by the layout manager)
        @Override
        public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.user_view, parent, false);

            // Adds an onClick listener
            // https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
            v.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            TextView idView = view.findViewById(R.id.userId);
                            TextView usernameView = view.findViewById(R.id.username);
                            String id = idView.getText().toString();
                            String username = usernameView.getText().toString();
                            Log.i("Username", username + " and " + id);
                            Log.i("User id", id);
//                            goToUserList(view, id);
                        }
                    });

            // set the view's size, margins, padding and layout parameters
            UserAdapter.ViewHolder vh = new UserAdapter.ViewHolder(v);
            return vh;
        }

        // Replaces the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
            // Replaces the contents of the view with the project id and title
            holder.userId.setText(users.get(position).firebaseId);
            holder.username.setText(users.get(position).username);
        }

        // Returns the size of projects (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return users.size();
        }

        // Takes the user to the ProjectWithTasks activity
        // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
        public void goToUserList(View v, String id, String title) {
            Intent goToUserList = new Intent(v.getContext(), UserList.class);

            // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
            goToUserList.putExtra("TASK_ID", id);
            goToUserList.putExtra("TASK_TITLE", title);
        }
}
