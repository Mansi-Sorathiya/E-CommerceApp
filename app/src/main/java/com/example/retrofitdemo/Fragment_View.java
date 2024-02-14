package com.example.retrofitdemo;

import static com.example.retrofitdemo.MainActivity.preferences;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.retrofitdemo.Models.Productdatalist;
import com.example.retrofitdemo.Models.UpdateData;
import com.example.retrofitdemo.Models.ViewData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_View extends Fragment {

    private Bitmap bitmap;
    private String base64Image;
    private int requestCode = 150;
    ImageView udimage;
    View_Adapter adapter;
    private static final int PICK_IMAGE_CAMERA = 100;
    private static final int PICK_IMAGE_GALLERY = 200;
    private static final int MY_CAMERA_REQUEST_CODE = 150;
    RecyclerView recyclerView;
    List<Productdatalist> list = new ArrayList();
    String uid;
    LottieAnimationView l1 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        uid = preferences.getString("id", "0");
        getData();
        return view;
    }

    private void getData() {
        Instance_Class.callAPI()
                .viewUser(uid)
                .enqueue(new Callback<ViewData>() {
                    @Override
                    public void onResponse(Call<ViewData> call, Response<ViewData> response) {
                        Log.d("MMM", "onResponse: Data=" + response.body().getConnection());
                        Log.d("MMM", "onResponse: Result=" + response.body().getResult());
                        if (response.body().getConnection() == 1) {
                            if (response.body().getResult() == 1) {
                                Toast.makeText(getActivity(), "ProductData Available", Toast.LENGTH_LONG).show();
                                for (int i = 0; i < response.body().getProductdata().size(); i++) {
                                    String id = response.body().getProductdata().get(i).getId();
                                    String uid = response.body().getProductdata().get(i).getUid();
                                    String name = response.body().getProductdata().get(i).getProName();
                                    String des = response.body().getProductdata().get(i).getProDes();
                                    String price = response.body().getProductdata().get(i).getProPrice();
                                    String image = response.body().getProductdata().get(i).getProImage();
                                    Productdatalist productdatalist = new Productdatalist(id, uid, name, des, price, image);
                                    list.add(productdatalist);

                                }
                                adapter = new View_Adapter(Fragment_View.this, list, new OnItemSelected() {
                                    @Override
                                    public void getItemPosition(int position) {
                                        Log.d("PPP", "getItemPosition: position=" + position);
                                        Log.d("PPP", "getItemPosition: Data Id=" + list.get(position).getId());
                                        Log.d("PPP", "getItemPosition: Data Name=" + list.get(position).getProName());
                                        CreateUpdate(position);

                                    }
                                });
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);

                                Log.d("PPP", "getItemPosition: Data Name=" + list.get(4).getProName());
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ViewData> call, Throwable t) {
                        Log.e("MMM", "onFailure: Error=" + t.getLocalizedMessage());
                    }
                });


    }

    private void CreateUpdate(int position) {

        Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.dialog);
        EditText name = dialog.findViewById(R.id.uname);
        EditText udes = dialog.findViewById(R.id.udes);
        EditText uprize = dialog.findViewById(R.id.uprize);
        udimage = dialog.findViewById(R.id.uimageView);
        ImageView rotate=dialog.findViewById(R.id.rotate);
        Button Addd = dialog.findViewById(R.id.addd);

        name.setText(list.get(position).getProName());
        udes.setText(list.get(position).getProDes());
        uprize.setText(list.get(position).getProPrice());
        Glide.with(getContext().getApplicationContext())
                .load("https://mansisite.000webhostapp.com/mySite/" + list.get(position).getProImage()).
                into(udimage);
        Log.d("KKK", "onBindViewHolder: Image=" + list.get(position).getProImage());

        udimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
                selectImgOther();
            }
        });
        Addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //  Log.d("JJJ", "convertImage: Base64Image=" + bitmap);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
                // Log.d("JJJ", "convertImage: Base64Image=" + base64Image);

                Instance_Class.callAPI()
                        .updateUser(list.get(position).getId(), name.getText().toString(), udes.getText().toString(),
                                uprize.getText().toString(), base64Image, list.get(position).getProImage())
                        .enqueue(new Callback<UpdateData>() {
                            @Override
                            public void onResponse(Call<UpdateData> call, Response<UpdateData> response) {

                            }

                            @Override
                            public void onFailure(Call<UpdateData> call, Throwable t) {
                                // Handle the failure
                            }
                        });


                final Animation myRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

                rotate.startAnimation(myRotation);
                Handler handler=new Handler();
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        addFragment(new Fragment_View());

                        dialog.dismiss();
                    }
                };
                handler.postDelayed(runnable,5000);
//        new Handler().postDelayed(new Runnable() {

            }

        });

        dialog.show();
//            @Override
//            public void run() {
//
//            }
//        },10000);
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_navigation, fragment);
        transaction.commit();
    }

    private void checkCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, requestCode);
    }

    private void selectImgOther() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(android.Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        udimage.setImageBitmap(bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}