package com.example.crime;

import android.os.Bundle;
import android.os.Message;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class MainActivity4 extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        emailEditText = findViewById(R.id.editTextText2);
        resetPasswordButton = findViewById(R.id.button3);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = emailEditText.getText().toString().trim();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(MainActivity4.this,"send email", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(MainActivity4.this,"Fail",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });
    }
}
