package com.example.finalfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Albergues extends AppCompatActivity {

    RecyclerView recyclerAlbergue;

    String s1[], s2[], s3[];
    int images[]={R.drawable.amigo4patas, R.drawable.adoptaunamascota,
            R.drawable.elmilagro, R.drawable.escuchasusladridos, R.drawable.supercats,
            R.drawable.angelguardian, R.drawable.misimiau, R.drawable.amoryrescate,
            R.drawable.lospeques
    };

    public String username;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albergues);

        username = this.getIntent().getExtras().getString("username");
        id = this.getIntent().getExtras().getInt("id");
        this.setTitle("Albergues");

        recyclerAlbergue = findViewById(R.id.recyclerAlbergue);

        s1 = getResources().getStringArray(R.array.albergues_name);
        s2 = getResources().getStringArray(R.array.descripcion);
        s3 = getResources().getStringArray(R.array.red);

        AlberguesAdapter myAdapter = new AlberguesAdapter(this, s1, s2, s3,images);
        recyclerAlbergue.setAdapter(myAdapter);
        recyclerAlbergue.setLayoutManager(new LinearLayoutManager(this));
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

    public void goToMain(View view){
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("username",this.username);
        intent.putExtra("id",this.id);
        startActivity(intent);
    }
}