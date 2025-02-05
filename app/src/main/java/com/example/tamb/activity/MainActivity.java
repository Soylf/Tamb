package com.example.tamb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tamb.R;
import com.example.tamb.adapter.PhotoAdapter;
import com.example.tamb.model.Photo;
import com.example.tamb.model.SavedPhoto;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void downloadActivityClick(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void onCLicked(View view) {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        PhotoAdapter adapter = new PhotoAdapter(SavedPhoto.getPhotosByPage(0,20));
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-position * page.getWidth());
                page.setAlpha(1 - Math.abs(position));
            }
        });
    }
}
