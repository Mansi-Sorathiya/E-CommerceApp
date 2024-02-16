package com.example.retrofitdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitdemo.Models.ProductDatum;

import java.util.ArrayList;
import java.util.List;

public class Show_Adapter extends RecyclerView.Adapter<Show_Adapter.ViewHolder>{

    Fragment_Show fragmentShow;
    List<ProductDatum> productdata;

    public Show_Adapter(Fragment_Show fragmentShow, List<ProductDatum> productdata) {
        this.fragmentShow = fragmentShow;
        this.productdata = productdata;
    }

    @NonNull
    @Override
    public Show_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragmentShow.getContext()).inflate(R.layout.show_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Show_Adapter.ViewHolder holder, int position) {
        holder.sname.setText(productdata.get(position).getProName());
        holder.sdes.setText(productdata.get(position).getProDes());
        holder.sprice.setText(productdata.get(position).getProPrice());

        // Load product image using Glide (you need to add Glide dependency in your app)
        Glide.with(fragmentShow)
                .load("https://mansisite.000webhostapp.com/mySite/" + productdata.get(position).getProImage())
                .into(holder.simageView);
    }

    @Override
    public int getItemCount() {
        return productdata.size();
    }

    public void filterList(ArrayList<ProductDatum> filteredlist)
    {
        this.productdata=filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sname, sdes, sprice;
        ImageView simageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sname = itemView.findViewById(R.id.sname);
            sdes = itemView.findViewById(R.id.sdes);
            sprice = itemView.findViewById(R.id.sprice);
            simageView = itemView.findViewById(R.id.simageView);
        }
    }
}
