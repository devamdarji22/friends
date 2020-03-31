package com.jadd.friends.LoopActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;
import com.jadd.friends.adapter.WaitingToVoteRecyclerViewAdapter;

import java.util.ArrayList;

public class WaitingForVoteActivity extends AppCompatActivity {

    String code;
    RecyclerView recyclerView;
    ArrayList<User> names = new ArrayList<>();
    DatabaseReference namesReference = FirebaseDatabase.getInstance().getReference("Lobby");
    private User user;
    WaitingToVoteRecyclerViewAdapter waitingToVoteRecyclerViewAdapter;
    private User u;
    private String randomQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_vote);

        code = getIntent().getStringExtra("CODE");
        randomQuestion = getIntent().getStringExtra("QUESTION");
        recyclerView = findViewById(R.id.voting_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        namesReference.child(code).child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                String uid = FirebaseAuth.getInstance().getUid();
                if(u==null) {
                    for (DataSnapshot player : dataSnapshot.getChildren()) {
                        user = player.getValue(User.class);
                        if (user.getUid().equals(uid)) {
                            u = user;
                        }
                        names.add(user);
                    }
                    waitingToVoteRecyclerViewAdapter = new WaitingToVoteRecyclerViewAdapter(WaitingForVoteActivity.this, names);
                    recyclerView.setAdapter(waitingToVoteRecyclerViewAdapter);
                    boolean notStart = false;
                    for (int i = 0; i < names.size(); i++) {
                        if (!names.get(i).isCompleted()) {
                            notStart = true;
                        }
                    }
                    if (!notStart) {
                        Intent intent = new Intent(WaitingForVoteActivity.this, FinalResultDisplayActivity.class);
                        intent.putExtra("CODE", code);
                        intent.putExtra("QUESTION", randomQuestion);
                        startActivity(intent);
                        WaitingForVoteActivity.this.finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
