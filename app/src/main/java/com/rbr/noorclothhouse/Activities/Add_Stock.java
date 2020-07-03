package com.rbr.noorclothhouse.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rbr.noorclothhouse.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Stock extends AppCompatActivity {

    static final int REQUEST_PICTURE_CAPTURE = 1;
    private  static final int IMAGE_CAPTURE_CODE=1001;
    private String pictureFilePath;
    public String photoFileName = "photo.jpg";
    File photoFile;

    ImageView img;
    Uri imguri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__stock);
        img = findViewById(R.id.addStock_image_view);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,"new");
                values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
                imguri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                //camera intent
                Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
                startActivityForResult(cameraintent,IMAGE_CAPTURE_CODE);
            }
        });
    }


    public void upload_stock(View view) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        img.setImageURI(imguri);
        Toast.makeText(getApplicationContext(),getRealPathFromURI(imguri),Toast.LENGTH_LONG).show();
        //called when image captured
        if(requestCode==RESULT_OK){
            img.setImageURI(imguri);
        }
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
}
