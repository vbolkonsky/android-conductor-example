package com.magorasystems.conductor.example.util;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.magorasystems.conductor.example.R;


public class ColorUtil {

    public static int getMaterialColor(Resources resources, int index) {
        TypedArray colors = resources.obtainTypedArray(R.array.mdcolor_300);

        final int returnColor = colors.getColor(index % colors.length(), Color.BLACK);

        colors.recycle();
        return returnColor;
    }

}
