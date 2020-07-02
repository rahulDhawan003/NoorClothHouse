package com.rbr.noorclothhouse.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rbr.noorclothhouse.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressDialog pd;
    }

    public void add_stock(View view) {

        startActivity(new Intent(getApplicationContext(),Add_Stock.class));
    }

    public void show_stock(View view) {
    }
}
