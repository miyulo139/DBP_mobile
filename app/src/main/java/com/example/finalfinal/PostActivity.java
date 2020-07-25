package com.example.finalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    public int id_user;
    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.id_user = this.getIntent().getExtras().getInt("id");
        this.username = this.getIntent().getExtras().getString("username");
        this.setTitle(this.username);
    }
    public void Sign(View view){
        EditText txtPlace = (EditText) findViewById(R.id.txtPlace);
        EditText txtType = (EditText) findViewById(R.id.txtType);
        EditText txtBreed = (EditText) findViewById(R.id.txtBreed);
        EditText txtAge = (EditText) findViewById(R.id.txtAge);
        EditText txtColor = (EditText) findViewById(R.id.txtColor);
        EditText txtInfo = (EditText) findViewById(R.id.txtInfo);
        final String place = txtPlace.getText().toString();
        final String type = txtType.getText().toString();
        final String breed = txtBreed.getText().toString();
        final String age = txtAge.getText().toString();
        final String color = txtColor.getText().toString();
        final String info = txtInfo.getText().toString();;
        final String id_user = Integer.toString(this.id_user);
        Map<String, String> message = new HashMap<>();
        message.put("place",place);
        message.put("type",type);
        message.put("breed",breed);
        message.put("age",age);
        message.put("color", color);
        message.put("info",info);
        message.put("id_user", id_user);
        JSONObject jsonMessage = new JSONObject(message);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/pets",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        goToMain();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void goToMain(){
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("username",this.username);
        intent.putExtra("id",this.id_user);
        startActivity(intent);
    }
}