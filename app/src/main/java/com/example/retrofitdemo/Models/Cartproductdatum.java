
package com.example.retrofitdemo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cartproductdatum {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("UID")
    @Expose
    private String uid;
    @SerializedName("PRO_NAME")
    @Expose
    private String proName;
    @SerializedName("PRO_DES")
    @Expose
    private String proDes;
    @SerializedName("PRO_PRICE")
    @Expose
    private String proPrice;
    @SerializedName("PRO_IMAGE")
    @Expose
    private String proImage;

    public Cartproductdatum(String id, String uid, String proName, String proDes, String proPrice, String proImage) {
        this.id = id;
        this.uid = uid;
        this.proName = proName;
        this.proDes = proDes;
        this.proPrice = proPrice;
        this.proImage = proImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

}
