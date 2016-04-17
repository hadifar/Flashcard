package net.hadifar.dope.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Amir on 4/17/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class Polygon extends View {

    private int fillColor = 0xffffffff;

    private Paint fillPaint;
    private Path polyPath;

    public Polygon(Context context) {
        super(context);
    }

    public Polygon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Polygon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        fillPaint = new Paint();
        fillPaint.setColor(fillColor);
        fillPaint.setStyle(Paint.Style.FILL);

        polyPath = new Path();
        polyPath.setFillType(Path.FillType.WINDING);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result;

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;

            default:
                // random size if nothing is specified
                result = 500;
                break;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result;

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;

            default:
                // random size if nothing is specified
                result = 500;
                break;
        }
        return result;
    }


    CornerPathEffect corEffect = new CornerPathEffect(5);

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int x = (measuredWidth / 2);
        int y = (measuredHeight / 2);
        int radius = Math.min(x, y);


        float a = (float) (Math.PI * 2) / 6;
        polyPath.reset();
        fillPaint.setPathEffect(corEffect);
        fillPaint.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);


        polyPath.moveTo(radius, 0);
        for (int i = 1; i < 6; i++) {
            polyPath.lineTo((float) (radius * Math.cos(a * i)),
                    (float) (radius * Math.sin(a * i)));
        }
        polyPath.close();

        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(-90);
        canvas.drawPath(polyPath, fillPaint);

        canvas.restore();

        super.onDraw(canvas);
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        fillPaint.setColor(fillColor);
        invalidate();
    }
}