package com.example.restaurandapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.Sets;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class billActivity extends AppCompatActivity {
    RecyclerView recycler;
    FloatingActionButton order;
    TextView tt;
    ProgressBar progress;
    private billAdapter madapter;
    Set set;
    private Vibrator vib;
    private RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        order=findViewById(R.id.order);
        progress=findViewById(R.id.progress1);
        tt=findViewById(R.id.tt);
        recycler=findViewById(R.id.recycler2);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        set =preferences.getStringSet("orders",null);
        String s[]=new String[set.size()];
        set.toArray(s);
        final ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<s.length;i++){
            list.add(s[i]);
        }
        if(list.isEmpty()){
            tt.setText("Your Cart Is Empty!");
        }
        recycler.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        madapter=new billAdapter(list);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(madapter);
        madapter. setLisner(new billAdapter.onClickListner() {
            @Override
            public void onItemClicked(final int pos) {
                vib.vibrate(50);
                new AlertDialog.Builder(billActivity.this)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                list.remove(pos);
                                madapter.notifyItemRemoved(pos);
                                Set<String> newS=new HashSet<String>();
                                final SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(billActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();

                                Set k=new HashSet<String>();
                                for(int i=0;i<list.size();i++){
                                    k.add(list.get(i));
                                }

                                editor.putStringSet("orders",k );
                                editor.commit();

                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                progress.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(billActivity.this);
                String name=preferences.getString("name",null);
                String address=preferences.getString("address",null);
                String phone=preferences.getString("phone",null);
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String ss="";
                if(list.size()==1){
                    ss=list.get(0);
                }
                else {
                    for (int i = 0; i < list.size(); i++) {
                        ss = ss + list.get(i);
                        ss = ss + "_";

                    }
                }
                System.out.println("value is "+ss);
                if(list.size()!=0&&!list.isEmpty()) {
                    DatabaseReference db=FirebaseDatabase.getInstance().getReference("orders");
                    String id=db.push().getKey();
                    Order newOrder = new Order(id,name, phone, currentTime, ss, address, false);
                    System.out.println("Order is"+ss);
                    db.child(id).setValue(newOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(billActivity.this,"Order Placed Succesfully!",Toast.LENGTH_LONG).show();
                        }
                    });
                    SharedPreferences.Editor editor=preferences.edit();
                    Set <String>ns=new HashSet<>();
                    editor.putStringSet("orders",ns);
                    editor.commit();
                    int size=list.size();
                    list.clear();
                    madapter.notifyItemRangeRemoved(0,size);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                else{
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(billActivity.this,"Your cart is empty!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
