package se.lightside.placeholder.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import se.lightside.placeholder.R;

/**
 * A Ratio Keyline as in http://www.google.com/design/spec/layout/metrics-and-keylines.html
 */
public class RatioLine extends View {

    private final Paint  mBadgePaint        = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint  mBadgeLabelPaint   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path   mBadgeArrowPath    = new Path();
    private final Rect   mBadgeBounds       = new Rect();
    private final String mBadgeLabel;
    private final int    mBadgePadding;
    private final float  mBadgeWidth;
    private final double mRatio;

    public RatioLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        mBadgePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm);
        mBadgePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        mBadgeLabelPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
        mBadgeLabelPaint.setTextAlign(Paint.Align.CENTER);

        String ratio;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RatioLine, 0, 0);
        try {
            ratio  = a.getString(R.styleable.RatioLine_ratio);
            int lineColor = a.getColor(R.styleable.RatioLine_labelColor, Color.parseColor("#3F51B5"));
            mBadgePaint.setColor(lineColor);
            int textColor = a.getColor(R.styleable.RatioLine_textColor, Color.WHITE);
            mBadgeLabelPaint.setColor(textColor);
        } finally {
            a.recycle();
        }

        if (TextUtils.isEmpty(ratio)) { ratio = "1:1"; }

        mBadgeLabel = ratio;

        String[] strs = ratio.split(":");
        int wRatio = Integer.parseInt(strs[0], 10);
        int hRatio = Integer.parseInt(strs[1], 10);

        mRatio = (wRatio*1.0 / hRatio*1.0);

        // Unify badge-length on the biggest reasonable ratio-text
        mBadgeLabelPaint.getTextBounds("xx:xx", 0, 5, mBadgeBounds);
        mBadgeWidth = 1.5f * mBadgeBounds.width();
    }

    /** @return the ratio of this ratio-line as a double */
    public double getRatio() { return mRatio; }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mBadgeBounds.height() + mBadgePadding*2;
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), height);
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int h = bottom - top;
        int w = right - left;
        float half = h / 2f;
        float bStart = w - mBadgeWidth;

        mBadgeArrowPath.reset();
        mBadgeArrowPath.moveTo(bStart - (half*0.8f), half);
        mBadgeArrowPath.lineTo(bStart, 0);
        mBadgeArrowPath.lineTo(bStart, h);
        mBadgeArrowPath.close();
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getHeight();
        int w = getWidth();

        float half = h / 2f;

        float bStart = w - mBadgeWidth;
        canvas.drawLine(0, half, bStart, half, mBadgePaint);
        canvas.drawRect(bStart, 0, w, h, mBadgePaint);
        canvas.drawPath(mBadgeArrowPath, mBadgePaint);

        float yPos = (h / 2) - ((mBadgeLabelPaint.descent() + mBadgeLabelPaint.ascent()) / 2) ;

        canvas.drawText(mBadgeLabel, (w + bStart)/2, yPos, mBadgeLabelPaint);

    }
}
