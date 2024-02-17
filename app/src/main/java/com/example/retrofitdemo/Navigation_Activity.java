package com.example.retrofitdemo;

import static com.example.retrofitdemo.MainActivity.editor;
import static com.example.retrofitdemo.MainActivity.preferences;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;


public class Navigation_Activity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ViewGroup container;

    int[] img = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i,
            R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r,
            R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z};

    int[] colorArray = {Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.BLACK, Color.LTGRAY
            , Color.MAGENTA, Color.WHITE, Color.GRAY, Color.DKGRAY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Navigation_Activity.this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //View view = LayoutInflater.from(this).inflate(R.layout.nav_header_navigation, container, false);
        View view=navigationView.getHeaderView(0);
        TextView email = view.findViewById(R.id.usergmail);
        ImageView imageView = view.findViewById(R.id.userimage);
        TextView username=view.findViewById(R.id.username);
        CardView cardView = view.findViewById(R.id.bgcardview);

        //For UserName
        String name = preferences.getString("name", "");
        username.setText(name);

        // For Email
        String useremail = preferences.getString("email", "");
        email.setText(useremail);

        // For UserImage
        char firstchar = useremail.toLowerCase().charAt(0);
        int userimage = get(firstchar, img);
        imageView.setImageResource(userimage);

        //For ImageBgColor
        if (cardView != null) {
            int r = new Random().nextInt(colorArray.length);
            cardView.setCardBackgroundColor(colorArray[r]);
        } else {
            Log.e("CardView", "CardView is null");
        }


        addFragment(new Fragment_View());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_add) {
                    addFragment(new Fragment_Add());
                }
                if (item.getItemId() == R.id.nav_view) {
                    addFragment(new Fragment_View());
                }
                if (item.getItemId() == R.id.nav_show) {
                    addFragment(new Fragment_Show());
                }
                if (item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(Navigation_Activity.this, com.example.retrofitdemo.Activities.Login_Activity.class);
                    editor.putBoolean("Login", false);
                    editor.commit();
                    startActivity(intent);
                    finish();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private static int get(char ch, int[] img) {
        int index = ch - 'a';
            // m(109) -a(97) = 12

        if (index >= 0 && index < img.length) {
            //12 >= 0 && 12 < 25
            return img[index];
        }
        return R.drawable.ic_launcher_background;
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_navigation, fragment);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_, menu);
        return true;
    }
}