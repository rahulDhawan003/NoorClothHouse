package com.rbr.noorclothhouse.helper;

public class Stock {
    private String short_code;
    private String stock_name;
    private String stock_img_url_;
    private String stock_price;

    public Stock(String short_code, String stock_name, String stock_img_url_, String stock_price, String stock_quantity) {
        this.short_code = short_code;
        this.stock_name = stock_name;
        this.stock_img_url_ = stock_img_url_;
        this.stock_price = stock_price;
        this.stock_quantity = stock_quantity;
    }

    private String stock_quantity;

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStock_img_url_() {
        return stock_img_url_;
    }

    public void setStock_img_url_(String stock_img_url_) {
        this.stock_img_url_ = stock_img_url_;
    }

    public String getStock_price() {
        return stock_price;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
}
