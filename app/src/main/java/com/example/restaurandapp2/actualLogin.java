package com.example.restaurandapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class actualLogin extends AppCompatActivity {
    Button Create;
    EditText password;
    EditText confirmPassword;
    Vibrator vib;
    EditText email;
    ProgressBar progress;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_login);
        Create=findViewById(R.id.create);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        password=findViewById(R.id.login_password);
        confirmPassword=findViewById(R.id.confirm_login_password);
        email=findViewById(R.id.login_email);
        progress=findViewById(R.id.progress);
        auth=FirebaseAuth.getInstance();
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                String em=email.getText().toString();
                String pass=password.getText().toString();
                String con=confirmPassword.getText().toString();
                if(em.equals("")||pass.equals("")||con.equals("")){
                    Toast.makeText(actualLogin.this,"Empty Field!",Toast.LENGTH_LONG).show();
                }
                else if(!pass.equals(con)){
                    Toast.makeText(actualLogin.this,"Passwords don't match",Toast.LENGTH_LONG).show();
                }
                else if(pass.length()<6){
                    Toast.makeText(actualLogin.this,"Password 7 Character long!",Toast.LENGTH_LONG).show();
                }
                else {
                    progress.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(em, pass)
                            .addOnCompleteListener(actualLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    progress.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnSuccessListener(actualLogin.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    finish();
                                    startActivity(new Intent(actualLogin.this, UserInfo.class));
                                }
                            })
                            .addOnFailureListener(actualLogin.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(actualLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        });
    }
}
