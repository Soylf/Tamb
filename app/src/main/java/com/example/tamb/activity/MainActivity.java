package com.example.tamb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamb.R;
import com.example.tamb.adapter.PhotoAdapter;
import com.example.tamb.model.Photo;
import com.example.tamb.model.SavedPhoto;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

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
        photos = SavedPhoto.getPhotosByPage(0, 20);
        RecyclerView recyclerView = findViewById(R.id.list);
        PhotoAdapter photoAdapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(photoAdapter);
    }
}
