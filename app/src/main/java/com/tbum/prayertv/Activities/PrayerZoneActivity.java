package com.tbum.prayertv.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tbum.prayertv.R;

public class PrayerZoneActivity extends AppCompatActivity {

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_zone);
    }

    public void btnUpdate(View view) {
        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}