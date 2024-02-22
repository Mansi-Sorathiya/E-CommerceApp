package com.example.retrofitdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.retrofitdemo.Models.CartItem;
import com.example.retrofitdemo.Models.Cartproductdatum;
import com.example.retrofitdemo.Models.DeleteData;
import com.example.retrofitdemo.Models.Productdatalist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {

    Cart_Activity cartActivity;
    List<Cartproductdatum> cartlist;


    public CartAdapter(Cart_Activity cartActivity, List<Cartproductdatum> cartlist) {
        this.cartActivity = cartActivity;
        this.cartlist = cartlist;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cartActivity).inflate(R.layout.cart_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.name.setText(cartlist.get(position).getProName());
        holder.des.setText(cartlist.get(position).getProDes());
        holder.price.setText(cartlist.get(position).getProPrice());

        Log.d("XXX", "onBindViewHolder: " + holder.name.getText());
        Log.d("XXX", "onBindViewHolder: " + holder.des.getText());
        Log.d("XXX", "onBindViewHolder: " + holder.price.getText());

        Glide.with(cartActivity)
                .load("https://mansisite.000webhostapp.com/mySite/" + cartlist.get(position).getProImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView name, price, des;
        ImageView imageView,delete;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cname);
            des = itemView.findViewById(R.id.cdes);
            price = itemView.findViewById(R.id.cprice);
            imageView = itemView.findViewById(R.id.cartimage);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
