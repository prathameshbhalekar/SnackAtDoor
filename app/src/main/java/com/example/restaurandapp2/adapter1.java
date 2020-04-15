package com.example.restaurandapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter1 extends RecyclerView.Adapter <adapter1.ViewHolder>{
    ArrayList<bookingUpload> list;
    private onClickListner listner;
    public interface onClickListner{
        void onItemClicked(int pos);
    }
    public void setLister(onClickListner mlistner){
        listner=mlistner;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView tableNo;
        public TextView time;
        public TextView date;
        public TextView fortime;
        public ViewHolder(@NonNull View itemView, final onClickListner listner) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            tableNo=itemView.findViewById(R.id.tableno);
            time=itemView.findViewById(R.id.time);
            date=itemView.findViewById(R.id.date);
            fortime=itemView.findViewById(R.id.fortime);
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
    public adapter1(ArrayList<bookingUpload>list){
        this.list=list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_item,parent,false);
        ViewHolder holder=new ViewHolder(v,listner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bookingUpload item=list.get(position);
        holder.name.setText(item.getName());
        holder.tableNo.setText(item.getTableNo());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        String s="For Time: "+item.getForTime();
        holder.fortime.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
