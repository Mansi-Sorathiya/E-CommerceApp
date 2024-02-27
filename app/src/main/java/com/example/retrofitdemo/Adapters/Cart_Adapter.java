package com.example.retrofitdemo.Adapters;

import static com.example.retrofitdemo.Activities.MainActivity.preferences;
import static com.example.retrofitdemo.Activities.MainActivity.editor;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitdemo.Activities.Cart_Activity;
import com.example.retrofitdemo.Activities.PaymentActivity;
import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.Models.CartDeleteData;
import com.example.retrofitdemo.Models.Cartproductdatum;
import com.example.retrofitdemo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.Holder> {

    Cart_Activity cartActivity;
    List<Cartproductdatum> cartlist;

    public Cart_Adapter(Cart_Activity cartActivity, List<Cartproductdatum> cartlist) {
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

//        Log.d("XXX", "onBindViewHolder: " + holder.name.getText());
//        Log.d("XXX", "onBindViewHolder: " + holder.des.getText());
//        Log.d("XXX", "onBindViewHolder: " + holder.price.getText());
        Log.d("III", "onBindViewHolder: img="+"https://mansisite.000webhostapp.com/mySite/" + cartlist.get(position).getProImage());
        Glide.with(cartActivity)
                .load("https://mansisite.000webhostapp.com/mySite/" + cartlist.get(position).getProImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Instance_Class.callAPI().cartdeleteUser(cartlist.get(position).getId()).enqueue(new Callback<CartDeleteData>() {
                    public void onResponse(Call<CartDeleteData> call, Response<CartDeleteData> response) {
                        if (response.isSuccessful()) {

                            cartlist.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                        } else {

                            Log.d("DELETE_RESPONSE", "onResponse: " + response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<CartDeleteData> call, Throwable t) {
                        Log.e("DELETE_FAILURE", "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(cartActivity);
                dialog.setContentView(R.layout.dialog_layout);
                TextView msg = dialog.findViewById(R.id.textview);
                Button cancel = dialog.findViewById(R.id.cancelbtn);
                Button okBtn = dialog.findViewById(R.id.okbtn);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(cartActivity, PaymentActivity.class);
                        cartActivity.startActivity(intent);
                        cartActivity.finish();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView name, price, des;
        ImageView imageView,delete,payment;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cname);
            des = itemView.findViewById(R.id.cdes);
            price = itemView.findViewById(R.id.cprice);
            imageView = itemView.findViewById(R.id.cart_item_imageView);
            delete=itemView.findViewById(R.id.delete);
            payment=itemView.findViewById(R.id.payment);
        }
    }
}
