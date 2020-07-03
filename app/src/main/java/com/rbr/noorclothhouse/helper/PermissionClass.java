package com.rbr.noorclothhouse.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class PermissionClass extends AppCompatActivity {


    public static void getPermissions(ArrayList<String> permission, Context c, Activity a) {

        String[] permissionList = new String[permission.size()];
        permission.toArray(permissionList);

        ActivityCompat.requestPermissions(a, permissionList, 1111);
        return;

    }



}
