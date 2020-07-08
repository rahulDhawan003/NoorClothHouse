package com.rbr.noorclothhouse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rbr.noorclothhouse.R;
import com.rbr.noorclothhouse.helper.PermissionClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();


        ProgressDialog pd;
    }


    public void checkPermission() {

        ArrayList<String> permissionAvailable = new ArrayList<>();
        ArrayList<String> permissionNeeded = new ArrayList<>();

        permissionAvailable.add(Manifest.permission.CAMERA);
        permissionAvailable.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        for (String p : permissionAvailable) {

            if (ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_DENIED)
                permissionNeeded.add(p);

        }
        if(permissionNeeded.size()>0)
            PermissionClass.getPermissions(permissionNeeded, getApplicationContext(), this);
    }

    public void add_stock(View view) {

        startActivity(new Intent(getApplicationContext(), Add_Stock.class));


    }

    public void show_stock(View view) {
        startActivity(new Intent(getApplicationContext(),Show_Stock.class));
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
    }
}
