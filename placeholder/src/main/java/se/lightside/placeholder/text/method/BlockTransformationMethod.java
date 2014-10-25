package se.lightside.placeholder.text.method;

import android.text.method.ReplacementTransformationMethod;
import android.view.Gravity;
import android.widget.TextView;

/**
*
*/
public class BlockTransformationMethod extends ReplacementTransformationMethod {

    public static void asTransformationFor(TextView tv) {
        tv.setTransformationMethod(new BlockTransformationMethod());
        // For some reason the unicode blocks behave like ltr-text
        // so we explicitly set gravity here to avoid weirdness
        tv.setGravity(Gravity.LEFT);
    }

    private static final char BLOCK_FULL = '\u2588';
    private static final char BLOCK_THREE_QUARTERS = '\u2586';

    private static char[] mOriginal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static char[] mReplacement;
    static {
        int l = mOriginal.length;
        mReplacement = new char[mOriginal.length];
        for (int i=0; i<l;i++) {
            mReplacement[i] = BLOCK_THREE_QUARTERS;
        }
    }

    @Override
    protected char[] getOriginal() {
        return mOriginal;
    }

    @Override
    protected char[] getReplacement() {
        return mReplacement;
    }
}
