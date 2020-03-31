package com.jadd.friends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jadd.friends.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerFinalDisplayRecyclerViewadapter extends RecyclerView.Adapter<PlayerFinalDisplayRecyclerViewadapter.MyViewHolder> {

    Context context;
    ArrayList<String> names;
    ArrayList<Integer> point;
    int k;

    public PlayerFinalDisplayRecyclerViewadapter(Context context, ArrayList<String> names,ArrayList<Integer> point) {
        this.context = context;
        this.point = point;
        this.names = names;
    }

    @NonNull
    @Override
    public PlayerFinalDisplayRecyclerViewadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_result,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerFinalDisplayRecyclerViewadapter.MyViewHolder holder, int position) {
        holder.nameView.setText(names.get(position));
        holder.pointView.setText(String.valueOf(point.get(position)));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameView,pointView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.final_name_view);
            pointView = itemView.findViewById(R.id.final_point_view);
        }
    }

}
