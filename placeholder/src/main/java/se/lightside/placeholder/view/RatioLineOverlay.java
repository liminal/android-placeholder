package se.lightside.placeholder.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

/**
 * An overlay of Ratio Keylines as in http://www.google.com/design/spec/layout/metrics-and-keylines.html
 */
@RemoteViews.RemoteView
public class RatioLineOverlay extends ViewGroup {

    /** This is used for computing child view positions */
    private final Rect mTmpChildRect = new Rect();

    public RatioLineOverlay(Context context) { this(context, null); }
    public RatioLineOverlay(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public RatioLineOverlay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


    }

    /** Any layout manager that doesn't scroll will want this. */
    @Override public boolean shouldDelayChildPressedState() { return false; }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int count = getChildCount();

        // Iterate through all children, measuring them
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        setMeasuredDimension(
                getDefaultSize(getSuggestedMinimumWidth(),  widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /** Position all children within this layout. */
    @Override protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        final int count = getChildCount();
        final int parentWidth = right - left;

        for (int i = 0; i < count; i++) {
            final RatioLine line = (RatioLine) getChildAt(i);
            if (line.getVisibility() != GONE ) {

                final int height    = line.getMeasuredHeight();

                mTmpChildRect.top       = top + (int)(parentWidth / line.getRatio()) - (height/2);
                mTmpChildRect.bottom    = mTmpChildRect.top + height;

                // Place the child.
                line.layout(left, mTmpChildRect.top, right, mTmpChildRect.bottom);
            }
        }
    }
}
