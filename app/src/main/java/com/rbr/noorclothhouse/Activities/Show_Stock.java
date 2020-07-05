package com.rbr.noorclothhouse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rbr.noorclothhouse.R;
import com.rbr.noorclothhouse.api.ApiClient;
import com.rbr.noorclothhouse.api.apiInterface;
import com.rbr.noorclothhouse.helper.Recycler_Adapter;
import com.rbr.noorclothhouse.helper.Stock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Show_Stock extends AppCompatActivity {

    RecyclerView rv;
    List<Stock> stocklist;
    List<Stock> stk;


    public static com.rbr.noorclothhouse.api.apiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__stock);

        //FindviewbyId
        findview();


        stocklist = new ArrayList<>();
        stk = new ArrayList<>();


        Call<List<Stock>> call = apiInterface.getStockServer();


        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {

                if(response.body()!=null){
                stocklist = response.body();



                for(int i =0 ; i<stocklist.size();i++){
                    stk.add(stocklist.get(i));
                }

                rv.setHasFixedSize(true);
                LinearLayoutManager layout=new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                Recycler_Adapter adapter = new Recycler_Adapter(getApplicationContext(), stk);
                rv.setAdapter(adapter);

            }}

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Toast.makeText(Show_Stock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void findview() {
        apiInterface = ApiClient.getApiClient().create(com.rbr.noorclothhouse.api.apiInterface.class);
        rv=findViewById(R.id.show_stock_recycler);
    }
}
