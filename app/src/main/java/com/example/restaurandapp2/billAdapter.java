package com.example.restaurandapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class billAdapter extends RecyclerView.Adapter <billAdapter.ViewHolder>{
    ArrayList<String> list;
    private onClickListner listner;
    public interface onClickListner{
        void onItemClicked(int pos);
    }
    public void setLisner(onClickListner mlistner){
        listner=mlistner;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView quantity;
        public ViewHolder(@NonNull View itemView, final onClickListner listner) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.quantity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null){
                        int pos=getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            listner.onItemClicked(pos);
                        }
                    }
                }
            });
        }
    }
    public billAdapter(ArrayList<String>list){
        this.list=list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_individual_item,parent,false);
        ViewHolder holder=new ViewHolder(v,listner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s=list.get(position);
        String[]data=s.split(",");
        holder.name.setText(data[0]);
        holder.quantity.setText(data[1]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
