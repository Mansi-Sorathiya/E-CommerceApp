package com.example.retrofitdemo.Activities;

import static com.example.retrofitdemo.MainActivity.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitdemo.Models.*;

import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView textView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        textView = findViewById(R.id.textview);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Instance_Class.callAPI().loginUser(email.getText().toString(), password.getText().toString()).
                        enqueue(new Callback<LoginData>() {
                    @Override
                    public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                        if (response.body().getConnection() == 1) {
                            if (response.body().getResult() == 1) {
                                Toast.makeText(Login_Activity.this, "Your Successful Login", Toast.LENGTH_SHORT).show();

                                editor.putBoolean("Login",true);
                                editor.putString("id", response.body().getUserdata().getId());
                                editor.putString("name", response.body().getUserdata().getName());
                                editor.putString("email", response.body().getUserdata().getEmail());
                                editor.putString("password", response.body().getUserdata().getPassword());
                                editor.commit();
                                Intent intent = new Intent(Login_Activity.this, com.example.retrofitdemo.Navigation_Activity.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(Login_Activity.this, "Can't Login", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginData> call, Throwable t) {
                        Toast.makeText(Login_Activity.this, "You are not Successfully Login", Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}