package net.hadifar.dope.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.ui.listeners.OnCardClickListener;
import net.hadifar.dope.ui.widget.TextDrawable;
import net.hadifar.dope.utils.ColorGenerator;
import net.hadifar.dope.utils.Utils;

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
public class ListAdapter extends BaseAdapter {

    public ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    private Context mContext;
    private List<BaseEntity> items;

    private int lastAnimatedPosition = -1;

    private OnCardClickListener listener;

    public ListAdapter(Context context, List<BaseEntity> items, OnCardClickListener listener) {
        this.items = items;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.row_base_list_items, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        runEnterAnimation(viewHolder.itemView, position);
        ((ViewHolder) viewHolder).bindView(getItem(position));

    }

    private void runEnterAnimation(View view, int position) {

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


    public void removeItem(Object entity) {
        items.remove(entity);
        notifyDataSetChanged();
    }


    public void addItem(Object entity) {
        items.add((BaseEntity) entity);
        notifyDataSetChanged();
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

        @Bind(R.id.txt_title_base)
        public TextView title;
        @Bind(R.id.txt_subtitle_base)
        public TextView subTitle;
        @Bind(R.id.img_thumbnail_base)
        public ImageView imageView;

        private BaseEntity entity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(BaseEntity baseEntity) {

            entity = baseEntity;

            TextDrawable drawable = TextDrawable.builder().buildRound(entity.getTitle().substring(0, 1), colorGenerator.getRandomColor(entity.getTitle()));

            imageView.setImageDrawable(drawable);
            title.setText(entity.getTitle());

            if (!TextUtils.isEmpty(entity.getSubtitle())) {
                subTitle.setVisibility(View.VISIBLE);
                subTitle.setText(entity.getSubtitle());
            } else
                subTitle.setVisibility(View.GONE);
        }

        @OnClick(R.id.icon_overflow_base)
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
