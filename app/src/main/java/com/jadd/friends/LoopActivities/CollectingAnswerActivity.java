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
import com.jadd.friends.adapter.CollectingAnswerRecyclerViewAdapter;

import java.util.ArrayList;

public class CollectingAnswerActivity extends AppCompatActivity {

    String code;
    RecyclerView recyclerView;
    ArrayList<User> names = new ArrayList<>();
    DatabaseReference namesReference = FirebaseDatabase.getInstance().getReference("Lobby");
    private User user;
    CollectingAnswerRecyclerViewAdapter collectingAnswerRecyclerViewAdapter;
    private User u;
    private String randomQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_answer);

        code = getIntent().getStringExtra("CODE");
        randomQuestion = getIntent().getStringExtra("QUESTION");
        recyclerView = findViewById(R.id.answer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        namesReference.child(code).child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                names.clear();
                String uid = FirebaseAuth.getInstance().getUid();
                for(DataSnapshot player : dataSnapshot.getChildren()){
                    user = player.getValue(User.class);
                    if(user.getUid().equals(uid)){
                        u = user;
                    }
                    names.add(user);
                }
                if(u.isCompleted()){
                    Intent intent1 = new Intent(CollectingAnswerActivity.this, WaitingForVoteActivity.class);
                    intent1.putExtra("CODE", code);
                    intent1.putExtra("QUESTION",randomQuestion);
                    startActivity(intent1);
                    finish();
                }
                collectingAnswerRecyclerViewAdapter = new CollectingAnswerRecyclerViewAdapter(CollectingAnswerActivity.this,names);
                recyclerView.setAdapter(collectingAnswerRecyclerViewAdapter);
                boolean notStart = false;
                for(int i=0;i<names.size();i++){
                    if(!names.get(i).isAnswered()){
                        notStart = true;
                    }
                }
                if(!notStart){
                    Intent intent = new Intent(CollectingAnswerActivity.this, AnswerDisplayActivity.class);
                    intent.putExtra("CODE",code);
                    intent.putExtra("QUESTION",randomQuestion);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
