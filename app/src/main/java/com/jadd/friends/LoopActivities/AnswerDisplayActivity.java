package com.jadd.friends.LoopActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;
import com.jadd.friends.adapter.AnswerDisplayRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class AnswerDisplayActivity extends AppCompatActivity implements AnswerDisplayRecyclerViewAdapter.OnAnswerClickListener {

    RecyclerView recyclerView;
    AnswerDisplayRecyclerViewAdapter answerDisplayRecyclerViewAdapter;
    TextView questionView;
    ArrayList<User> names = new ArrayList<>();
    String code;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lobby");
    private User user;
    private User u;
    private String randomQuestion;
    private BroadcastReceiver act2InitReceiver;
    boolean once = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = getIntent().getStringExtra("CODE");
        randomQuestion = getIntent().getStringExtra("QUESTION");
        setContentView(R.layout.activity_answer_display);
        recyclerView = findViewById(R.id.answer_display_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionView = findViewById(R.id.question_answer_display_view);
        questionView.setText(randomQuestion);


        if(once){
            once = false;
            reference.child(code).child("Member").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Intent intent1 = new Intent(AnswerDisplayActivity.this, WaitingForVoteActivity.class);
                        intent1.putExtra("CODE", code);
                        startActivity(intent1);
                        finish();
                    }
                    Collections.shuffle(names);
                    answerDisplayRecyclerViewAdapter = new AnswerDisplayRecyclerViewAdapter(AnswerDisplayActivity.this,
                            names,AnswerDisplayActivity.this);
                    recyclerView.setAdapter(answerDisplayRecyclerViewAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(act2InitReceiver);
    }*/

    @Override
    public void onAnswerClick(int pos) {

        if(u.getAnswer().equals(names.get(pos).getAnswer())){
            Toast.makeText(this, "Goli beta, masti nai!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            int point = names.get(pos).getPoint();
            point++;
            names.get(pos).setPoint(point);
            u.setCompleted(true);
            User user = names.get(pos);
            u.setAnswerOf(user.getName());
            reference.child(code).child("Member").child(names.get(pos).getName()).setValue(user);
            reference.child(code).child("Member").child(u.getName()).setValue(u);

        }

    }
}
