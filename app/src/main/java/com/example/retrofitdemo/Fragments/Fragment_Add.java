package com.example.retrofitdemo.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.retrofitdemo.Instance_Class;
import com.example.retrofitdemo.Models.ProductData;
import com.example.retrofitdemo.R;

import static com.example.retrofitdemo.Activities.MainActivity.preferences;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Add extends Fragment {

    private static final int PICK_IMAGE_CAMERA = 100;
    private static final int PICK_IMAGE_GALLERY = 200;
    private static final int MY_CAMERA_REQUEST_CODE = 150;
    EditText pname, pdescription, pprize;
    ImageView imageView;

    private int requestCode = 150;
    private Bitmap bitmap;
    private String base64Image;
    final CharSequence[] items = {"Camera", "Gallery"};
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add, container, false);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        pname = view.findViewById(R.id.pname);
        pdescription = view.findViewById(R.id.pdes);
        pprize = view.findViewById(R.id.pprize);
        imageView = view.findViewById(R.id.imageView);


        uid = preferences.getString("id", null);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
                selectImgOther();
            }
        });

        Log.d("MMM", "onCreateView: UID=" + uid);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertImage();
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                Instance_Class.callAPI()
                        .productUser(uid, pname.getText().toString(), pdescription.getText().toString(), pprize.getText().toString(),
                                base64Image)
                        .enqueue(new Callback<ProductData>() {
                            @Override
                            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                                Log.d("MMM", "onResponse: Data=" + response.body().getConnection());
                                Log.d("MMM", "onResponse: Productadd=" + response.body().getProductaddd());
                                if (response.body().getConnection() == 1) {
                                    if (response.body().getProductaddd() == 1) {
                                        Toast.makeText(getActivity(), "Product Add Successfully", Toast.LENGTH_LONG).show();

                                        Fragment_View fragmentView = new Fragment_View();
                                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.nav_host_fragment_content_navigation, fragmentView);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

//                                        pname.setText("");
//                                        pdescription.setText("");
//                                        pprize.setText("");
//                                        imageView.setImageResource(R.drawable.ic_launcher_background);

                                    } else if (response.body().getProductaddd() == 2) {
                                        Toast.makeText(getActivity(), "Already Product Add", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "product Not Add", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ProductData> call, Throwable t) {
                                Log.e("MMM", "onFailure: Error=" + t.getLocalizedMessage());
                                Toast.makeText(getActivity(), "Failed to communicate with the server", Toast.LENGTH_LONG).show();

                            }
                        });


            }
        });
        return view;

    }

    private void convertImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Log.d("JJJ", "convertImage: Base64Image=" + bitmap.toString());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("JJJ", "convertImage: Base64Image=" + base64Image);

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
        imageView.setImageBitmap(bitmap);
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