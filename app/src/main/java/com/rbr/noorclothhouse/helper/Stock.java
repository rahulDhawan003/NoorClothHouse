package com.rbr.noorclothhouse.helper;

import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("short_code")
    private String short_code;
    @SerializedName("stock_name")
    private String stock_name;
    @SerializedName("stock_img_url")
    private String stock_img_url_;
    @SerializedName("stock_price")
    private String stock_price;
    @SerializedName("stock_quantity")
    private String stock_quantity;

    public Stock(String short_code, String stock_name, String stock_img_url_, String stock_price, String stock_quantity) {
        this.short_code = short_code;
        this.stock_name = stock_name;
        this.stock_img_url_ = stock_img_url_;
        this.stock_price = stock_price;
        this.stock_quantity = stock_quantity;
    }



    public String getShort_code() {
        return short_code;
    }



    public String getStock_name() {
        return stock_name;
    }



    public String getStock_img_url_() {
        return stock_img_url_;
    }


    public String getStock_price() {
        return stock_price;
    }



    public String getStock_quantity() {
        return stock_quantity;
    }


}
