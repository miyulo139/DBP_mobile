package com.example.finalfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    public String username;
    public int id;
    RecyclerView mRecycleView;
    RecyclerView.Adapter mAdapter;
    public int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecycleView = findViewById(R.id.main_recycle_view);
        username = this.getIntent().getExtras().getString("username");
        id = this.getIntent().getExtras().getInt("id");
        getCount();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        getPets();
    }

    public void getPets(){
        String url = "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/pets";
        JsonArrayRequest request =  new JsonArrayRequest(
                Request.Method.GET,
                url,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //to do on users response
                        mAdapter = new HomeAdapter(response, getActivity(),username, id);
                        mRecycleView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //to do on error
                        error.printStackTrace();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void getCount(){
        String url = "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/pets";
        JsonArrayRequest request =  new JsonArrayRequest(
                Request.Method.GET,
                url,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<response.length();i++){
                                JSONObject vacio;
                                vacio = response.getJSONObject(i);
                                int probar = vacio.getInt("id_user");
                                if (probar==id){
                                    contador += 1;
                                }
                            }
                            getActivity().setTitle(username + " has rescatado: " + Integer.toString(contador));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //to do on error
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public Activity getActivity(){
        return this;
    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void onClickClose(View v){
        String url = "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/logoutx";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showMessage("You left the session");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                        showMessage("There was a problem");
                    }
                }
        );
        RequestQueue quequ = Volley.newRequestQueue(this);
        quequ.add(request);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void GoToRegister(View view){
        Intent intent = new Intent(this,PostActivity.class);
        intent.putExtra("id",this.id);
        intent.putExtra("username",this.username);
        startActivity(intent);
    }

    public void GoToAlbergues(View view){
        Intent intent = new Intent(this,Albergues.class);
        intent.putExtra("id",this.id);
        intent.putExtra("username",this.username);
        startActivity(intent);
    }
}