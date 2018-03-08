package com.construction.atominac.construction;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by _atominac on 06/03/2018.
 */

 class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskListModel> tasklist;
    private Context mContext;


    TaskListAdapter(List<TaskListModel> taskList,Context context) {
        this.tasklist= taskList;
        mContext=context;

    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskname;

        ViewHolder(View view){
            super(view);
            taskname=(TextView) view.findViewById(R.id.task_name);
            view.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
//                    Toast.makeText(mContext,categoryName.getText().toString(),Toast.LENGTH_SHORT).show();
                    taskdesc fragment = new taskdesc();
                    Bundle bundle=new Bundle();
                    bundle.putString("task_name",taskname.getText().toString());

                    fragment.setArguments(bundle);
                    ((home) mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }


    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View taskView= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_model,parent,false);
        return new TaskListAdapter.ViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder holder, int position) {
        TaskListModel activityListItems = tasklist.get(position);
        holder.taskname.setText(activityListItems.getTaskname());
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }
}

