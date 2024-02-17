package com.example.retrofitdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Grid_Adapter extends BaseAdapter {

    Cart_Activity cartActivity;
    String name;
    String des;
    String price;
    String img;

    public Grid_Adapter(Cart_Activity cartActivity, String name, String des, String price, String img) {

        this.cartActivity = cartActivity;
        this.name = name;
        this.des = des;
        this.price = price;
        this.img=img;
    }

    public Grid_Adapter(Cart_Activity cartActivity) {
        this.cartActivity=cartActivity;
    }

    @Override
    public int getCount() {

        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view= LayoutInflater.from(cartActivity).inflate(R.layout.cart_item, viewGroup, false);
        ImageView imageView;
        TextView itemname,itemdes,itemprice;
        itemname=view.findViewById(R.id.cname);
        itemdes=view.findViewById(R.id.cdes);
        itemprice=view.findViewById(R.id.cprice);

        itemname.setText(""+name);
        itemdes.setText(""+des);
        itemprice.setText(""+price);

        return view;
    }
}
