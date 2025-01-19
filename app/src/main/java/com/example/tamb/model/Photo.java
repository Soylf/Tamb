package com.example.tamb.model;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    private Bitmap image;
    private String name;
    private String description;
}
