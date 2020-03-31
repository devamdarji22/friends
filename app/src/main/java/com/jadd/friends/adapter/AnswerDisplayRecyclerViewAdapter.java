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

public class AnswerDisplayRecyclerViewAdapter extends RecyclerView.Adapter<AnswerDisplayRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> names;
    OnAnswerClickListener onAnswerClickListener;

    public AnswerDisplayRecyclerViewAdapter(Context context, ArrayList<User> names, OnAnswerClickListener onAnswerClickListener) {
        this.context = context;
        this.names = names;
        this.onAnswerClickListener = onAnswerClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_answer,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, onAnswerClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.answerView.setText(names.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnAnswerClickListener onAnswerClickListener;
        TextView answerView;

        public MyViewHolder(@NonNull View itemView,OnAnswerClickListener onAnswerClickListener) {
            super(itemView);
            this.onAnswerClickListener = onAnswerClickListener;
            answerView = itemView.findViewById(R.id.answer_display_view);
            answerView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAnswerClickListener.onAnswerClick(getAdapterPosition());
        }
    }

    public interface OnAnswerClickListener{
        void onAnswerClick(int pos);
    }

}
