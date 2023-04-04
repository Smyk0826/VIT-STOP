
package com.example.vitstop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private  EditText mConfirm;
    private TextView RegBtn;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mName = findViewById(R.id.inputpass);
        mEmail = findViewById(R.id.inpuEmail);
        mPassword = findViewById(R.id.editTextTextPassword2);
        mConfirm = findViewById(R.id.editTextTextPassword3);
        RegBtn = findViewById(R.id.textView7);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.Reg_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name  = mName.getText().toString();
                String email  = mEmail.getText().toString();
                String password  = mPassword.getText().toString();
                String Cpassword  = mConfirm.getText().toString();
                if(!name.isEmpty()&&!email.isEmpty()&&!password.isEmpty()&&!Cpassword.isEmpty()){
                    if(!password.equals(Cpassword)){
                        Toast.makeText(SignIn.this,"Passwords mismatch!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignIn.this,"Account successfully created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this,HomeActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(SignIn.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(SignIn.this,"Please fill the empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}