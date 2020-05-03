package com.example.bcs421homework3;

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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUsernameEditText = findViewById(R.id.editTextUsername);
        mPasswordEditText = findViewById(R.id.editTextPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                            intent.putExtra("userInputEmail", email);
                            startActivity(intent);

                        }
                        else{
                            String fail = getResources().getString(R.string.failToast);
                            Toast.makeText(getApplicationContext(),fail, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}
