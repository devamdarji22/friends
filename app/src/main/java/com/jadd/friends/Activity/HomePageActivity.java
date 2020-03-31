package com.jadd.friends.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.Classes.Lobby;
import com.jadd.friends.LoopActivities.LobbyActivity;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

import java.util.ArrayList;
import java.util.Random;

public class HomePageActivity extends AppCompatActivity {

    TextView create,join;
    private ArrayList<String> wordArrayList;
    private Random random;
    DatabaseReference stringCheckRef,userRef;
    Lobby lobby;
    private String code;
    User user;
    private FirebaseUser user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        create = findViewById(R.id.create_button);
        join = findViewById(R.id.join_button);
        random = new Random();

        stringCheckRef = FirebaseDatabase.getInstance().getReference("Lobby");
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        makingList();

        //user = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()).getVa

        user1 = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(this, user1.getUid(), Toast.LENGTH_SHORT).show();




        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    if(item.getKey().equals(user1.getUid())){
                        user = item.getValue(User.class);
                        final String userCode = item.child("code").getValue(String.class);

                        stringCheckRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot item : dataSnapshot.getChildren()){
                                    if(item.getKey().equals(userCode)){
                                        Intent intent = new Intent(HomePageActivity.this, LobbyActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        String name = item.child("name").getValue(String.class);
                        Toast.makeText(HomePageActivity.this, user.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        validString();
                        lobby = new Lobby(code);
                        user.setLeader(true);
                        user.setCode(code);
                        userRef.child(FirebaseAuth.getInstance().getUid()).setValue(user);
                        stringCheckRef.child(code).setValue(lobby);
                        stringCheckRef.child(code).child("Member").child(user.getName()).setValue(user);

                        Intent intent = new Intent(HomePageActivity.this,LobbyActivity.class);
                        startActivity(intent);
                    }
                });

                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomePageActivity.this, JoinActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //i have changed
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void makingList(){
        wordArrayList = new ArrayList<String>();
        for(String word : (getString(R.string.word_collection)).split(" ")) {
            wordArrayList.add(word);
        }
    }
    void validString(){
        generateString();
        stringCheckRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    if(item.getKey().toString() == code){
                        validString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void generateString(){
        int randomInteger = random.nextInt(wordArrayList.size());
        int randomInteger2 = random.nextInt(wordArrayList.size());
        code = wordArrayList.get(randomInteger)+" " +wordArrayList.get(randomInteger2);
    }
}
