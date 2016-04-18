package net.hadifar.dope.ui.widget.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;



public class LinearProgress extends BaseProgressView {


    private RectF rectP;


    public LinearProgress(Context context) {
        super(context);
    }

    public LinearProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init(Context context) {
        this.context = context;
        progress = 0;

        initBackgroundColor();
        initForegroundColor();
        initTextColor();
        rectP = new RectF();

    }

    @Override
    protected void initForegroundColor() {
        super.initForegroundColor();
    }

    @Override
    protected void initTextColor() {
        super.initTextColor();
        // typeface_path="Roboto-Regular.ttf";
        // Typeface typeface = Typeface.createFromAsset(context.getAssets(), typeface_path);
        // textPaint.setTypeface(typeface);
    }

    @Override
    protected void initBackgroundColor() {
        super.initBackgroundColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRects(canvas);


    }


    private void drawRects(Canvas canvas) {
        int nMiddle = height / 2;
        Rect bounds = new Rect();
        String text = "" + progress + "%";
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        float mt = textPaint.measureText(text) + 40 + text.length();

        int progressX = (int) ((width - mt) * progress / maximum_progress);

        rectP.left = getPaddingLeft();
        rectP.top = nMiddle;
        rectP.right = progressX;
        rectP.bottom = nMiddle;
        if (progress > 2)
            canvas.drawRect(rectP, foregroundPaint);


        if (progress < maximum_progress)
            canvas.drawRect(rectP.width() + mt, nMiddle, width - getPaddingRight(), nMiddle, backgroundPaint);

        canvas.drawText(text, progressX + 10, nMiddle + backgroundStrokeWidth,
                textPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        width = (int) (w - xpad);
        height = (int) (h - ypad);
        setMeasuredDimension(width, height);

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(PADDING, 0, 0, 0);


    }


}