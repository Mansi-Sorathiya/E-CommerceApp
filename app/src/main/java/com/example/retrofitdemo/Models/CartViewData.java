
package com.example.retrofitdemo.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CartViewData {

    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("cartproductdata")
    @Expose
    private List<Cartproductdatum> cartproductdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Cartproductdatum> getCartproductdata() {
        return cartproductdata;
    }

    public void setCartproductdata(List<Cartproductdatum> cartproductdata) {
        this.cartproductdata = cartproductdata;
    }

}
