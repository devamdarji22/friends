package com.jadd.friends.LoopActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;
import com.jadd.friends.adapter.AnswerSelectedFinalDisplayRecyclerViewAdapter;
import com.jadd.friends.adapter.PlayerFinalDisplayRecyclerViewadapter;

import java.util.ArrayList;

public class FinalResultDisplayActivity extends AppCompatActivity {

    RecyclerView answerRecyclerView,finalResultRecyclerView;
    private String code;
    ArrayList<User> names = new ArrayList<>(),toDisplay = new ArrayList<>();
    ArrayList<String> resultList = new ArrayList<>();
    ArrayList<Integer> points = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lobby");
    private User user;
    private User u = new User();
    Button nextButton;
    AnswerSelectedFinalDisplayRecyclerViewAdapter answerDisplayRecyclerViewAdapter;
    PlayerFinalDisplayRecyclerViewadapter playerFinalDisplayRecyclerViewadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result_display);

        code = getIntent().getStringExtra("CODE");
        answerRecyclerView = findViewById(R.id.people_selected_recycler_view);
        finalResultRecyclerView = findViewById(R.id.result_recycler_view);
        nextButton = findViewById(R.id.next_round_button);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        finalResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        reference.child(code).child("Member").addValueEventListener(new ValueEventListener() {
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

                int a;
                if(u.isLeader()){
                    nextButton.setVisibility(View.VISIBLE);
                }
                else {
                    nextButton.setVisibility(View.GONE);
                }
                if(!u.isReady()){
                    Intent intent = new Intent(FinalResultDisplayActivity.this, LobbyActivity.class);
                    intent.putExtra("CODE",code);
                    startActivity(intent);
                }
                for(int i = 0,j=0;i<names.size();i++){
                    a=0;
                    if(u.getName().equals(names.get(i).getAnswerOf())){
                        toDisplay.add(names.get(i));
                    }
                    resultList.add(names.get(i).getName());
                    for (int k = 0;k<names.size();k++){
                        if(names.get(i).getName().equals(names.get(k).getAnswerOf())){
                            a++;
                        }
                    }
                    points.add(a);
                }

                answerDisplayRecyclerViewAdapter = new AnswerSelectedFinalDisplayRecyclerViewAdapter(FinalResultDisplayActivity.this,
                        toDisplay);
                answerRecyclerView.setAdapter(answerDisplayRecyclerViewAdapter);

                playerFinalDisplayRecyclerViewadapter = new PlayerFinalDisplayRecyclerViewadapter
                        (FinalResultDisplayActivity.this,resultList,points);
                finalResultRecyclerView.setAdapter(playerFinalDisplayRecyclerViewadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int a = 0;a<names.size();a++){
                    names.get(a).setAnswerOf(null);
                    names.get(a).setAnswer(null);
                    names.get(a).setCompleted(false);
                    names.get(a).setAnswered(false);
                    names.get(a).setReady(false);
                    reference.child(code).child("Member").child(names.get(a).getName()).setValue(names.get(a));
                    reference.child(code).child("question").removeValue();
                }

                Intent intent = new Intent(FinalResultDisplayActivity.this, LobbyActivity.class);
                intent.putExtra("CODE",code);
                startActivity(intent);
                finish();
            }
        });



    }
}
