package com.example.finalfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void showMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
    private void goToHomeActivity(String username, int id){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    public void onSignUp(View view){
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        EditText txtName = (EditText) findViewById(R.id.txtName);
        EditText txtFulllname = (EditText) findViewById(R.id.txtFullname);
        EditText txtContact = (EditText) findViewById(R.id.txtContact);
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();
        String name = txtName.getText().toString();
        String fullname = txtFulllname.getText().toString();
        int contact = Integer.parseInt(String.valueOf(txtContact.getText()));
        Map<String, String> message = new HashMap<>();
        message.put("username",username);
        message.put("password",password);
        message.put("name",name);
        message.put("fullname",fullname);
        message.put("contact", Integer.toString(contact));

        JSONObject jsonMessage = new JSONObject(message);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/users",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        showMessage("User created !");
                        onLogin(username, password);

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //TODO
                        showMessage("Failed :c");
                    }
                }
        );
        //Send request
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void onLogin(String username, String password){
        Map<String, String> message = new HashMap<>();
        message.put("username", username);
        message.put("password", password);
        JSONObject jsonMessage = new JSONObject(message);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        showMessage("Welcome !");
                        try {
                            String username = response.getString("username");
                            int id = response.getInt("id");
                            goToHomeActivity(username, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //TODO
                        showMessage("Failed :c");
                    }
                }
        );
        //Send request
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        Toast.makeText(this, jsonMessage.toString(), Toast.LENGTH_LONG).show();
    }
}