package com.example.retrofitdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.R;
import com.example.retrofitdemo.Models.RegisterData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText name,email,password;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.registerUser);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Instance_Class.callAPI()
                        .registerUser(name.getText().toString(),email.getText().toString(),password.getText().toString())
                        .enqueue(new Callback<RegisterData>() {
                            @Override
                            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                                Log.d("MMM", "onResponse: Data=" + response.body().getConnection());
                                Log.d("MMM", "onResponse: Result=" + response.body().getResult());
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getResult() == 1) {
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                                        startActivity(intent);
                                    } else if (response.body().getResult() == 2) {
                                        Toast.makeText(RegisterActivity.this, "Already Registered", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registered Not Registered", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                                }

                            }
                            @Override
                            public void onFailure(Call<RegisterData> call, Throwable t) {
                                Log.e("MMM", "onFailure: Error=" + t.getLocalizedMessage());
                            }
                        });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

    }
}