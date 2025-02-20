package com.example.tamb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tamb.R;
import com.example.tamb.adapter.PhotoAdapter;
import com.example.tamb.model.SavedPhoto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void downloadActivityClick(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void testOnClicked(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void onClicked(View view) {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        PhotoAdapter adapter = new PhotoAdapter(SavedPhoto.getPhotosByPage(0,20));
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setAdapter(adapter);
    }
}
