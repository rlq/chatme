package com.he.util;

import android.content.Context;

public class ResUtil {

    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout", paramContext.getPackageName());
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string", paramContext.getPackageName());
    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
    }

    public static int getStyleId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "style", paramContext.getPackageName());
    }

    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
    }

    public static int getColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "color", paramContext.getPackageName());
    }

    public static int getStyleable(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "styleable", paramContext.getPackageName());
    }


    public static int getDimen(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "dimen", paramContext.getPackageName());
    }

    public static int[] getStyleableId(Context paramContext, String paramString3) {
        try {
            return (int[]) Class.forName(paramContext.getPackageName() + ".R$" + "styleable").getField(paramString3).get(paramString3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
