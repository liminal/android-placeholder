package se.lightside.placeholder.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Helper class for view compatibility across API-levels
 */
public class ViewCompat {

    /** Do not instantiate */
    private ViewCompat() {}

    /**
     * Set background of a view appropriately for API level without annoying deprecation warnings
     *
     * @param view the view to set the background for
     * @param drawable the drawable to set as background
     */
    public static void setBackground(final View view, final Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            setBackgroundPreJellyBean(view, drawable);
        }
    }

    @SuppressWarnings("deprecation")
    private static void setBackgroundPreJellyBean(final View view, final Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

}
