package com.emadoki.edslider;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Util
{
    public static float dimenToPx(Context context, float dimen)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen, metrics);
    }
}
