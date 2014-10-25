package se.lightside.placeholder.graphics.drawable;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Abstract baseclass for placeholder drawables
 */
public abstract class AbstractPlaceholderDrawable extends Drawable {

    private PlaceHolderState mPlaceHolderState;

    AbstractPlaceholderDrawable() {
        this((PlaceHolderState)null);
    }

    AbstractPlaceholderDrawable(PlaceHolderState orig) {
        mPlaceHolderState = new PlaceHolderState(orig);
    }


    @Override public void setAlpha(int alpha) {
        mPlaceHolderState.mAlpha = alpha;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPlaceHolderState.mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    public Paint getPaint() {
        return mPlaceHolderState.mPaint;
    }

    public void setPaint(Paint p) {
        mPlaceHolderState.mPaint.set(p);
    }

    @Override
    public int getOpacity() { return PixelFormat.UNKNOWN; }

    /**
     * Common placeholder states
     */
    static class PlaceHolderState extends ConstantState {
        int mChangingConfigurations;
        Paint mPaint;
        int mAlpha;

        PlaceHolderState(PlaceHolderState orig) {
            if (orig != null) {
                mPaint = orig.mPaint;
                mAlpha = orig.mAlpha;
            } else {
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            }

        }

        @Override
        public Drawable newDrawable() {
            return null;
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }
    }
}
