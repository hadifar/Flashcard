//package com.march1905.dope.ui.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.march1905.dope.R;
//import com.march1905.dope.core.BundleDataBaseManager;
//import com.march1905.dope.ui.listeners.OnItemClickListener;
//import com.march1905.dope.ui.widget.TextDrawable;
//import com.march1905.dope.model.Category;
//import com.march1905.dope.utils.ColorGenerator;
//import com.march1905.dope.utils.Utils;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Amir on 4/10/2016 AD
// * Project : Flashcard
// * GitHub  : @AmirHadifar
// * Twitter : @AmirHadifar
// */
//public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int ANIMATED_ITEMS_COUNT = 8;
//
//    private Context mContext;
//    private ColorGenerator colorGenerator;
//    private List<Category> items;
//
//    private int lastAnimatedPosition = -1;
//
//    private OnItemClickListener listener;
//
//
//    public CategoryAdapter(Context context, OnItemClickListener listener) {
//        this.items = BundleDataBaseManager.getInstance().getAllCategories();
//        this.mContext = context;
//        this.colorGenerator = ColorGenerator.MATERIAL;
//        this.listener = listener;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {
//
//        View rootView = LayoutInflater.from(mContext).inflate(R.layout.row_category_list_items, viewGroup, false);
//        return new ViewHolder(rootView);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
//
//        runEnterAnimation(viewHolder.itemView, position);
//        ((ViewHolder) viewHolder).bindView(getItem(position));
//
//    }
//
//    private void runEnterAnimation(View view, int position) {
//        if (position >= ANIMATED_ITEMS_COUNT - 1) {
//            return;
//        }
//        if (position > lastAnimatedPosition) {
//            lastAnimatedPosition = position;
//            view.setTranslationY(Utils.getScreenHeight(mContext));
//            view.animate()
//                    .translationY(0)
//                    .setInterpolator(new DecelerateInterpolator(3.f))
//                    .setDuration(700)
//                    .setStartDelay(500)
//                    .start();
//        }
//    }
//
//    public Category getItem(int position) {
//        return items.get(position);
//    }
//
//    public void removeItem(Category category) {
//        items.remove(category);
//    }
//
//    public void addItem(Category category) {
//        items.add(category);
//    }
//
//
//    @Override
//    public long getItemId(int position) {
//        return getItem(position).getId();
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        @Bind(R.id.txt_title_category)
//        public TextView title;
//        @Bind(R.id.txt_subtitle_category)
//        public TextView subTitle;
//        @Bind(R.id.image_thumbnail_category)
//        public ImageView imageView;
//
//        private Category mCategory;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//
//        public void bindView(Category category) {
//            mCategory = category;
//            TextDrawable drawable = TextDrawable.builder().buildRound(category.getTitle().substring(0, 1), colorGenerator.getRandomColor());
//            imageView.setImageDrawable(drawable);
//            title.setText(mCategory.getTitle());
//            subTitle.setText(mCategory.getSubtitle());
//        }
//
//        @OnClick(R.id.icon_overflow_category)
//        public void onClickOverflow(View view) {
//            if (listener != null)
//                listener.onMoreClick(view, mCategory);
//        }
//
//        @OnClick(R.id.cv_root_category)
//        public void onClickCategory() {
//            if (listener != null)
//                listener.onRootClick(mCategory);
//        }
//
//    }
//
//}