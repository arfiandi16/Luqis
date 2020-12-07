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

public class Register extends AppCompatActivity {
    //visitor&Seller
    EditText vName,vEmail,vPassword;
    Button btnRegister;
    TextView lnkLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        vName = findViewById(R.id.editTextTextPersonName);
        vEmail = findViewById(R.id.editTextTextEmailAddress);
        vPassword = findViewById(R.id.editTextTextPassword);
        btnRegister = findViewById(R.id.btnLogin);
        lnkLogin = findViewById(R.id.lnkLogin);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = vName.getText().toString();
                String Email = vEmail.getText().toString();
                String Pass= vPassword.getText().toString();

                if (TextUtils.isEmpty(Username)){
                    vName.setError("username is required");
                    return;
                }
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

                fAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(Register.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    }
                });

            }
        }


        );

    }
    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}