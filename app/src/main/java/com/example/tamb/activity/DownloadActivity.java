package com.example.tamb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tamb.R;
import com.example.tamb.model.Photo;
import com.example.tamb.model.SavedPhoto;
import com.google.android.material.snackbar.Snackbar;

public class DownloadActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView description;
    private TextView name;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download);

        imageView = findViewById(R.id.imageView);
        description = findViewById(R.id.descriptionText);
        name = findViewById(R.id.nameViev);
        imageView.setImageDrawable(null);
        Button selectImageB = findViewById(R.id.selectImageButton);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        loadImage(selectedImageUri);
                    }
                }
        );

        selectImageB.setOnClickListener(view -> OpenGallery());
    }

    private void loadImage(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.error_image)
                .into(imageView);
    }

    public void downloadOnClick(View view) {
        savedPhoto();
        Snackbar snackbar = Snackbar.make(view, "Отправленно", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(0XFF555553);
        snackbar.setActionTextColor(0XFF81C784);
        snackbar.show();
    }

    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void savedPhoto() {
        try {
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);

            Photo photo = new Photo(bitmap, name.getText().toString(), description.getText().toString());
            SavedPhoto.setPhoto(photo);
        } catch (Exception e) {
            Log.e("SavedPhoto", "Ошибка при Image -> bitmap", e);
        }
    }

}
