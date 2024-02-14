package com.example.retrofitdemo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.retrofitdemo.Models.Productdatalist;

import java.util.List;

public class Show_Adapter extends RecyclerView.Adapter<Show_Adapter.Holder> {
    Fragment_Show fragmentShow;
    List<Productdatalist> list;

    public Show_Adapter(Fragment_Show fragmentShow, List<Productdatalist> list) {
        this.fragmentShow = fragmentShow;
        this.list = list;
    }

    @NonNull
    @Override

    public Show_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Show_Adapter.Holder holder, int position) {
        holder.pname.setText(list.get(position).getProName());
        holder.pdes.setText(list.get(position).getProDes());
        holder.price.setText(list.get(position).getProPrice());
        Glide.with(fragmentShow.getContext().getApplicationContext())
                .load("https://mansisite.000webhostapp.com/mySite/" + list.get(position).getProImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView pname, pdes, price;
        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.sname);
            pdes = itemView.findViewById(R.id.sdes);
            price = itemView.findViewById(R.id.sprice);
            imageView = itemView.findViewById(R.id.simageView);
        }
    }
}
