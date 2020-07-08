package com.rbr.noorclothhouse.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rbr.noorclothhouse.R;
import com.rbr.noorclothhouse.api.ApiClient;
import com.rbr.noorclothhouse.api.ResponseClass;
import com.rbr.noorclothhouse.api.apiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_Stock extends AppCompatActivity {


    private static final int IMAGE_CAPTURE_CODE = 1001;
    public static com.rbr.noorclothhouse.api.apiInterface apiInterface;
    ImageView imageView;
    public Uri imguri;
    TextInputEditText short_code, name, quantity, price;
    Bitmap b1, newb,finalup;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__stock);
        imageView = findViewById(R.id.addStock_image_view);

        apiInterface = ApiClient.getApiClient().create(apiInterface.class);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Adding Stock...");

        findView();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
            }
        });
    }


    public void upload_stock(View view) {
        pd.show();

        String scode = short_code.getText().toString().trim();
        String sname = name.getText().toString().trim();
        String sprice = price.getText().toString().trim();
        String squantity = quantity.getText().toString().trim();
        String image = imgtoString();

        if (dataValidater(scode, sname, sprice, squantity, image) == 0) {
            pd.dismiss();
            Toast.makeText(this, "Please fill all fields and attach an image", Toast.LENGTH_SHORT).show();
            return;
        }


        Call<ResponseClass> call= apiInterface.addStockServer(image,scode,sname,sprice,squantity);

        call.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {

                if(response.body().getResponse().equals("ok"))
                {
                    pd.dismiss();
                    Toast.makeText(Add_Stock.this, "Stock successfully added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finishAffinity();
                }else if(response.body().getResponse().equals("error")){
                    pd.dismiss();
                    Toast.makeText(Add_Stock.this, "Error Adding stock", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {

                pd.dismiss();
                Toast.makeText(Add_Stock.this, "Error Adding stock", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1001) {

            try {
                b1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                newb = scaleDown(b1, 2000, true);
                finalup =modifyOrientation(newb,getRealPathFromURI(imguri));
            } catch (IOException e) {
                e.printStackTrace();
            }



            imageView.setImageURI(imguri);
        }


    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void checkForPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    111);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    111);


        } else {
            ContentValues values = new ContentValues();
            // values.put(MediaStore.Images.Media.TITLE,"new");
            // values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
            imguri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imguri);

            startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
        }
    }

    void findView() {

        short_code = findViewById(R.id.addStock_shortNo_Et);
        name = findViewById(R.id.addStock_name_Et);
        price = findViewById(R.id.addStock_pricePerMeter_Et);
        quantity = findViewById(R.id.addStock_quantity_Et);


    }

    int dataValidater(String scode, String sname, String sprice, String quantity, String image) {

        if (scode.equals("") || sname.equals("") || sprice.equals("") || quantity.equals("") || image.equals("")) {
            return 0;
        } else
            return 1;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public String imgtoString() {

        if (newb != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            finalup.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
            byte[] bytearray = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(bytearray, Base64.DEFAULT);
        }
        return "";
    }


    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}


