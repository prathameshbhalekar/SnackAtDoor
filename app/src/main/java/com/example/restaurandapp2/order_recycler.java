package com.example.restaurandapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class order_recycler extends AppCompatActivity {
    private List<Order> mUploads1;
    private SharedPreferences preferences;
    DatabaseReference mDatabaseRef;
    private ProgressBar progress;
    private RecyclerView mRecyclerView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recycler);
        mRecyclerView1=findViewById(R.id.order_recycler);
        mRecyclerView1.setHasFixedSize(true);
        progress = findViewById(R.id.progress2);
        order_recycler.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        LinearLayoutManager mm=new LinearLayoutManager(order_recycler.this);
        mm.setOrientation(RecyclerView.VERTICAL);
        progress.setVisibility(View.VISIBLE);
        mRecyclerView1.setLayoutManager(mm);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("orders");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads1 = new ArrayList<>();
                preferences = PreferenceManager.getDefaultSharedPreferences(order_recycler.this);
                String address=preferences.getString("address",null);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order up = postSnapshot.getValue(Order.class);
                    if(address.equals(up.address)){
                        mUploads1.add(up);
                    }
                }
//                adapterOrders mAdapter = new adapterOrders((ArrayList<Order>) mUploads1,order_recycler.this);
//
//
//                mRecyclerView1.setAdapter(mAdapter);
                progress.setVisibility(View.INVISIBLE);
                order_recycler.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                SnapHelper snapHelper1 = new LinearSnapHelper();
                snapHelper1.attachToRecyclerView(mRecyclerView1);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(order_recycler.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
