package com.construction.atominac.construction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.Objects;

public class login extends AppCompatActivity {
    EditText username,password;
    Button button;
    ProgressBar progressBar;
    LinearLayout LinearLayout;
    private  static  final String TAG="Login";
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout=(LinearLayout)findViewById(R.id.login_form);
        progressBar=(ProgressBar)findViewById(R.id.login_progress);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        session= new UserSessionManager(getApplicationContext());
        button=(Button)findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num1 = username.getText().toString();
                String num2 = password.getText().toString();

            if (!num1.equals("")){
                    loginApiCall(num1,num2);
                }
                else
                    Toast.makeText(login.this, "enter valid details", Toast.LENGTH_SHORT).show();


            }
        });

}

    private void loginApiCall(String checkusername, String checkpassword) {
        showProgress();
        RequestQueue queue = Volley.newRequestQueue(this);
        String api="https://homebuddy2018.herokuapp.com/login/";
        Map<String,Object> data=new HashMap<>();
        data.put("phone",checkusername);
        data.put("password",checkpassword);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,api,new JSONObject(data),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("login");
                    if (status.equals("success")){
                        String userId = response.getString("id");
                        session.create_login_session(userId);
                        Intent intent = new Intent("com.construction.atominac.construction.home");
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();

                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
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
        LinearLayout.setVisibility(View.GONE);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        LinearLayout.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed(){Intent i=new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}
