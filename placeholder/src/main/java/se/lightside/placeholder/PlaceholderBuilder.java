package se.lightside.placeholder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import se.lightside.placeholder.graphics.drawable.CrossDrawable;
import se.lightside.placeholder.graphics.drawable.DimensionsDrawable;
import se.lightside.placeholder.graphics.drawable.GridDrawable;
import se.lightside.placeholder.util.MetricsConverter;
import se.lightside.placeholder.util.ViewCompat;

/**
 * Builder for Placeholders
 */
public class PlaceholderBuilder {

    private final Context mCtx;
    private final List<Drawable> mStack;

    public PlaceholderBuilder(Context context) {
        mCtx    = context;
        mStack  = new ArrayList<>();
    }

    /**
     * add a background cross to the placeholder
     *
     * @return the builder for chaining
     */
    public PlaceholderBuilder withCross() { return stack(new CrossDrawable()); }

    /**
     * add a background grid to the placeholder
     *
     * @param xyd distance between horizontal/vertical gridlines
     * @param paint paint for the gridlines
     *
     * @return the builder for chaining
     */
    public PlaceholderBuilder withGrid(float xyd,  Paint paint) { return withGrid(xyd, xyd, paint); }

    /**
     * add a background grid to the placeholder
     *
     * @param xd distance between vertical gridlines
     * @param yd distance between horizontal gridlines
     * @param paint paint for the gridlines
     *
     * @return the builder for chaining
     */
    public PlaceholderBuilder withGrid(float xd, float yd, Paint paint) { return stack(new GridDrawable(xd, yd, paint)); }

    /**
     * add a label to the placeholder showing the dimensions of the placeholder in dp
     *
     * @return the builder for chaining
     */
    public PlaceholderBuilder withDimensions() { return withDimensions(MetricsConverter.UNIT_DP); }

    /**
     * add a label to the placeholder showing the dimensions of the placeholder in the units provided
     *
     * @param unitName name of a dimension unit. Supported are "dp" and "px"
     *
     * @return the builder for chaining
     */
    public PlaceholderBuilder withDimensions(String unitName) { return stack(new DimensionsDrawable(new MetricsConverter(mCtx, unitName))); }

    /**
     * Composite the built drawable and set it as background drawable for the given view
     *
     * @param view to set background drawable for
     */
    public void asBackgroundFor(View view) {
        ViewCompat.setBackground(view, composite());
    }

    /**
     * Composite the built drawable and set it as image drawable for the given imageview
     *
     * @param imageView to set imageDrawable for
     */
    public void asImageFor(ImageView imageView) {
        imageView.setImageDrawable(composite());
    }

    /**
     * Just composite and return the current Placeholder
     *
     * @return composite placeholder {@link Drawable}
     */
    public Drawable create() {
        return composite();
    }

    /**
     * True if no placeholders have been selected yet for this builder
     *
     * @return true if placeholder stack is empty
     */
    public boolean isEmpty() {
        return mStack.isEmpty();
    }

    /**
     * helper for simple call chaining
     * @param drawable a drawable to add to the stack
     *
     * @return the builder for chaining
     */
    private PlaceholderBuilder stack(Drawable drawable) {
        mStack.add(drawable);
        return this;
    }

    /**
     * Composite the stack and return as a drawable
     *
     * @return if stack is empty returns null
     *      if the stack only has one element return that element,
     *      otherwise put the whole stack in a {@link android.graphics.drawable.LayerDrawable} and return that
     */
    private Drawable composite() {
        if (isEmpty()) {
            return null;
        } else if (mStack.size() == 1) {
            return mStack.get(0);
        } else {
            return new LayerDrawable(mStack.toArray(new Drawable[mStack.size()]));
        }
    }

}
