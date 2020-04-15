package com.example.restaurandapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {
    Button t1;
    Button t2;
    TextView table;
    Button t3;
    Button t4;
    Vibrator vib;
    Button t5;
    TextView text;
    Button save;
    int current=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.booking_fragment,container,false);
        t1=v.findViewById(R.id.t1);
        t2=v.findViewById(R.id.t2);
        table=v.findViewById(R.id.table);
        vib=(Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        t3=v.findViewById(R.id.t3);
        t4=v.findViewById(R.id.t4);
        t5=v.findViewById(R.id.t5);
        save=v.findViewById(R.id.book);
        text=v.findViewById(R.id.text);
        t1.setEnabled(true);
        t2.setEnabled(true);
        t3.setEnabled(true);
        t4.setEnabled(true);
        t5.setEnabled(true);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                text.setText("Capacity: 4");
                t1.setEnabled(true);
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                t1.setEnabled(false);
                table.setText("Table No: 1");
                current=1;
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                text.setText("Capacity: 4");
                t1.setEnabled(true);
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                t2.setEnabled(false);
                current=2;
                table.setText("Table No: 2");
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                text.setText("Capacity: 6");
                t1.setEnabled(true);
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                t3.setEnabled(false);
                table.setText("Table No: 3");
                current=3;
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                text.setText("Capacity: 8");
                t1.setEnabled(true);
                table.setText("Table No: 4");
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                t4.setEnabled(false);
                current=4;
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                text.setText("Capacity: 8");
                t1.setEnabled(true);
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                table.setText("Table No: 5");
                t5.setEnabled(false);
                current=5;
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                if (current == 0) {
                    Toast.makeText(getContext(),"Please select a table!",Toast.LENGTH_LONG).show();
                } else {
                    t1.setEnabled(true);
                    t2.setEnabled(true);
                    t3.setEnabled(true);
                    t4.setEnabled(true);
                    t5.setEnabled(true);
                    table.setText("Table No: ");
                    int temp=current;
                    current=0;
                    text.setText("Capacity: ");
                    Intent i = new Intent(getContext(), bookingFinalScreen.class);
                    i.putExtra("table", temp);
                    startActivity(i);
                }
            }
        });


        return v;
    }
}
