package se.lightside.placeholder.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import se.lightside.placeholder.text.method.BlockTransformationMethod;

/**
 * A TextView that turns all its letters into blocks. Can be useful if you want to get a
 * rough idea of how text will be laid out without being distracted by Lorem Ipsum
 */
public class BlockedOutTextView extends TextView {


    public BlockedOutTextView(Context context) {
        this(context, null);
    }

    public BlockedOutTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RtlHardcoded")
    public BlockedOutTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        BlockTransformationMethod.asTransformationFor(this);
    }


}
