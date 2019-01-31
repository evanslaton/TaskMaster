package com.evanslaton.taskmaster.project;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;
import com.evanslaton.taskmaster.projectandtasks.ProjectWithTasks;

import java.util.List;

// http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
// https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Project> projects;

    // Provides a reference to the views for each Project
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView projectId;
        public TextView projectTitle;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            projectId = v.findViewById(R.id.projectId);
            projectTitle = v.findViewById(R.id.projectTitle);
        }
    }

    // Constructor
    public ProjectAdapter(List<Project> projects) {
        this.projects = projects;
    }

    // Adds a new project to projects
    // https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
    public void add(Project project) {
        projects.add(project);
        notifyItemInserted(projects.size() - 1);
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        this.notifyDataSetChanged();
    }

    // Create a new view (invoked by the layout manager)
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.project_view, parent, false);

        // Adds an onClick listener
        // https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        TextView idView = view.findViewById(R.id.projectId);
                        TextView titleView = view.findViewById(R.id.projectTitle);
                        String id = idView.getText().toString();
                        String title = titleView.getText().toString();
                        Log.i("Project Title", title);
                        Log.i("Project id", id);
//                        goToProject(view, id, title);
                    }
                });

        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replaces the contents of the view with the project id and title
        holder.projectId.setText(projects.get(position).getFirebaseId());
        holder.projectTitle.setText(projects.get(position).getTitle());
    }

    // Returns the size of projects (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return projects.size();
    }

    // Takes the user to the ProjectWithTasks activity
    // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
    public void goToProject(View v, String id, String title) {
        Intent goToProjectWithTasksIntent = new Intent(v.getContext(), ProjectWithTasks.class);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        goToProjectWithTasksIntent.putExtra("PROJECT_ID", id);
        goToProjectWithTasksIntent.putExtra("PROJECT_TITLE", title);
        v.getContext().startActivity(goToProjectWithTasksIntent);
    }
}
