package com.example.restaurandapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterOrders extends RecyclerView.Adapter <adapterOrders.ViewHolder>{
    ArrayList<Order> list;
    Context context;
    private onClickListner listner;
    public interface onClickListner{
        void onItemClicked(int pos);
    }


    public void setListner(onClickListner mlistner){
        listner=mlistner;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView timei;
        ListView itemsi;
        public ViewHolder(@NonNull View itemView, final onClickListner listner) {
            super(itemView);
            timei=itemView.findViewById(R.id.time_order);
            itemsi=itemView.findViewById(R.id.list_order);
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
    public adapterOrders(ArrayList<Order>list, Context context){
        this.list=list; this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_layout_order,parent,false);
        ViewHolder holder=new ViewHolder(v,listner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=list.get(position);
        holder.timei.setText(order.getTime());
        String o=order.getOrder();
        String []orderArray=o.split("_");
        ArrayAdapter <String> a=new ArrayAdapter<>(context,R.layout.list_view,orderArray);
        holder.itemsi.setAdapter(a);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
