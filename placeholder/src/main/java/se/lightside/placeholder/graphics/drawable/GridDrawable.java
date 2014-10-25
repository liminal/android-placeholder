package se.lightside.placeholder.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * A placeholder that draws a grid on its canvas
 */
public class GridDrawable extends AbstractPlaceholderDrawable {

    float   xspacing;
    float[] xpositions;
    float   yspacing;
    float[] ypositions;

    public GridDrawable(float xd, float yd, Paint paint) {
        super();
        xspacing = xd;
        yspacing = yd;
        setPaint(paint);
    }

    @Override
    public void draw(Canvas canvas) {
        int y = canvas.getHeight();
        int x = canvas.getWidth();

        for (float xline : xpositions) {
            canvas.drawLine(xline, 0, xline, y, getPaint());
        }

        for (float yline : ypositions) {
            canvas.drawLine(0, yline, x, yline, getPaint());
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        xpositions = calculateCache(bounds.width(),  xspacing);
        ypositions = calculateCache(bounds.height(), yspacing);

    }

    private static float[] calculateCache(int total, float spacing) {
        int count = (int) (total / spacing);
        float[] arr = new float[count];
        float d = 0;
        for (int i=0;i<count;i++) {
            d += spacing;
            arr[i] = d;
        }
        return arr;
    }
}
