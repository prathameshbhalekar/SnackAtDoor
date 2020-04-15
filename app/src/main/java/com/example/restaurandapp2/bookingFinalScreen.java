package com.example.restaurandapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class bookingFinalScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button setDate;
    ProgressBar progress;
    Button setTime;
    Spinner Time;
    Button save;
    ArrayList<String> list;
    TextView Showtime;
    int table=0;
    boolean b1=false;
    boolean b2=false;
    private DatabaseReference mDatabaseRef;
    String timefor="0";
    TextView Showdate;
    private Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_final_screen);
        progress=findViewById(R.id.loading1);
        progress.setVisibility(View.INVISIBLE);
        vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setDate=findViewById(R.id.setdate);
        setTime=findViewById(R.id.settime);
        Time=findViewById(R.id.getTime);
        save=findViewById(R.id.save);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bookings");

        list=new ArrayList<>();
        list.add("30");
        list.add("60");
        list.add("90");
        list.add("120");
        list.add("150");
        list.add("180");
        list.add("210");
        list.add("240");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        Time.setAdapter(spinnerArrayAdapter);
        Intent i=getIntent();
        if(i.getExtras()!=null){
            table=i.getIntExtra("table",0);
        }
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                DialogFragment picker=new datePicker();
                picker.show(getSupportFragmentManager(),"date picker");
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                DialogFragment timer=new timePicker();
                timer.show(getSupportFragmentManager(),"time picker");
            }
        });
        Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timefor=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                if(!b1||!b2){
                    Toast.makeText(bookingFinalScreen.this,"Please select date and time.",Toast.LENGTH_LONG).show();
                }
                else {
                    new AlertDialog.Builder(bookingFinalScreen.this)
                            .setTitle("Confirm Booking")
                            .setMessage("Are you sure you want to confirm the booking.?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    progress.setVisibility(View.VISIBLE);
                                    bookingFinalScreen.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    String date = Showdate.getText().toString();
                                    String time = Showtime.getText().toString();
                                    String uploadId = mDatabaseRef.push().getKey();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(bookingFinalScreen.this);
                                    String name = preferences.getString("name", "");
                                    bookingUpload upload = new bookingUpload(uploadId, name, Integer.toString(table), date, time, timefor, false);
                                    mDatabaseRef.child(uploadId).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progress.setVisibility(View.INVISIBLE);
                                            bookingFinalScreen.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(bookingFinalScreen.this, "success", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String current= DateFormat.getDateInstance().format(c.getTime());
        Showdate=findViewById(R.id.textView);
        Showdate.setText(current);
        b2=true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Showtime=findViewById(R.id.time);
        b1=true;
        Showtime.setText(hourOfDay+":"+minute);
    }
}
