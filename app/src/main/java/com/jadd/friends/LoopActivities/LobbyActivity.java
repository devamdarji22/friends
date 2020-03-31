package com.jadd.friends.LoopActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.Activity.HomePageActivity;
import com.jadd.friends.Classes.Question;
import com.jadd.friends.DataLoadListener;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;
import com.jadd.friends.ViewModel.NameListViewModel;
import com.jadd.friends.adapter.LobbyRecyclerViewAdapter;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity implements LobbyRecyclerViewAdapter.OnPlayerKickClickListner
        , LobbyRecyclerViewAdapter.OnPlayerReadyClickListner, DataLoadListener {

    DatabaseReference cancelRef = FirebaseDatabase.getInstance().getReference("Lobby")
            ,userRef = FirebaseDatabase.getInstance().getReference("Users");
    private String code;
    private String name;
    TextView codeView;
    RecyclerView lobbyRecyclerView;
    LobbyRecyclerViewAdapter lobbyRecyclerViewAdapter;
    ArrayList<User> names = new ArrayList<>();
    boolean leader = false;
    private User u;

    NameListViewModel nameListViewModel;
    private User leaderUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        codeView = findViewById(R.id.code_view);
        lobbyRecyclerView = findViewById(R.id.lobby_recycler_view);
        lobbyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        nameListViewModel = new ViewModelProvider(LobbyActivity.this).get(NameListViewModel.class);

        nameListViewModel.init(LobbyActivity.this);

        names = nameListViewModel.getNames().getValue();

        for (int i=0;i<names.size();i++){
            if(names.get(i).isLeader()){
                leaderUser =  names.get(i);
            }
        }

        Toast.makeText(this, leaderUser.getCode(), Toast.LENGTH_SHORT).show();
        codeView.setText(leaderUser.getCode());

        //leaderUser = nameListViewModel.getLeader();

        if(FirebaseAuth.getInstance().getUid().equals(leaderUser.getUid())){
            leader = true;
        }
        else {
            leader = false;
        }

        lobbyRecyclerViewAdapter = new LobbyRecyclerViewAdapter(
                LobbyActivity.this,names
                ,LobbyActivity.this,LobbyActivity.this,
                leader
        );
        lobbyRecyclerView.setAdapter(lobbyRecyclerViewAdapter);

        /*final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    if (item.getKey().equals(user.getUid())){
                        code = item.child("code").getValue(String.class);
                        name = item.child("name").getValue(String.class);
                    }
                }
                codeView.setText(code);
                cancelRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean check = false;
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            if(item.getKey().equals(code)){
                                names.clear();


                                for(DataSnapshot player : item.child("Member").getChildren()){
                                    User user = player.getValue(User.class);
                                    names.add(user);
                                    if(user.getName().equals(name)){
                                        u = user;
                                        leader = user.isLeader();
                                        check = true;
                                    }
                                }

                                lobbyRecyclerViewAdapter = new LobbyRecyclerViewAdapter(
                                        LobbyActivity.this,names,LobbyActivity.this,LobbyActivity.this,
                                        leader
                                );
                                lobbyRecyclerView.setAdapter(lobbyRecyclerViewAdapter);

                                if(u.isCompleted()){
                                    Intent intent = new Intent(LobbyActivity.this, QuestionActivity.class);
                                    intent.putExtra("CODE",code);
                                    startActivity(intent);
                                    finish();
                                }
                                if(u.isAnswered()){
                                    Intent intent = new Intent(LobbyActivity.this, QuestionActivity.class);
                                    intent.putExtra("CODE",code);
                                    startActivity(intent);
                                    finish();
                                }

                                boolean notStart = false;
                                for(int i=0;i<names.size();i++){
                                    if(!names.get(i).isReady()){
                                        notStart = true;
                                    }
                                }
                                if(!notStart&&names.size()>=2){
                                    Intent intent = new Intent(LobbyActivity.this, QuestionActivity.class);
                                    intent.putExtra("CODE",code);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        if(!check){
                            Intent intent = new Intent(LobbyActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lobby_menu, menu);



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
        if (id == R.id.action_cancel) {

            //cancelFunc(id);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*void cancelFunc(final int id){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    if (item.getKey().equals(user.getUid())){
                        code = item.child("code").getValue(String.class);
                        name = item.child("name").getValue(String.class);
                    }
                }
                cancelRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if(id == R.id.action_cancel) {
                                boolean leader = (Boolean) dataSnapshot.child(code).child("Member").child(name).child("leader").getValue();
                                if (leader) {
                                    cancelRef.child(code).removeValue();
                                    userRef.child(FirebaseAuth.getInstance().getUid()).child("code").removeValue();
                                    Intent intent = new Intent(LobbyActivity.this, HomePageActivity.class);
                                    startActivity(intent);
                                } else {
                                    cancelRef.child(code).child("Member").child(name).removeValue();
                                    userRef.child(FirebaseAuth.getInstance().getUid())
                                            .child("code").removeValue();
                                    Intent intent = new Intent(LobbyActivity.this, HomePageActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        catch (NullPointerException e){
                            Toast.makeText(LobbyActivity.this, "Wait", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public void onPlayerKickClick(int pos) {
        cancelRef.child(code).child("Member").child(names.get(pos).getName()).removeValue();
        userRef.child(names.get(pos).getUid()).child("code").removeValue();
    }

    @Override
    public void onPlayerReadyClick(int pos) {
        Toast.makeText(this, names.get(pos).getName(), Toast.LENGTH_SHORT).show();
        User cur = names.get(pos);
        if(names.get(pos).isReady()){
            cur.setReady(false);
            cancelRef.child(code).child("Member").child(names.get(pos).getName()).setValue(cur);
        }
        else {
            cur.setReady(true);
            cancelRef.child(code).child("Member").child(names.get(pos).getName()).setValue(cur);
        }

        /*cancelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User cur = dataSnapshot.child(code).child("Member").child(names.get(pos).getName()).getValue(User.class);
                if(cur.isReady()){
                    cancelRef.child(code).child("Member").child(cur.getName()).child("ready").setValue(false);
                }
                else {
                    cancelRef.child(code).child("Member").child(cur.getName()).child("ready").setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }


    @Override
    public void onNameLoaded() {
        nameListViewModel.getNames().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                lobbyRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
