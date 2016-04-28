package net.hadifar.dope.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.ui.listeners.OnPolygonClickListener;
import net.hadifar.dope.ui.widget.Polygon;
import net.hadifar.dope.utils.Utils;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 4/17/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class GridAdapter extends BaseAdapter {

    private static final int GRID_ANIMATION_DELAY = 600;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();


    private final Context context;
    private final int cellSize;

    private final List<String> icons;

    private boolean lockedAnimations = false;
    private int lastAnimatedItem = -1;

    private OnPolygonClickListener listener;

    public GridAdapter(Context context, OnPolygonClickListener listener) {
        this.context = context;
        this.cellSize = Utils.getScreenWidth(context) / 2;
        this.icons = Arrays.asList(context.getResources().getStringArray(R.array.icons_learning_method));
        this.listener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_base_grid_items, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.height = cellSize;
        layoutParams.width = cellSize;

        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runAnimation((ViewHolder) viewHolder, position);
        ((ViewHolder) viewHolder).bindView(getItem(position));
    }

    private void runAnimation(final ViewHolder holder, int position) {

        if (!lockedAnimations) {
            if (lastAnimatedItem == holder.getPosition()) {
                setLockedAnimations(true);
            }

            long animationDelay = GRID_ANIMATION_DELAY + holder.getPosition() * 30;

            holder.polygon.setScaleY(0);
            holder.polygon.setScaleX(0);

            holder.polygon.animate()
                    .scaleY(1)
                    .scaleX(1)
                    .setDuration(200)
                    .setInterpolator(INTERPOLATOR)
                    .setStartDelay(animationDelay)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            holder.iconView.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
        }

        if (lastAnimatedItem < position) lastAnimatedItem = position;
    }

    public String getItem(int position) {
        return icons.get(position);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    @Override
    public void addItem(BaseEntity entity) {
    }

    @Override
    public void removeItem(BaseEntity entity) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cv_polygon)
        Polygon polygon;

        @Bind(R.id.icon_polygon)
        TextView iconView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(String icon) {
            int color = colorGenerator.getRandomColor(icon);
            polygon.setFillColor(color);
            iconView.setText(icon);

        }

        @OnClick(R.id.cv_polygon)
        public void onPolyClick(View v) {
            //TODO change null value
            if (listener != null)
                listener.onRootClickListener(v, null);
        }
    }

    public void setLockedAnimations(boolean lockedAnimations) {
        this.lockedAnimations = lockedAnimations;
    }
}
