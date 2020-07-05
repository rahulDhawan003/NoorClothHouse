package com.rbr.noorclothhouse.api;

import com.rbr.noorclothhouse.helper.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiInterface {

    @FormUrlEncoded
    @POST("stock_add.php")
    Call<ResponseClass> addStockServer(@Field("image") String image, @Field("scode") String scode, @Field("sname") String name,
                                @Field("sprice") String price, @Field("squantity") String quantity);


    @GET("get_stock.php")
    Call<List<Stock>> getStockServer();


    @FormUrlEncoded
    @POST("edit_stock.php")
    Call<ResponseClass> editStockServer( @Field("scode") String scode, @Field("sprice") String price, @Field("squantity") String quantity);

    @FormUrlEncoded
    @POST("delete_stock.php")
    Call<ResponseClass> deleteStockServer( @Field("scode") String scode);


}
