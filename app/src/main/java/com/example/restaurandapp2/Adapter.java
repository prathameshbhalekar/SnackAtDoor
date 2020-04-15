package com.example.restaurandapp2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ImageViewHolder> {
    private Context mContext;
    private List<Uploder> mUploads;
    private onClickListner listner;
    public interface onClickListner{
        void onItemClicked(int pos);
    }
    public void setLisner(onClickListner mlistner){
        listner=mlistner;
    }

    public Adapter(Context context, List<Uploder> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        ImageViewHolder holder=new ImageViewHolder(v,listner);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Uploder uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.des.setText(uploadCurrent.getDescription());
//        System.out.println(holder)
        Picasso.with(mContext)
                .load(uploadCurrent.getUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView des;
        public ImageView imageView;

        public ImageViewHolder(View itemView, final onClickListner listner) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.title);
            des=itemView.findViewById(R.id.body);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listner.onItemClicked(pos);
                        }
                    }
                }
            });
        }
    }
}
