package com.example.tamb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamb.R;
import com.example.tamb.model.Photo;
import com.example.tamb.model.SavedPhoto;

public class MainActivity extends AppCompatActivity {
    private TextView description;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description = findViewById(R.id.textView);
        photo = findViewById(R.id.imageView2);
    }

    public void downloadActivityClick(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }
    public void getImageClick(View view) {
        Photo photo1 = SavedPhoto.getPhoto(1L);
        description.setText(photo1.getDescription());
        photo.setImageBitmap(photo1.getImage());
    }
}