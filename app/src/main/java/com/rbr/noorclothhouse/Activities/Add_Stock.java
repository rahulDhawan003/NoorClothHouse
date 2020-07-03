package com.rbr.noorclothhouse.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rbr.noorclothhouse.R;


public class Add_Stock extends AppCompatActivity {


    private  static final int IMAGE_CAPTURE_CODE=1001;
    ImageView imageView;
    public Uri imguri;
    TextInputEditText short_code,name,quantity,price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__stock);
        imageView = findViewById(R.id.addStock_image_view);

        findView();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
            }
        });
    }


    public void upload_stock(View view) {

        String scode = short_code.getText().toString().trim();
        String sname = name.getText().toString().trim();
        String sprice = price.getText().toString().trim();
        String squantity = quantity.getText().toString().trim();

        if(dataValidater(scode,sname,sprice,squantity)==0){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode==RESULT_OK){


            imageView.setImageURI(imguri);
        }
       // Toast.makeText(getApplicationContext(),imguri.toString(),Toast.LENGTH_LONG).show();


    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void checkForPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    111);

        }
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                   ActivityCompat.requestPermissions(this,
                           new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                           111);


        }

        else{
            ContentValues values=new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"new");
            values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
            imguri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);

            startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
        }
    }

    void findView(){

       short_code = findViewById(R.id.addStock_shortNo_Et);
       name = findViewById(R.id.addStock_name_Et);
       price = findViewById(R.id.addStock_pricePerMeter_Et);
       quantity = findViewById(R.id.addStock_quantity_Et);


    }

    int dataValidater(String scode,String sname,String sprice,String quantity){

        if(scode.equals("") || sname.equals("") ||sprice.equals("") ||quantity.equals("")){
            return 0;
        }

        else
            return 1;
    }
}


