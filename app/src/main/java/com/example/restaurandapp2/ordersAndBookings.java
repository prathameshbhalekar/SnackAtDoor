package com.example.restaurandapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ordersAndBookings extends AppCompatActivity {

    private SharedPreferences preferences;
    private ProgressBar progress;

    DatabaseReference mDatabaseRef;
    private List<bookingUpload> mUploads;
    private RecyclerView mRecyclerView;

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_and_bookings);
        ordersAndBookings.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mRecyclerView = findViewById(R.id.booking_recycler);
        mRecyclerView.setHasFixedSize(true);
        progress = findViewById(R.id.progress2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ordersAndBookings.this));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bookings");
        progress.setVisibility(View.VISIBLE);
        preferences = PreferenceManager.getDefaultSharedPreferences(ordersAndBookings.this);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads = new ArrayList<>();

                String name = preferences.getString("name", null);
                System.out.println("value is " + name);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    bookingUpload up2 = postSnapshot.getValue(bookingUpload.class);
                    System.out.println("value is " + up2.getName());
                    if (name.trim().equals(up2.getName().trim())) {
                        mUploads.add(up2);
                    }
                }

                adapter1 mAdapter = new adapter1((ArrayList<bookingUpload>) mUploads);

                ordersAndBookings.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mRecyclerView.setAdapter(mAdapter);
//                SnapHelper snapHelper = new LinearSnapHelper();
//                snapHelper.attachToRecyclerView(mRecyclerView);
                progress.setVisibility(View.INVISIBLE);
                ordersAndBookings.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//
    }
}
