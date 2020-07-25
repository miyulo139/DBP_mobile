package com.example.finalfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    public JSONArray pets;
    private Context context;
    private int userId;
    public String myUsername;

    public HomeAdapter(JSONArray users, Context context,String user,int id){
        this.pets = users;
        this.context = context;
        this.userId = id;
        this.myUsername = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position, payloads);
        try {
            JSONObject pet = pets.getJSONObject(position);
            String place = pet.getString("place");
            String type = pet.getString("type");
            String info = pet.getString("info");
            String breed = pet.getString("breed");
            String age = pet.getString("age");
            String color = pet.getString("color");
            final Integer id = pet.getInt("id");
            holder.place.setText(place);
            holder.type.setText(type);
            holder.info.setText(info);
            holder.breed.setText(breed);
            holder.age.setText(age);
            holder.color.setText(color);
            holder.container.findViewById(R.id.idButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotorandom(id);
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void gotorandom(int id){
        String ia = Integer.toString(id);
        String url = "http://ec2-18-219-212-25.us-east-2.compute.amazonaws.com/pets/" + ia;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        f5();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        message(error.toString());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return pets.length();
    }
    public void f5(){
        Intent intent= new Intent(this.context,HomeActivity.class);
        intent.putExtra("username",this.myUsername);
        intent.putExtra("id",this.userId);
        this.context.startActivity(intent);
    }
    public void message(String message){
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView place, type, info, age, color, breed;

        RelativeLayout container;
        public ViewHolder(View itemView){
            super(itemView);
            place = itemView.findViewById(R.id.idLugar);
            type = itemView.findViewById(R.id.idTipo);
            info = itemView.findViewById(R.id.idInfo);
            age = itemView.findViewById(R.id.idEdad);
            color = itemView.findViewById(R.id.idColor);
            breed = itemView.findViewById(R.id.idRaza);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }

}
