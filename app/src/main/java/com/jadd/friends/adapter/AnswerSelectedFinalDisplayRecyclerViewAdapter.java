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

public class AnswerSelectedFinalDisplayRecyclerViewAdapter extends RecyclerView.Adapter<AnswerSelectedFinalDisplayRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> names;

    public AnswerSelectedFinalDisplayRecyclerViewAdapter(Context context, ArrayList<User> names) {
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_answer,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(names.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.answer_display_view);
        }
    }
}
