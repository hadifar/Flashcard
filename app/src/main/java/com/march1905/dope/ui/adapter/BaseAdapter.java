package com.march1905.dope.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.march1905.dope.R;
import com.march1905.dope.model.BaseEntity;
import com.march1905.dope.model.Category;
import com.march1905.dope.ui.listeners.OnItemClickListener;
import com.march1905.dope.ui.widget.TextDrawable;
import com.march1905.dope.utils.ColorGenerator;
import com.march1905.dope.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 4/13/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ANIMATED_ITEMS_COUNT = 8;

    private Context mContext;
    private ColorGenerator colorGenerator;
    private List<? extends BaseEntity> items;

    private int lastAnimatedPosition = -1;

    private OnItemClickListener listener;


    public BaseAdapter(Context context, List<? extends BaseEntity> items, OnItemClickListener listener) {
        this.items = items;
        this.mContext = context;
        this.colorGenerator = ColorGenerator.MATERIAL;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.row_category_list_items, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        runEnterAnimation(viewHolder.itemView, position);
        ((ViewHolder) viewHolder).bindView(getItem(position));

    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .setStartDelay(500)
                    .start();
        }
    }

    public BaseEntity getItem(int position) {
        return items.get(position);
    }

    public void removeItem(BaseEntity entity) {
        items.remove(entity);
    }

    public void addItem(BaseEntity entity) {
        items.remove(entity);
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_title_category)
        public TextView title;
        @Bind(R.id.txt_subtitle_category)
        public TextView subTitle;
        @Bind(R.id.image_thumbnail_category)
        public ImageView imageView;

        private BaseEntity entity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(BaseEntity e) {
            entity = e;
            TextDrawable drawable = TextDrawable.builder().buildRound(entity.getTitle().substring(0, 1), colorGenerator.getRandomColor());
            imageView.setImageDrawable(drawable);
            title.setText(entity.getTitle());
            if (!TextUtils.isEmpty(entity.getSubtitle()))
                subTitle.setText(entity.getSubtitle());
            else
                subTitle.setVisibility(View.GONE);
        }

        @OnClick(R.id.icon_overflow_category)
        public void onClickOverflow(View view) {
            if (listener != null)
                listener.onMoreClick(view, entity);
        }

        @OnClick(R.id.cv_root_category)
        public void onClickCategory() {
            if (listener != null)
                listener.onRootClick(entity);
        }

    }

}
