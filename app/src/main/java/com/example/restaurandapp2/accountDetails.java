package com.example.restaurandapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class accountDetails extends AppCompatActivity {
    private Vibrator vib;
    private Button email;
    private Button linkedin;
    private  Button github;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        email=findViewById(R.id.email);
        linkedin=findViewById(R.id.linkedin);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        github=findViewById(R.id.github);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "prathameshbhalekar13@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
                accountDetails.this.startActivity(Intent.createChooser(emailIntent, null));
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                String url = "https://www.linkedin.com/in/prathamesh-bhalekar-b56038197/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                String url = "https://github.com/prathameshbhalekar";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
