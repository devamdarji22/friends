package com.jadd.friends.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jadd.friends.R;
import com.jadd.friends.Classes.User;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText,passwordText,emailText;
    Button signUpButton;
    private User user1;
    private DatabaseReference signUpReference;
    private FirebaseUser ownerUser;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.user_activity_name);
        passwordText = findViewById(R.id.user_activity_password);
        signUpButton = findViewById(R.id.sign_up_activity_button);
        emailText = findViewById(R.id.user_activity_email);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ownerName = nameText.getText().toString();
                String ownerPassword = passwordText.getText().toString();
                String ownerEmail = emailText.getText().toString();
                user1 = new User(ownerName,ownerEmail);
                signUpReference = FirebaseDatabase.getInstance().getReference("Users");
                //signUpReference.child("x").setValue("x");


                //Toast.makeText(SignUpActivity.this, "OnCLick", Toast.LENGTH_SHORT).show();
                //repository = new Repository(user1,ownerPassword);
                //repository.signUp();

                auth.createUserWithEmailAndPassword(user1.getEmail(), ownerPassword)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user1's information
                                    final FirebaseUser user = auth.getCurrentUser();
                                    //signUpReference.child("a").setValue("x");
                                    user1.setUid(FirebaseAuth.getInstance().getUid());
                                    signUpReference.child(user.getUid()).setValue(SignUpActivity.this.user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user1.
                                    Toast.makeText(SignUpActivity.this, "SignUp failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                /*Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.putExtra("OWNER_NAME",ownerName);
                startActivity(intent);*/
            }
        });

    }
}
