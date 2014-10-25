package se.lightside.placeholder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.lightside.placeholder.PlaceholderBuilder;
import se.lightside.placeholder.R;
import se.lightside.placeholder.graphics.drawable.CrossDrawable;
import se.lightside.placeholder.graphics.drawable.DimensionsDrawable;
import se.lightside.placeholder.graphics.drawable.GridDrawable;
import se.lightside.placeholder.util.MetricsConverter;
import se.lightside.placeholder.util.ViewCompat;

/**
 * General PlaceholderView useful when developing to get an aproximate idea about
 * how things will look.
 *
 */
public class PlaceholderView extends View {

    private static final Paint RED    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint GREEN  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint BLUE   = new Paint(Paint.ANTI_ALIAS_FLAG);

    static {
        RED.setColor(Color.parseColor("#ff0000"));
        GREEN.setColor(Color.parseColor("#00ff00"));
        BLUE.setColor(Color.parseColor("#0000ff"));
    }

    public PlaceholderView(Context context) {
        this(context, null);
    }

    public PlaceholderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceholderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        PlaceholderBuilder builder = new PlaceholderBuilder(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.PlaceholderView, 0, 0);
        float rspace, gspace, bspace;
        String showDimens;
        boolean crossedBg;
        try {
            crossedBg   = a.getBoolean(R.styleable.PlaceholderView_ph_bgCross, false);
            rspace      = a.getDimension(R.styleable.PlaceholderView_ph_gridSpacingRed, 0);
            gspace      = a.getDimension(R.styleable.PlaceholderView_ph_gridSpacingGreen, 0);
            bspace      = a.getDimension(R.styleable.PlaceholderView_ph_gridSpacingBlue, 0);
            showDimens  = a.getString(R.styleable.PlaceholderView_ph_showDimensions);

        } finally {
            a.recycle();
        }

        if (crossedBg)  { builder.withCross(); }
        if (rspace > 0) { builder.withGrid(rspace, RED); }
        if (gspace > 0) { builder.withGrid(gspace, GREEN); }
        if (bspace > 0) { builder.withGrid(bspace, BLUE); }
        if (!TextUtils.isEmpty(showDimens)) { builder.withDimensions(showDimens); }

        if (!builder.isEmpty()) {
            builder.asBackgroundFor(this);
        }
    }


}
