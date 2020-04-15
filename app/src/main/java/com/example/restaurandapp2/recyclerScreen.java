package com.example.restaurandapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class recyclerScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Uploder> mUploads;
    private String type;
    private ProgressBar progress;
    private boolean call;
    private Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_screen);
        progress=findViewById(R.id.lo);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        call=getIntent().getBooleanExtra("call",false);
        type =getIntent().getExtras().getString("type");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploder upload = postSnapshot.getValue(Uploder.class);
                    if(call==false){
                        if(upload.getType().equals(type)){
                            mUploads.add(upload);
                        }
                    }
                    if(call==true){
                        if(upload.getType2().equals(type)){
                            mUploads.add(upload);
                        }
                    }
                }

                mAdapter = new Adapter(recyclerScreen.this, mUploads);
                mAdapter. setLisner(new Adapter.onClickListner() {
                    @Override
                    public void onItemClicked(int pos) {
                        vib.vibrate(50);
                        Uploder u=mUploads.get(pos);
                        String name=u.getName();
                        String des=u.getDescription();
                        String type1=u.getType();
                        String type2=u.getType2();
                        String url=u.getUrl();
                        Intent i=new Intent(recyclerScreen.this,FinalItemDisplay.class);
                        i.putExtra("name",name);
                        i.putExtra("des",des);
                        i.putExtra("type1",type1);
                        i.putExtra("type2",type2);
                        i.putExtra("url",url);
                        startActivity(i);

                    }
                });

                mRecyclerView.setAdapter(mAdapter);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progress.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(recyclerScreen.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });







    }
}
