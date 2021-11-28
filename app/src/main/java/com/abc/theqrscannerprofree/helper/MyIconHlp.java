package com.abc.theqrscannerprofree.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.constant.TheColorsConst;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

public class MyIconHlp implements InterfaceConstan, TheColorsConst {

    public static Drawable getCodeTypeIcon(Context context, int codeType) {
        Drawable itemIcon = null;
        switch (codeType) {
            case CODE_PREVIEW_TYPE_TEXT:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.ic_baseline_text_fields_24, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_BARCODE:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.code_type_barcode_ic, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_WIFI:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.code_type_wifi_ic, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_GEO:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.ic_baseline_map_24, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_WEB_LINK:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.ic_baseline_link_24, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_CONTACT:
                itemIcon = NamedPaintHlp.recolorIcon(context, R.drawable.code_type_contact_ic, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]);
                break;
        }

        return itemIcon;
    }

}
