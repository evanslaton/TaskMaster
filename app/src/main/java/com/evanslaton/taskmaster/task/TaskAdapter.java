package com.evanslaton.taskmaster.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanslaton.taskmaster.R;

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
    public void add(Task task) {
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

        // set the view's size, margins, padding and layout parameters
        TaskAdapter.ViewHolder vh = new TaskAdapter.ViewHolder(v);
        return vh;
    }

    // Replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Replaces the contents of the view with the project id and title
//        holder.taskId.setText(String.valueOf(tasks.get(position).getId()));
        holder.taskTitle.setText(tasks.get(position).title);
        holder.taskState.setText("State: " + tasks.get(position).state);
    }

    // Returns the size of projects (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
