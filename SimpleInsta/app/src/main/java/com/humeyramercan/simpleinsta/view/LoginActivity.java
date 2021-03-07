package com.humeyramercan.simpleinsta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.humeyramercan.simpleinsta.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailEditText, passwordEditText;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        Intent intent=getIntent();
        String info=intent.getStringExtra("info");

        if(info!=null && info.matches("newUser")){

        }else {
            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                intent = new Intent(LoginActivity.this, TimelineActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

        public void signIn (View view){

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            if (email.isEmpty() != true && password.isEmpty() != true) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(final AuthResult authResult) {
                        if( authResult.getUser().isEmailVerified()){
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, TimelineActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            AlertDialog.Builder alert =new AlertDialog.Builder(LoginActivity.this);
                            alert.setCancelable(false);
                            alert.setTitle("Unverified account");
                            alert.setMessage("This account is unverified.Would you like us to resend the activation code?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                        authResult.getUser().sendEmailVerification();
                                        Toast.makeText(LoginActivity.this, "Activation code sent again!", Toast.LENGTH_LONG).show();
                                }
                            });
                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alert.show();
                    }
                }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Please enter email or password!", Toast.LENGTH_LONG).show();
            }
        }
        public void signUp (View view){
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }

        public void passwordReset(View view){
            Intent intent=new Intent(LoginActivity.this, passwordResetActivity.class);
            startActivity(intent);

        }

}