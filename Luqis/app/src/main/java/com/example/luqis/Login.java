package com.example.luqis;

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

public class Login extends AppCompatActivity {
    EditText vEmail,vPassword;
    Button btnLogin;
    TextView lnkRegister;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        vEmail = findViewById(R.id.editTextTextEmailAddress);
        vPassword = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lnkRegister = findViewById(R.id.lnkLogin);

        fAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = vEmail.getText().toString().trim();
                String Pass= vPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    vEmail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(Pass)){
                    vPassword.setError("password is required");
                    return;
                }
                if (Pass.length()<8){
                    vPassword.setError("password must be 8 characters or longger ");
                    return;
                }

                fAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }



}