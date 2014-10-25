package se.lightside.placeholder.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import se.lightside.placeholder.util.ViewCompat;

/**
 * A simple placeholder that draws a cross on between its corners
 */
public class CrossDrawable extends AbstractPlaceholderDrawable {

    public CrossDrawable() {
        super();

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.DKGRAY);
        p.setStrokeWidth(1);

        setPaint(p);
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect  = new RectF(getBounds());
        canvas.drawRect(rect, getPaint());
        canvas.drawLine(rect.left, rect.top, rect.right, rect.bottom, getPaint());
        canvas.drawLine(rect.left, rect.bottom, rect.right, rect.top, getPaint());
    }

}