package com.construction.atominac.construction;


import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class taskdesc extends Fragment {
    private Context mContext;
    EditText currentprogress;
    TextView taskdesc,expectedprogress;
    Button submit;
   ProgressBar progressbar;
    protected View mView;
    public static final String TAG = "taskdescription";
    LinearLayout linearLayout;

    public taskdesc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_taskdesc, container, false);
        this.mView = view;
        linearLayout=(LinearLayout)mView.findViewById(R.id.desc_layout);
       // progressbar=(ProgressBar)mView.findViewById(R.id.desc_progressbar);
        currentprogress=(EditText)mView.findViewById(R.id.actualprogress);
        taskdesc=(TextView)mView.findViewById(R.id.textdesc);
        expectedprogress=(TextView)mView.findViewById(R.id.expectedprogress);
        submit=(Button)mView.findViewById(R.id.Submit);
        String id="hey";
        UserDetailsApiCall(id);

        ((home) getActivity())
                .setActionBarTitle("Task Description");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num1 = currentprogress.getText().toString();


                if (!num1.equals("")){
                   submitApiCall(num1);
                }
                else
                    Toast.makeText(mContext,"enter valid information",Toast.LENGTH_SHORT).show();

            }


        });

        return mView;

    }

    private void submitApiCall(String num1){
        //showProgress();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String api = "https://homebuddy2018.herokuapp.com/editItem/";
        Map<String, Object> data = new HashMap<>();
        data.put( "num1", num1 );


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,api,new JSONObject(data),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(mContext,status,Toast.LENGTH_SHORT).show();


                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
               // hideProgress();
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


    private void UserDetailsApiCall(String id){
        //showProgress();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String api = "";
        Map<String, Object> data = new HashMap<>();
        data.put( "id", id );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,api,new JSONObject(data),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String name = response.getString("taskdescription");
                    taskdesc.setText(name);
                    String phone = response.getString("phone");
                    expectedprogress.setText(phone);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                //hideProgress();
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
        linearLayout.setVisibility(View.GONE);
       progressbar.setIndeterminate(true);
       progressbar.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        linearLayout.setVisibility(View.VISIBLE);
        progressbar.setIndeterminate(false);
       progressbar.setVisibility(View.GONE);
    }

}
