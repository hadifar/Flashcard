package com.march1905.dope.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.march1905.dope.R;

/**
 * Amir Hadifar on 02/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class AutoScaleTextView extends TextView {
    private Paint textPaint;

    private float _preferredTextSize;
    private float _minTextSize;

    public AutoScaleTextView(Context context) {
        this(context, null);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoScaleTextViewStyle);

        // Use this constructor, if you do not want use the default style
        // super(context, attrs);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.textPaint = new Paint();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScaleTextView, defStyle, 0);
        this._minTextSize = a.getDimension(R.styleable.AutoScaleTextView_minTextSize, 10f);
        a.recycle();

        this._preferredTextSize = this.getTextSize();
    }

    /**
     * Set the minimum text size for this view
     *
     * @param minTextSize The minimum text size
     */
    public void setMinTextSize(float minTextSize) {
        this._minTextSize = minTextSize;
    }

    /**
     * Resize the text so that it fits
     *
     * @param text      The text. Neither <code>null</code> nor empty.
     * @param textWidth The width of the TextView. > 0
     */
    private void refitText(String text, int textWidth) {
        if (textWidth <= 0 || text == null || text.length() == 0)
            return;

        // the width
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

        final float threshold = 0.5f; // How close we have to be

        this.textPaint.set(this.getPaint());

        float minTextSize = _minTextSize;
        float preferredTextSize = _preferredTextSize;
        while ((preferredTextSize - minTextSize) > threshold) {
            float size = (preferredTextSize + minTextSize) / 2;
            this.textPaint.setTextSize(size);
            if (this.textPaint.measureText(text) >= targetWidth)
                preferredTextSize = size; // too big
            else
                minTextSize = size; // too small
        }

        // Use min size so that we undershoot rather than overshoot
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        this.refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        if (width != oldwidth)
            this.refitText(this.getText().toString(), width);
    }
}
