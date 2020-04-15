package com.example.restaurandapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import io.opencensus.metrics.LongGauge;

public class HomeFragment extends Fragment {
    Button veg;
    Button nonveg;
    Button desert;
    Button b;
    AutoCompleteTextView search;
    private DatabaseReference mDatabaseRef;
    private List<String> mUploads;
    private List<Uploder> all;
    private Button pizza;
    private Button fries;
    private Button shake;
    private Button burger;
    private Button icecream;
    private Button cake;
    private ProgressBar progress;
    private Vibrator vib;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vib=(Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        View v=inflater.inflate(R.layout.home_fragment,container,false);
        progress=v.findViewById(R.id.loading);
        veg=v.findViewById(R.id.veg);
        search=v.findViewById(R.id.search);
        nonveg=v.findViewById(R.id.nonveg);
        desert=v.findViewById(R.id.desert);
        b=v.findViewById(R.id.button);
        pizza=v.findViewById(R.id.pizza);
        fries=v.findViewById(R.id.fries);
        shake=v.findViewById(R.id.shakes);
        burger=v.findViewById(R.id.burger);

        icecream=v.findViewById(R.id.icecream);
        cake=v.findViewById(R.id.cake);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        progress.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads = new ArrayList<>();
                all=new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Uploder upload = postSnapshot.getValue(Uploder.class);
                    all.add(upload);
                    mUploads.add(upload.getName());
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mUploads);
                    search.setAdapter(adapter);
                    progress.setVisibility(View.INVISIBLE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vib.vibrate(50);
                        String query=search.getText().toString();
                        if(query.equals("")){
                            Toast.makeText(getContext(),"Please enter text!",Toast.LENGTH_LONG);
                        }
                        else{
                            int check=0;
                            Uploder found=all.get(0);
                            for(int i=0;i<all.size();i++){
                                if(query.equals(all.get(i).getName())){
                                    check=1;
                                    found=all.get(i);
                                    break;
                                }
                            }
                            if(check==0){
                                Toast.makeText(getContext(),"Sorry! not found.",Toast.LENGTH_LONG);
                            }
                            else{
                                Intent i=new Intent(getActivity(),FinalItemDisplay.class);
                                i.putExtra("url",found.getUrl());
                                i.putExtra("name",found.getName());
                                i.putExtra("des",found.getDescription());
                                i.putExtra("type1",found.getType());
                                i.putExtra("type2",found.getType2());
                                startActivity(i);



                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("VEG",false);
            }
        });
        nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("NON-VEG",false);
            }
        });
        desert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("DESERT",false);
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Pizza",true);
            }
        });
        fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Fries",true);
            }
        });
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Shakes",true);
            }
        });
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Burgers",true);
            }
        });
        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Icecream",true);
            }
        });
        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition("Cakes",true);
            }
        });

//        System.out.println("value is "+mUploads.get(3));


        return v;

    }
    void transition(String type,boolean call){
        vib.vibrate(50);
        Intent i=new Intent(getActivity(),recyclerScreen.class);
        i.putExtra("type",type);
        i.putExtra("call",call);
        startActivity(i);
    }
}
