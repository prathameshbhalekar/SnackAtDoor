package com.example.restaurandapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {
    private TextView Name;
    private TextView Address;
    private TextView Phone;
    private Button LogOut;
    private Button reset;
    private TextView Email;
    private Button bookings;
    private Button orders;
    Vibrator vib;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile_fragment,container,false);
        auth=FirebaseAuth.getInstance();
        final FirebaseUser user=auth.getCurrentUser();
        Name=v.findViewById(R.id.details_name);
        LogOut=v.findViewById(R.id.logout);
        bookings=v.findViewById(R.id.showbookings);
        reset=v.findViewById(R.id.button3);
        Address=v.findViewById(R.id.details_address);
        Phone=v.findViewById(R.id.details_phone);
        Email=v.findViewById(R.id.details_email);
        vib=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        final String name=preferences.getString("name",null);
        String address=preferences.getString("address",null);
        String phone=preferences.getString("phone",null);
        Name.setText(name);
        Address.setText(address);
        Phone.setText(phone);
        Email.setText(user.getEmail());
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                new AlertDialog.Builder(getContext())
                        .setTitle("Reset")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Do you really eant to Reset Password")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.sendPasswordResetEmail(user.getEmail());
                            }
                        }).setNegativeButton(android.R.string.no,null).show();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                new AlertDialog.Builder(getContext())
                        .setTitle("Log Out")
                        .setMessage("Do you really want to Log Out?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                auth.signOut();
                                Intent i=new Intent(getContext(),Login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("EXIT", true);
                                startActivity(i);
                                getActivity().finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(50);
                startActivity(new Intent(getContext(),ordersAndBookings.class));
            }
        });
        return v;
    }
}
