package com.jadd.friends.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jadd.friends.R;

public class MainActivity extends AppCompatActivity {

    EditText nameField,passwordField;
    TextView signUpView;
    Button loginButton,signUpButton;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userName;
    private String userPassword;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.user_name);
        passwordField = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        signUpView = findViewById(R.id.register_text_view);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent intent;

                    intent = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(intent);

                    /*if(ownerFlag){
                        intent = new Intent(LoginActivity.this,RestaurantActivity.class);
                        startActivity(intent);
                    }
                    else {
                        intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("OWNER_FLAG",false);


                        //Toast.makeText(LoginActivity.this, "Employee", Toast.LENGTH_SHORT).show();
                        //intent = new Intent(LoginActivity.this,RestaurantActivity.class);
                    }*/
                    //startActivity(intent);
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameField.getText().toString();
                userPassword = passwordField.getText().toString();
                if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(userPassword)){
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(userName, userPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user1's information
                                        FirebaseUser user = auth.getCurrentUser();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
