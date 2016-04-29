package net.hadifar.dope.ui.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import net.hadifar.dope.R;
import net.hadifar.dope.utils.Utils;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {

    private final int defaultMargin = Utils.dpToPx(16);

    private Drawable mDivider;

    public DividerItemDecorator(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.default_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.State state) {
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();

        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            int pos = recyclerView.getChildAdapterPosition(child);

            //TODO change this due to settings options
            if (pos == 0 || pos == 5 || pos == 6 || pos == 13 || pos == 14)
                continue;


            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        //TODO change this due to settings options
        if (pos == 6) {
            outRect.top = defaultMargin;
        }
    }
}