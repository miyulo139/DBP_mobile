package com.example.finalfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlberguesAdapter extends RecyclerView.Adapter<AlberguesAdapter.MyViewHolder> {

    String data1[], data2[], data3[];
    int img[];
    Context context;

    public AlberguesAdapter(Context ct, String s1[], String s2[], String s3[], int images[]){
        context=ct;
        data1=s1;
        data2=s2;
        data3 = s3;
        img=images;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.albergues_adapter, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.text1.setText(data1[position]);
        holder.text2.setText(data2[position]);
        holder.text3.setText(data3[position]);
        holder.img.setImageResource(img[position]);
    }

    @Override
    public int getItemCount(){
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text1, text2, text3;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.idHome);
            text2 = itemView.findViewById(R.id.idDesc);
            text3 = itemView.findViewById(R.id.idRed);
            img = itemView.findViewById(R.id.idImagen2);
        }
    }
}
