package com.example.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.retrofitdemo.Activities.RegisterActivity;
import com.example.retrofitdemo.Models.Productdatalist;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    Boolean Login=false;
    LottieAnimationView lotty ;
    public static List<Productdatalist> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        editor = preferences.edit();
        Login=preferences.getBoolean("Login",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Login){
                    Intent intent=new Intent(MainActivity.this, Navigation_Activity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(MainActivity.this, com.example.retrofitdemo.Activities.Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);

    }
}