package com.example.tamb.model;

import android.util.ArrayMap;
import android.util.Log;

import lombok.Builder;

@Builder
public class SavedPhoto {
    private static final SavedPhoto INSTANCE = SavedPhoto.builder().build();
    private static long id = 1L;

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
}
