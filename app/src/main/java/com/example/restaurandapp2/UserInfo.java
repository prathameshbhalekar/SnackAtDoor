package com.example.restaurandapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.prefs.PreferenceChangeEvent;

public class UserInfo extends AppCompatActivity {
    private Button save;
    private EditText name;
    private EditText address;
    private EditText phone;
    private FirebaseAuth auth;
    private DatabaseReference db;
    Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        save=findViewById(R.id.save);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        auth= FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference("users");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                if (address.getText().equals("") || name.getText().equals("") || phone.getText().equals("")) {
                    Toast.makeText(UserInfo.this, "Empty Field", Toast.LENGTH_LONG);
                }
                else {
                    user newUser = new user(auth.getCurrentUser().getEmail(), auth.getCurrentUser().getUid(), address.getText().toString(), name.getText().toString(), phone.getText().toString());
                    String id = db.push().getKey();
                    db.child(id).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserInfo.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("name", name.getText().toString());
                            editor.putString("address", address.getText().toString());
                            editor.putString("phone", phone.getText().toString());
                            editor.commit();
                            startActivity(new Intent(UserInfo.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });

    }
}
