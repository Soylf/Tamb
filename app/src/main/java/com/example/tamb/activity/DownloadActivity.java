package com.example.tamb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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
        Button selectImageB = findViewById(R.id.selectImageButton);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            imageView.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Toast.makeText(this, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        selectImageB.setOnClickListener(view -> OpenGallery());
    }

    public void downloadOnClick(View view) {
        savedPhoto();
        Snackbar snackbar =  Snackbar.make(view,
                "Отправленно",
                Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(0XFF555553);
        snackbar.setActionTextColor(0XFF81C784);
        snackbar.show();
    }

    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void savedPhoto() {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            Photo photo = new Photo(bitmap, name.getText().toString(), description.getText().toString());
            SavedPhoto.setPhoto(photo);
        }catch (Exception e) {
            Log.e("SavedPhoto", "Ошибка при Image -> bitmap", e);
        }

    }
}