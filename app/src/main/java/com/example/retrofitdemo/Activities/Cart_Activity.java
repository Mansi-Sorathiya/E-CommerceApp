package com.example.retrofitdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static com.example.retrofitdemo.Activities.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.retrofitdemo.Adapters.Cart_Adapter;
import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.Models.CartViewData;
import com.example.retrofitdemo.Models.Cartproductdatum;
import com.example.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Cart_Adapter cartAdapter;
    List<Cartproductdatum> cartlist = new ArrayList();
    Cartproductdatum cartproductdatum;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView);
        String uid;

            uid = preferences.getString("id", "0");
            Instance_Class.callAPI()
                    .cartviewUser(uid)
                    .enqueue(new Callback<CartViewData>() {
                        @Override
                        public void onResponse(Call<CartViewData> call, Response<CartViewData> response) {

                            Log.d("MMM", "onResponse: Data=" + response.body().getConnection());
                            Log.d("MMM", "onResponse: Result=" + response.body().getResult());
                            if (response.body().getConnection() == 1) {
                                if (response.body().getResult() == 1) {

                                    Toast.makeText(Cart_Activity.this, "CartView Data Available", Toast.LENGTH_LONG).show();

                                    for (int i = 0; i < response.body().getCartproductdata().size(); i++) {
                                        String id = response.body().getCartproductdata().get(i).getId();
                                        String uid = response.body().getCartproductdata().get(i).getUid();
                                        String name = response.body().getCartproductdata().get(i).getProName();
                                        String des = response.body().getCartproductdata().get(i).getProDes();
                                        String price = response.body().getCartproductdata().get(i).getProPrice();
                                        String image = response.body().getCartproductdata().get(i).getProImage();
                                        cartproductdatum = new Cartproductdatum(id, uid, name, des, price, image);
                                        cartlist.add(cartproductdatum);

                                        Log.d("ZZZ", "onResponse: cartlist = " + cartlist.get(position).getProImage());
                                    }

                                    cartAdapter = new Cart_Adapter(Cart_Activity.this, cartlist);
                                    LinearLayoutManager manager = new LinearLayoutManager(Cart_Activity.this);
                                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerView.setLayoutManager(manager);
                                    recyclerView.setAdapter(cartAdapter);

                                }
                            } else {
                                Toast.makeText(Cart_Activity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<CartViewData> call, Throwable t) {
                            Log.e("MMM", "onFailure: Error=" + t.getLocalizedMessage());
                        }
                    });
    }

}