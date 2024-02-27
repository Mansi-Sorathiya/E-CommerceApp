package com.example.retrofitdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.retrofitdemo.Models.Productdatalist;
import com.example.retrofitdemo.R;

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