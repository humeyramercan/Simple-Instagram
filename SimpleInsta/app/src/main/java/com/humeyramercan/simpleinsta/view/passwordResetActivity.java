package com.humeyramercan.simpleinsta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.humeyramercan.simpleinsta.R;

public class passwordResetActivity extends AppCompatActivity {

    EditText emailEditText;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        emailEditText=findViewById(R.id.emailEditText);
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public void sendEmail (View view){
        String email=emailEditText.getText().toString();
        if(email.isEmpty()!=true){
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(passwordResetActivity.this,"Password reset email sent!",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(passwordResetActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(passwordResetActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}