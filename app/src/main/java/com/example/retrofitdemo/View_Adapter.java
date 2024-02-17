package com.example.retrofitdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.retrofitdemo.Models.Productdatalist;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Adapter extends RecyclerView.Adapter<View_Adapter.Holder>  {
    Fragment_View fragmentView;
    List<Productdatalist> list;
    private static final int PICK_IMAGE_CAMERA = 100;
    private static final int PICK_IMAGE_GALLERY = 200;
    private static final int MY_CAMERA_REQUEST_CODE = 150;
    private int requestCode = 150;
    OnItemSelected onItemSelected;
    Cart_Activity cartActivity;

    public View_Adapter(Fragment_View fragmentView, List list, OnItemSelected onItemSelected) {
        this.fragmentView = fragmentView;
        this.list = list;
        this.onItemSelected=onItemSelected;
    }

    public View_Adapter(Fragment_View fragmentView, List<Productdatalist> list) {
        this.fragmentView = fragmentView;
        this.list = list;

    }

    @Override
    public View_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragmentView.getContext()).inflate(R.layout.view_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull View_Adapter.Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.pname.setText("" + list.get(position).getProName());
        holder.pdes.setText("" + list.get(position).getProDes());
        holder.price.setText("" + list.get(position).getProPrice());
        Glide.with(fragmentView.getContext().getApplicationContext())
                .load("https://mansisite.000webhostapp.com/mySite/" + list.get(position).getProImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk cache
                .skipMemoryCache(true) // Skip memory cache
                .placeholder(R.drawable.rotate)
                .into(holder.imageView);

        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(fragmentView.getContext(), holder.optionMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.update) {

                            onItemSelected.getItemPosition(holder.getAdapterPosition());

                        } else if (menuItem.getItemId() == R.id.delete) {

                            Instance_Class.callAPI().deleteUser(list.get(position).getId()).enqueue(new Callback<com.example.retrofitdemo.Models.DeleteData>() {
                                public void onResponse(Call<com.example.retrofitdemo.Models.DeleteData> call, Response<com.example.retrofitdemo.Models.DeleteData> response) {
                                    if (response.isSuccessful()) {
                                        // Delete operation was successful
                                        list.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle unsuccessful response, log error message

                                        Log.d("DELETE_RESPONSE", "onResponse: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<com.example.retrofitdemo.Models.DeleteData> call, Throwable t) {
                                    Log.e("DELETE_FAILURE", "onFailure: " + t.getMessage());
                                }
                            });

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void filterList(ArrayList<Productdatalist> filteredlist) {
        this.list=filteredlist;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView pname, pdes, price;
        ImageView imageView;
        ImageView optionMenu,cart;

        public Holder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.pname);
            pdes = itemView.findViewById(R.id.pdes);
            price = itemView.findViewById(R.id.pprice);
            imageView = itemView.findViewById(R.id.imageView);
            optionMenu = itemView.findViewById(R.id.optionMenu);
            cart=itemView.findViewById(R.id.cart);

        }
    }

}


