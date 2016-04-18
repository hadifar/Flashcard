package net.hadifar.dope.ui.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import net.hadifar.dope.R;
import net.hadifar.dope.utils.FontHelper;

abstract class BaseProgressView extends View {
    protected int progress;
    protected int maximum_progress = 100;

    protected Paint foregroundPaint, backgroundPaint, textPaint;
    protected float backgroundStrokeWidth = 3f, strokeWidth = 5f;
    protected int PADDING = 20;
    protected int color = getResources().getColor(R.color.color_accent), backgroundColor = Color.WHITE;
    protected int textColor = getResources().getColor(R.color.color_primary_dark);
    protected int shadowColor = getResources().getColor(R.color.default_shader);
    protected int textSize = 36;
    protected int height, width;

    protected Context context;
    protected boolean isRoundEdge;
    protected boolean isShadowed;
    protected OnProgressTrackListener listener;

    public void setOnProgressTrackListener(OnProgressTrackListener listener) {
        this.listener = listener;
    }

    protected abstract void init(Context context);

    public BaseProgressView(Context context) {
        super(context);
        init(context);

    }

    public BaseProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        init(context);

    }


    public BaseProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.Progress, 0, 0);
        try {
            progress = (int) typedArray.getFloat(
                    R.styleable.Progress_progress, progress);
            strokeWidth = typedArray.getDimension(
                    R.styleable.Progress_stroke_width, strokeWidth);
            backgroundStrokeWidth = typedArray.getDimension(
                    R.styleable.Progress_background_stroke_width,
                    backgroundStrokeWidth);
            color = typedArray.getInt(
                    R.styleable.Progress_progress_color, color);
            backgroundColor = typedArray.getInt(
                    R.styleable.Progress_background_color, backgroundColor);
            textColor = typedArray.getInt(
                    R.styleable.Progress_text_color, textColor);
            textSize = typedArray.getInt(
                    R.styleable.Progress_text_size, textSize);

        } finally {
            typedArray.recycle();
        }
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);


    }

    protected void initBackgroundColor() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);
        if (isRoundEdge) {
            backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        }
        if (isShadowed) {

            backgroundPaint.setShadowLayer(3.0f, 0.0f, 2.0f, shadowColor);
        }
    }

    protected void initForegroundColor() {
        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        if (isRoundEdge) {
            foregroundPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        if (isShadowed) {

            foregroundPaint.setShadowLayer(3.0f, 0.0f, 2.0f, shadowColor);
        }
    }

    protected void initTextColor() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(1f);
        textPaint.setTextSize(textSize);
        Typeface typeface = FontHelper.getInstance(context).getRobotoTypeface();
        textPaint.setTypeface(typeface);


    }

    public float getProgress() {
        return progress;
    }


    public void setProgress(int progress) {
        setProgressInView(progress);

    }

    private synchronized void setProgressInView(int progress) {
        this.progress = (progress <= maximum_progress) ? progress : maximum_progress;
        invalidate();
        trackProgressInView(progress);
    }

    private void trackProgressInView(int progress) {
        if (listener != null) {
            listener.onProgressUpdate(progress);
            if (progress >= maximum_progress) {
                listener.onProgressFinish();
            }
        }
    }

    public void resetProgress() {
        setProgress(0);

    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        init(context);
    }

    public int getProgressColor() {
        return color;
    }

    public void setProgressColor(int color) {
        this.color = color;
        init(context);

    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        init(context);

    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        init(context);

    }

    public void setProgressStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        init(context);
    }

    public void setBackgroundStrokeWidth(int strokeWidth) {
        this.backgroundStrokeWidth = strokeWidth;
        init(context);
    }

    public void setRoundEdge(boolean isRoundEdge) {
        this.isRoundEdge = isRoundEdge;
        init(context);
    }

    public void setShadow(boolean isShadowed) {
        this.isShadowed = isShadowed;
        init(context);

    }
}
