package se.lightside.placeholder.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import se.lightside.placeholder.util.MetricsConverter;

/**
 * A placeholder that prints the dimensions of the canvas it's drawn on as a
 * centered label on the canvas
 */
public class DimensionsDrawable extends AbstractPlaceholderDrawable {

    private MetricsConverter mConv;

    public DimensionsDrawable(MetricsConverter metricsConverter) {
        super();

        mConv = metricsConverter;

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

        setPaint(p);
    }

    @Override
    public void draw(Canvas canvas) {
        int height = getBounds().height();
        int width = getBounds().width();
        drawLabel(canvas, height, width);
    }

    private void drawLabel(Canvas canvas, int height, int width) {
        Paint p = getPaint();
        String label = mConv.asString(width, height);
        int targetWidth = width / 3;
        float prelimTextWidth = p.measureText(label);
        float ts = p.getTextSize();
        p.setTextSize(ts * (targetWidth / prelimTextWidth));

        Rect bounds = new Rect();
        p.getTextBounds(label, 0, label.length(), bounds);

        canvas.drawText(label, (width - bounds.width())/2, (height - ((p.descent() + p.ascent()) / 2))/ 2, p);

    }

}