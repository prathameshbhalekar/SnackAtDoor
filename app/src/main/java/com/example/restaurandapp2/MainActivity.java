package com.example.restaurandapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floating;
    FirebaseAuth auth;
    private Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        auth=FirebaseAuth.getInstance();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        Set<String> ns=new HashSet<>();
        editor.putStringSet("orders",ns);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            finish();
            startActivity(new Intent(MainActivity.this,Login.class));
        }
        floating =findViewById(R.id.floating);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();
        BottomNavigationView nav=findViewById(R.id.bottomNavigationView);
        nav.setOnNavigationItemSelectedListener(listener);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                Intent i=new Intent(MainActivity.this,billActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        vib.vibrate(50);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        vib.vibrate(50);
        switch (item.getItemId()){
            case R.id.id:
                startActivity(new Intent(this,accountDetails.class));
                return true;

            case R.id.dev:
                startActivity(new Intent(this,accountDetails.class));
                return  true;

            case R.id.menu_contact:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "abc@gmail.com", null));
                this.startActivity(Intent.createChooser(emailIntent, null));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            vib.vibrate(50);
            Fragment selected=null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selected=new HomeFragment();
                    break;
                case R.id.nav_booking:
                    selected=new BookingFragment();
                    break;
                case R.id.nav_offers:
                    selected=new OfferFragment();
                    break;
                case R.id.nav_profile:
                    selected=new profileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,selected).commit();
            return true;
        }
    };
}
