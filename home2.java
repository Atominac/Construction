package com.construction.atominac.construction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.category;


public class home2 extends Fragment {

    View mView;
    private  static  final  String TAG="taskList";
    ProgressBar ProgressBar;
    public String category;
    private List<TaskListModel> activityList = new ArrayList<>();
    private TaskListAdapter mAdapter;
    TaskListModel activityItems;
    LinearLayoutManager layoutManager ;
    RecyclerView recyclerView ;


    public home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        this.mView = view;

        setHasOptionsMenu(true);

        ((home) getActivity())
                .setActionBarTitle("Task List");

        ProgressBar = (ProgressBar)mView.findViewById(R.id.task_progressBar);
        recyclerView = (RecyclerView) mView.findViewById(R.id.task_recycler);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        TaskListApiCall();

        return view ;



    }


    void TaskListApiCall(){
        showProgress();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String api = "https://homebuddy2018.herokuapp.com/showCat";

        VolleyRequester request = new VolleyRequester(Request.Method.GET,api,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i <=jsonArray.length(); i++) {
                    try {
                        JSONObject itemDetails = (JSONObject)jsonArray.get(i);
                        String taskname = itemDetails.get("category_name").toString();


                        activityItems = new TaskListModel(taskname);
                        activityList.add(activityItems);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                mAdapter = new TaskListAdapter(activityList, getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                hideProgress();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };

        queue.add(request);
    }


    private void showProgress() {
        recyclerView.setVisibility(View.GONE);
        ProgressBar.setIndeterminate(true);
        ProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        ProgressBar.setIndeterminate(false);
        ProgressBar.setVisibility(View.GONE);
    }


}
