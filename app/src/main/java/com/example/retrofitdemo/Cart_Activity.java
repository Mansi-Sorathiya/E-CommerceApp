package com.example.retrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.example.retrofitdemo.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitdemo.Models.CartItem;
import com.example.retrofitdemo.Models.CartViewData;
import com.example.retrofitdemo.Models.Cartproductdatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_Activity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
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

                                    Log.d("XXX", "onResponse: cartlist = "+cartlist.get(position).getProImage());
                                }
                                cartAdapter = new CartAdapter(Cart_Activity.this, cartlist);
                                LinearLayoutManager manager = new LinearLayoutManager(Cart_Activity.this);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(cartAdapter);

//                                Log.d("PPP", "getItemPosition: Data Name=" + list.get(4).getProName());
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