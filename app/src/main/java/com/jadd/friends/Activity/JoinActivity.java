package com.jadd.friends.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.LoopActivities.LobbyActivity;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {

    EditText t1,t2,t3,t4,t5,t6,t7,t8;
    ArrayList<EditText> arrayList;
    Button button;
    StringBuilder stringBuilder;
    DatabaseReference lobbyReference = FirebaseDatabase.getInstance().getReference("Lobby")
            ,userReference = FirebaseDatabase.getInstance().getReference("Users");
    String code;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        t1 = findViewById(R.id.editText1);
        t2 = findViewById(R.id.editText2);
        t3 = findViewById(R.id.editText3);
        t4 = findViewById(R.id.editText4);
        t5 = findViewById(R.id.editText5);
        t6 = findViewById(R.id.editText6);
        t7 = findViewById(R.id.editText7);
        t8 = findViewById(R.id.editText8);
        button = findViewById(R.id.button);

        changeFocus(t1,t2);
        changeFocus(t2,t3);
        changeFocus(t3,t4);
        changeFocus(t4,t5);
        changeFocus(t5,t6);
        changeFocus(t6,t7);
        changeFocus(t7,t8);








        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeGenerater();
                lobbyReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            if(code.equals(item.getKey())){

                                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        user = dataSnapshot.child(FirebaseAuth.getInstance().getUid())
                                                .getValue(User.class);
                                        user.setLeader(false);
                                        user.setAnswered(false);
                                        user.setReady(false);
                                        userReference.child(FirebaseAuth.getInstance().getUid()).child("code")
                                                .setValue(code);
                                        lobbyReference.child(code).child("Member").child(user.getName())
                                                .setValue(user);
                                        Intent intent = new Intent(JoinActivity.this, LobbyActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    private void changeFocus(final EditText t1,final EditText t2) {
        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(t1.getText().toString().trim().length() >=1){
                    t1.clearFocus();
                    t2.requestFocus();
                }
            }
        });
    }

    void codeGenerater(){
        code = "";
        String a = t1.getText().toString();
        code = code + a;
        a = t2.getText().toString();
        code = code + a;
        a = t3.getText().toString();
        code = code + a;

        a = t4.getText().toString();
        code = code + a;
        code = code + " ";
        a = t5.getText().toString();
        code = code + a;
        a = t6.getText().toString();
        code = code + a;
        a = t7.getText().toString();
        code = code + a;
        a = t8.getText().toString();
        code = code + a;
        Toast.makeText(this,code , Toast.LENGTH_SHORT).show();




        /*for(int i = 0;i<7;i++){
            char a = arrayList.get(i).getText().charAt(0);
            if(i!=4){

            }
            else {
                stringBuilder.append(" ");
            }
        }
        code = stringBuilder.toString();
        Toast.makeText(this,code , Toast.LENGTH_SHORT).show();*/
    }
}
