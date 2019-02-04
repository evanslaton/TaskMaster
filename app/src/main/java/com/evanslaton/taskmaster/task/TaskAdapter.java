package com.evanslaton.taskmaster.task;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.applicationuser.UserList;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

// http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;

    // Provides a reference to the views for each Project
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView taskId;
        public TextView taskTitle;
        public TextView taskState;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            taskId = v.findViewById(R.id.taskId);
            taskTitle = v.findViewById(R.id.taskTitle);
            taskState = v.findViewById(R.id.taskState);
        }
    }

    // Constructor
    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Adds a new project to projects
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
    public void add(Task task, String firebaseId) {
        task.setFirebaseId(firebaseId);
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    // Create a new view (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.task_view, parent, false);

        // Adds an onClick listener
        // https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        TextView idView = view.findViewById(R.id.taskId);
                        TextView titleView = view.findViewById(R.id.taskTitle);
                        String id = idView.getText().toString();
                        String title = titleView.getText().toString();
                        Log.i("Task Title", title);
                        Log.i("Task id", id);
                        goToUserList(view, id, title);
                    }
                });

        // set the view's size, margins, padding and layout parameters
        TaskAdapter.ViewHolder vh = new TaskAdapter.ViewHolder(v);
        return vh;
    }

    // Replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replaces the contents of the view with the project id and title
        holder.taskId.setText(String.valueOf(tasks.get(position).getFirebaseId()));
        holder.taskTitle.setText(tasks.get(position).title);
        holder.taskState.setText("State: " + tasks.get(position).state);
    }

    // Returns the size of projects (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // Takes the user to the ProjectWithTasks activity
    // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
    public void goToUserList(View v, String id, String title) {
        Intent goToUserList = new Intent(v.getContext(), UserList.class);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        goToUserList.putExtra("TASK_ID", id);
        goToUserList.putExtra("TASK_TITLE", title);
        v.getContext().startActivity(goToUserList);
    }
}
