package com.rbr.noorclothhouse.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface apiInterface {

    @FormUrlEncoded
    @POST("stock_add.php")
    Call<ResponseClass> addStockServer(@Field("image") String image, @Field("scode") String scode, @Field("sname") String name,
                                @Field("sprice") String price, @Field("squantity") String quantity);


}
