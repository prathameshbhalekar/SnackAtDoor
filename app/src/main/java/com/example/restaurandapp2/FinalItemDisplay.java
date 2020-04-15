package com.example.restaurandapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class FinalItemDisplay extends AppCompatActivity {

    ImageView image;
    ImageView veg;
    String itemname;
    String itemCount="1";
    ImageView nonveg;
    TextView name;
    TextView des;
    Button add;
    Spinner s;
    Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_item_display);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        image=findViewById(R.id.imagei);
        veg=findViewById(R.id.vegi);
        nonveg=findViewById(R.id.nonvegi);
        name=findViewById(R.id.namei);
        des=findViewById(R.id.desi);
        add=findViewById(R.id.addi);
        s=findViewById(R.id.spinneri);
        String[]array={"1","2","3","4","5"};
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        Intent i=getIntent();
        if(i.getExtras()!=null){
            Picasso.with(this)
                    .load(i.getStringExtra("url"))
                    .fit()
                    .centerCrop()
                    .into(image);
            itemname=(i.getStringExtra("name"));
            name.setText(itemname);
            des.setText(i.getStringExtra("des"));
            String type=i.getStringExtra("type1");

            if(type.equals("NON-VEG")){
                veg.setVisibility(View.INVISIBLE);
            }
            else{
                nonveg.setVisibility(View.INVISIBLE);
          }
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    itemCount=Integer.toString(position+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {}

            });



            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vib.vibrate(50);
                    Toast.makeText(FinalItemDisplay.this,"Item Added to cart!",Toast.LENGTH_SHORT).show();
                    final SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(FinalItemDisplay.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    Set set =preferences.getStringSet("orders",null);
                    String s=(itemname+","+itemCount);
                    if(set==null){
                        set = new HashSet<String>();
                        set.add(s);
                        editor.putStringSet("orders",set);
                        editor.commit();
                    }
                    else{
                        set.add(s);
                        editor.putStringSet("orders",set);
                        editor.commit();
                    }


                }
            });



       }
    }

}
