package com.example.restaurandapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button nnnn;
    Vibrator vib;
    Button reset;
    DatabaseReference db;
    Button login;
    ProgressBar progress;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reset=findViewById(R.id.reset);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        progress=findViewById(R.id.progress);
        login=findViewById(R.id.llll);
        nnnn=findViewById(R.id.nnnn);
        vib= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        auth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference("users");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Login.this)
                        .setTitle("Reset Password")
                        .setMessage("Do you really want to reset Password?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(!"".equals(email.getText())){
                                    auth.sendPasswordResetEmail(email.getText().toString());
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });
        nnnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                startActivity(new Intent(Login.this,actualLogin.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                final String em=email.getText().toString();
                String pass=password.getText().toString();
                if(em.equals("")){
                    Toast.makeText(Login.this,"Please enter Email ID",Toast.LENGTH_LONG).show();
                }
                else if(pass.equals("")){
                    Toast.makeText(Login.this,"Please enter Password",Toast.LENGTH_LONG).show();
                }
                else {
                    progress.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progress.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Success", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                                        db.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    user upload = postSnapshot.getValue(user.class);
                                                    if (upload.getEmail().trim().equals(em.trim())) {
                                                        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("name", upload.getName());
                                                        editor.putString("address", upload.getAddress());
                                                        editor.putString("phone", upload.getPhone());
                                                        editor.commit();
                                                        startActivity(new Intent(Login.this, MainActivity.class));
                                                        finish();
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(Login.this,"Please Enter a valid Account.",Toast.LENGTH_LONG).show();
                                        reset.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                    );
                }
            }
        });


    }
}
