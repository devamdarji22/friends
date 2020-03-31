package com.jadd.friends.LoopActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jadd.friends.Classes.Lobby;
import com.jadd.friends.Classes.Question;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = "OK";
    private static final int NUM_THREADS = 1;
    TextView questionView;
    EditText answerView;
    Button submitButton;
    String code;
    Question question = new Question();
    ArrayList<User> names = new ArrayList<>();
    Random random;
    String randomName;
    private DatabaseReference cancelRef = FirebaseDatabase.getInstance().getReference("Lobby")
            ,reference = FirebaseDatabase.getInstance().getReference("Lobby")
            ,databaseReference = FirebaseDatabase.getInstance().getReference("Lobby");
    private User user,u;
    private String randomQuestion;
    boolean once = true;
    private String randomName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        code = getIntent().getStringExtra("CODE");

        questionView = findViewById(R.id.question_view);
        answerView = findViewById(R.id.answer_view);
        submitButton = findViewById(R.id.answer_submit_button);
        listGenerator();

        Log.d(TAG, "onCreate: called");

        random = new Random();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer = answerView.getText().toString();
                if(answer==null){
                    Toast.makeText(QuestionActivity.this, "Answer is empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    u.setAnswer(answer);
                    u.setAnswered(true);
                    cancelRef.child(code).child("Member").child(u.getName()).setValue(u);
                    Intent intent = new Intent(QuestionActivity.this, CollectingAnswerActivity.class);
                    intent.putExtra("CODE", code);
                    intent.putExtra("QUESTION", randomQuestion);
                    startActivity(intent);
                    finish();
                }
            }
        });

        reference.child(code).child("Member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: called");
                names.clear();
                String uid = FirebaseAuth.getInstance().getUid();
                for (DataSnapshot player : dataSnapshot.getChildren()) {
                    user = player.getValue(User.class);
                    if (user.getUid().equals(uid)) {
                        u = user;
                    }
                    names.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(code).child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (u.isAnswered()) {
                    Intent intent = new Intent(QuestionActivity.this, CollectingAnswerActivity.class);
                    intent.putExtra("CODE", code);
                    randomQuestion = dataSnapshot.getValue(String.class);
                    intent.putExtra("QUESTION", randomQuestion);
                    startActivity(intent);
                    finish();
                } else {

                    if (u.isLeader() && dataSnapshot.getValue(String.class) == null) {
                        //DO LEADER STUFF
                        //Toast.makeText(QuestionActivity.this, String.valueOf(random.nextInt(names.size())), Toast.LENGTH_SHORT).show();
                        int random1 = random.nextInt(names.size());
                        int random2 = random.nextInt(names.size());
                        while (random1 == random2){
                            random2 = random.nextInt(names.size());
                        }
                        randomName = names.get(random.nextInt(names.size())).getName();
                        randomName2 = names.get(random.nextInt(names.size())).getName();
                        finalQuestion();
                        Lobby lobby = new Lobby(code, randomQuestion);
                        databaseReference.child(code).child("question").setValue(randomQuestion);
                        questionView.setText(randomQuestion);

                    } else {
                        //DO NORMAL STUFF

                        randomQuestion = dataSnapshot.getValue(String.class);
                        questionView.setText(randomQuestion);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //String randomQuestion = question.getQuestions().get(random.nextInt(question.questions.size())).replaceAll("[name]",randomName);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");

    }

    void finalQuestion(){
        randomQuestion = question.getQuestions().get(random.nextInt(question.getQuestions().size()))
                .replace("[name]",randomName).replace("[name2]",randomName2);
    }

    void listGenerator(){
        question.addQuestion("What is [name]'s petname ?");
        question.addQuestion("If [name] was the prime minister of India,What is the first thing they would have done?");
        question.addQuestion("[name] breaks a red signal for what?");
        question.addQuestion("For what talent [name] would have been called on ellen show?");
        question.addQuestion("What would have been a weirdest dream for [name]?");
        question.addQuestion("[name] and [name2] starts a band. What would have been the name of that band?");
        question.addQuestion("[name] would have cried for what reason?");
        question.addQuestion("What would have been the biggest fear of [name]?");
        question.addQuestion("[name] stopped a train he was travelling in, what could be the reason?");
        question.addQuestion("One thing [name] is good at?");
        question.addQuestion("[name] can  replace a character in a movie. Which character would he replace and which movie?");
        question.addQuestion("[name] opens up a new company.what could be the name of that company?");
        question.addQuestion("[name] is offered a tv series to play a role, which could it be?");
        question.addQuestion("What do u think [name] was in previous birth?");
        question.addQuestion("If one habit [name] had to forget what would it be?");
        question.addQuestion("An FIR has been filed against [name], what could it be for?");
        question.addQuestion("[name] would die for what?");
        question.addQuestion("What is [name]'s go-to pickup line?");
        question.addQuestion("What to you love the most about [name]?");
        question.addQuestion("If [name] invented a swim stroke,what would it be?");
        question.addQuestion("[name] is called away on a secret mission that only they can handle, what is it?");
        question.addQuestion("When drunk,[name] always...");
        question.addQuestion("What does [name] always bring to the party?");
        question.addQuestion("If [name] was a fruit, what would it be?");
        question.addQuestion("If [name] was a vegetable, what would it be?");
        question.addQuestion("If [name] was a car,which would it be?");
        question.addQuestion("Who could be [name]'s secret crush?");
        question.addQuestion("Suggest a perfect meme [name] can fit in?");
        question.addQuestion("If [name] was a dish, what could it be?");
        question.addQuestion("Any one weakness of [name]...");
        question.addQuestion("On which activity [name] spends most of the time?");
        question.addQuestion("[name] resemble to which cartoon character?");
        question.addQuestion("Which song do u dedicate to [name]?");
        question.addQuestion("What could be the perfect b'day gift for [name]'s next birthday?");
        question.addQuestion("If [name] gets a chance to meet a celeb,who could it be?");
        /*question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");
        question.addQuestion("");







        question.addQuestion("");*/

    }

}
