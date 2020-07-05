package com.rbr.noorclothhouse.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.rbr.noorclothhouse.R;
import com.rbr.noorclothhouse.api.ApiClient;
import com.rbr.noorclothhouse.api.ResponseClass;
import com.rbr.noorclothhouse.api.apiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStock extends AppCompatActivity {
    Bundle b;
    String name,sno,url,price,qty;
    TextInputEditText et_qty,et_price;
    TextView tv_sno,tv_name;
    ImageView imageView;
    public static com.rbr.noorclothhouse.api.apiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);
        apiInterface = ApiClient.getApiClient().create(com.rbr.noorclothhouse.api.apiInterface.class);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Updating Stock...");

        

        Intent i = getIntent();
         b = i.getExtras();
         findView();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(getApplicationContext()).load(b.get("url")).apply(options).into(imageView);


        et_price.setText(b.get("price").toString());
        et_qty.setText(b.get("quantity").toString());
         tv_sno.setText("Short code: "+b.get("sno").toString());
         tv_name.setText("Name: "+b.get("name").toString());





    }

    private void findView() {
        et_price=findViewById(R.id.detailScreen_pricePerMeter_et);
        et_qty=findViewById(R.id.detailScreen_quantity_et);
        tv_name=findViewById(R.id.detailScreen_name_tv);
        tv_sno=findViewById(R.id.detailScreen_shortno_tv);
        imageView = findViewById(R.id.detailScreen_image_view);


    }

    public void edit_stock(View view) {


       if( dataValidater(et_price.getText().toString().trim(),et_qty.getText().toString().trim())==0){

           Toast.makeText(this, "Please fill the data to be updated", Toast.LENGTH_SHORT).show();
           return;

       }
        pd.show();
        Call<ResponseClass> call = apiInterface.editStockServer(b.get("sno").toString(),
                et_price.getText().toString().trim(),et_qty.getText().toString().trim());

        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {

                if(response.body().getResponse().equals("ok")){
                    pd.dismiss();
                    startActivity(new Intent(getApplicationContext(),Show_Stock.class));
                    finish();
                }else
                {
                    pd.dismiss();
                    Toast.makeText(DetailStock.this, "Error updating stock", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {

                pd.dismiss();
                Toast.makeText(DetailStock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private int dataValidater(String trim, String trim1) {

        if(trim.equals("") || trim1.equals(""))
            return 0;

        return 1;
    }

    public void delete_stock(View view) {


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you really want to delete the stock with scode "+b.get("sno"));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                        delFun();


                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();




    }


    public void delFun(){
        pd.setMessage("Deleting Stock...");
        pd.setCancelable(false);
        pd.show();

        Call<ResponseClass> call = apiInterface.deleteStockServer(b.get("sno").toString());
        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                if(response.body().getResponse().equals("ok")){
                    pd.dismiss();
                    startActivity(new Intent(getApplicationContext(),Show_Stock.class));
                    finish();
                }else
                {
                    pd.dismiss();
                    Toast.makeText(DetailStock.this, "Error deleting stock", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {

                pd.dismiss();
                Toast.makeText(DetailStock.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
