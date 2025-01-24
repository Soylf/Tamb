package com.example.tamb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamb.R;
import com.example.tamb.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final List<Photo> photos;
    private final LayoutInflater inflater;

    public PhotoAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.description.setText(photo.getDescription());
        holder.name.setText(photo.getName());
        holder.imageView.setImageBitmap(photo.getImage());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        TextView description, name;
        ImageView imageView;

        PhotoViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.itemPhotoDescriptionView);
            name = view.findViewById(R.id.itemPhotoNameView);
            imageView = view.findViewById(R.id.itemPhotoImageView);
        }
    }
}