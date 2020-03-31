package com.jadd.friends.Repository;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.Classes.User;
import com.jadd.friends.DataLoadListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class Repo {

    static Context context;
    static DataLoadListener dataLoadListener;
    static Repo instance;
    private ArrayList<User> names= new ArrayList<>();
    static String code = "";
    private User user;
    private boolean leader = false;

    public static Repo getInsatnce(Context context1){
        context = context1;
        if(instance == null){
            instance= new Repo();

        }
        dataLoadListener = (DataLoadListener) context;
        return instance;
    }

    public MutableLiveData<ArrayList<User>> getNames(){
        loadCode();
        if(names.size()==0) {

            loadNames();
        }



        MutableLiveData<ArrayList<User>> nameList = new MutableLiveData<>();
        nameList.setValue(names);

        return nameList;
    }

    private void loadCode() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final String uid = FirebaseAuth.getInstance().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(uid).getValue(User.class);
                code = user.getCode();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loadNames() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lobby");
        names.clear();
        reference.child(code).child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot player : dataSnapshot.getChildren()){
                    User u = player.getValue(User.class);
                    names.add(u);
                }

                dataLoadListener.onNameLoaded();
                Toast.makeText(context, String.valueOf(names.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public User getLeader() {
        loadNames();
        for (int i=0;i<names.size();i++){
            if(names.get(i).isLeader()){
                return names.get(i);
            }
        }
        return null;

    }

}
