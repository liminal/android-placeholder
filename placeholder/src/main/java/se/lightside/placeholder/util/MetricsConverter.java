package se.lightside.placeholder.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Converter to turn a pixel value into a typed dimension for display
 */
public class MetricsConverter {

    public static final String UNIT_DP   = "dp";
    public static final String UNIT_PX   = "px";

    private DisplayMetrics mDm;
    private String mConvertUnit;


    public MetricsConverter(@NonNull Context ctx, String convertUnit) {
        mDm = ctx.getResources().getDisplayMetrics();
        mConvertUnit = convertUnit;
    }


    /**
     * Returns a string representation of the provided pixel dimensions converted with this MetricsConverter's
     * convertUnit
     *
     * @param widthPx width in pixels
     * @param heightPx height in pixels
     * @return a String of the format "12dp x 42dp" or whichever unit you're using
     */
    public String asString(int widthPx, int heightPx) {
        return String.format(Locale.US, "%s x %s", asString(widthPx), asString(heightPx));
    }

    public String asString(int pixels) {
        switch (mConvertUnit) {
            case UNIT_DP:
                return String.format(Locale.US, "%ddp", (int) (pixels / mDm.density));
            default:
                return ""+pixels+"px";
        }
    }
}
