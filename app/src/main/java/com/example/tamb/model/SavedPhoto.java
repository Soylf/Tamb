package com.example.tamb.model;

import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

@Builder
public class SavedPhoto {
    private static final SavedPhoto INSTANCE = SavedPhoto.builder().build();
    private static long id = 0L;

    @Builder.Default
    private ArrayMap<Long, Photo> photosArrayMap = new ArrayMap<>();

    public static void setPhoto(Photo photo) {
        try {
            INSTANCE.photosArrayMap.put(id, photo);
            id++;
        } catch (NullPointerException e) {
            Log.e("SavedPhoto", "Ошибка при доступе к карте фотографий", e);
        }
    }

    public static Photo getPhoto(Long id) {
        Photo photo = null;
        try {
            photo = INSTANCE.photosArrayMap.get(id);
        } catch (NullPointerException e) {
            Log.e("SavedPhoto", "Ошибка при доступе к карте фотографий", e);
        }
        return photo;
    }

    public static List<Photo> getPhotosByPage(int page, int size) {
        List<Photo> photos = new ArrayList<>();
        try {
            int start = page * size;
            int end = Math.min(start + size, INSTANCE.photosArrayMap.size());

            if (start >= end) return photos; //пустая страница

            for (int i = start; i < end; i++) {
                Photo photo = INSTANCE.photosArrayMap.get((long) i);
                if (photo != null) {
                    photos.add(photo);
                }
            }
        } catch (Exception e) {
            Log.e("SavedPhoto", "Ошибка при доступе к карте фотографий", e);
        }
        return photos;
    }
}
