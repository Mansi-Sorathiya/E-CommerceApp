package com.example.retrofitdemo;

import com.example.retrofitdemo.Models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Interface
{
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegisterData> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password) ;

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginData> loginUser(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<ProductData> productUser(@Field("userid") String userid,@Field("pname") String pname,@Field("pdes") String description,@Field("pprize") String price,@Field("productimage")String img);

    @FormUrlEncoded
    @POST("viewProduct.php")
    Call<ViewData> viewUser(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("updateproduct.php")
    Call<UpdateData> updateUser(@Field("id") String id,@Field("name") String name,@Field("description") String description,@Field("price") String price,@Field("imagedata") String imagedata,@Field("imagename") String imagename);

    @FormUrlEncoded
    @POST("deleteproduct.php")
    Call<DeleteData> deleteUser(@Field("id") String id);


//    Call<Productdatalist> getUserProducts(@Path("userId") String userId);

    @GET("showallproduct.php")
    Call<ShowallData> getUserProducts();
}
