package com.example.retrofitdemo.Fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.Models.ShowallData;
import com.example.retrofitdemo.Models.ProductDatum;
import com.example.retrofitdemo.R;
import com.example.retrofitdemo.Adapters.Show_Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Show extends Fragment {

    List<ProductDatum> productdata;
    Show_Adapter adapter;
    int cnt=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__show, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.srecyclerView);
        SearchView searchView=view.findViewById(R.id.searchView);
        ImageButton sort=view.findViewById(R.id.ssort);


        recyclerView.setLayoutManager(new LinearLayoutManager(Fragment_Show.this.getContext()));
        Instance_Class.callAPI().getUserProducts().enqueue(new Callback<ShowallData>() {
            @Override
            public void onResponse(Call<ShowallData> call, Response<ShowallData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getConnection() == 1 && response.body().getResult() == 1)
                    {
                        productdata = response.body().getProductData();
                        Log.d("TTT", "onResponse: " + productdata);
                        adapter = new Show_Adapter(Fragment_Show.this, productdata);
                        recyclerView.setAdapter(adapter);
                    } else if (response.body().getResult() == 0) {
                        Toast.makeText(getContext(), "No more items available", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowallData> call, Throwable t) {
                Log.e("TTT", "onFailure: " + t.getLocalizedMessage());
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    Collections.sort(productdata, (lhs, rhs) -> lhs.getProName().compareTo(rhs.getProName()));

                if (cnt % 2 == 0) {
                    Collections.sort(productdata, Comparator.comparing(ProductDatum::getProName));
                } else {
                    Collections.sort(productdata, Comparator.comparing(ProductDatum::getProName).reversed());
                }
                cnt++;
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ProductDatum> filteredlist=new ArrayList<>();
                for (ProductDatum item : productdata) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getProName().toLowerCase().contains(newText.toLowerCase())) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    // if no item is added in filtered list we are
                    // displaying a toast message as no data found.
                    Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
                } else {
                    // at last we are passing that filtered
                    // list to our adapter class.
                    adapter.filterList(filteredlist);
                }
                return true;
            }
        });
        return view;
    }
}