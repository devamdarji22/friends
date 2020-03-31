package com.jadd.friends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LobbyRecyclerViewAdapter extends RecyclerView.Adapter<LobbyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> names;
    OnPlayerKickClickListner onPlayerKickClickListner;
    OnPlayerReadyClickListner onPlayerReadyClickListner;
    boolean leader;

    public LobbyRecyclerViewAdapter(Context context, ArrayList<User> names,
                                    OnPlayerKickClickListner onPlayerKickClickListner,
                                    OnPlayerReadyClickListner onPlayerReadyClickListner,boolean leader) {
        this.context = context;
        this.leader = leader;
        this.names = names;
        this.onPlayerKickClickListner = onPlayerKickClickListner;
        this.onPlayerReadyClickListner = onPlayerReadyClickListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_player_lobby,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, onPlayerKickClickListner,onPlayerReadyClickListner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(!leader){
            holder.kickButton.setVisibility(View.GONE);
        }
        else {
            holder.kickButton.setVisibility(View.VISIBLE);
        }
        if(names.get(position).isLeader()){
            holder.kickButton.setVisibility(View.GONE);
        }
        if(FirebaseAuth.getInstance().getUid().equals(names.get(position).getUid())){
            holder.readyButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.readyButton.setVisibility(View.GONE);
        }
        if(names.get(position).isReady()){
            holder.ready.setText("Ready");
        }
        else {
            holder.ready.setText("Not Ready");
        }
        if(names.get(position).isReady()){
            holder.readyButton.setText("Not Ready");
        }
        else {

            holder.readyButton.setText("Ready");
        }
        holder.name.setText(names.get(position).getName());
        holder.pointView.setText(String.valueOf(names.get(position).getPoint()));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    //implements View.OnClickListener
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnPlayerReadyClickListner onPlayerReadyClickListner;
        OnPlayerKickClickListner onPlayerKickClickListner;
        TextView name,ready,pointView;
        Button kickButton,readyButton;

        public MyViewHolder(@NonNull View itemView, final OnPlayerKickClickListner onPlayerKickClickListner,
                            final OnPlayerReadyClickListner onPlayerReadyClickListner) {
            super(itemView);
            name = itemView.findViewById(R.id.lobby_name_view);
            readyButton = itemView.findViewById(R.id.lobby_ready_button);
            ready = itemView.findViewById(R.id.lobby_ready_view);
            kickButton = itemView.findViewById(R.id.lobby_kick_button);
            pointView = itemView.findViewById(R.id.point_view);
            this.onPlayerKickClickListner = onPlayerKickClickListner;
            this.onPlayerReadyClickListner = onPlayerReadyClickListner;
            kickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPlayerKickClickListner.onPlayerKickClick(getAdapterPosition());
                }
            });
            readyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPlayerReadyClickListner.onPlayerReadyClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            onPlayerKickClickListner.onPlayerKickClick(getAdapterPosition());
            onPlayerReadyClickListner.onPlayerReadyClick(getAdapterPosition());
        }
    }

    public interface OnPlayerKickClickListner{
        void onPlayerKickClick(int pos);
    }
    public interface OnPlayerReadyClickListner {
        void onPlayerReadyClick(int pos);
    }

}
