package com.jadd.friends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WaitingToVoteRecyclerViewAdapter extends RecyclerView.Adapter<WaitingToVoteRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> names;

    public WaitingToVoteRecyclerViewAdapter(Context context, ArrayList<User> names) {
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_collecting_answer_view,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(names.get(position).getName());
        if(names.get(position).isCompleted()){
            holder.checkView.setBackgroundResource(R.drawable.ic_answered);
        }
        else {
            holder.checkView.setBackgroundResource(R.drawable.ic_not_answered);
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,checkView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.answer_name_view);
            checkView = itemView.findViewById(R.id.answer_condition_view);
        }
    }

}
