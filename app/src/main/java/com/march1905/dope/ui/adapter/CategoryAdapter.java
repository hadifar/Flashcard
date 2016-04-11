package com.march1905.dope.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.march1905.dope.R;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.ui.widget.TextDrawable;
import com.march1905.dope.model.Category;
import com.march1905.dope.utils.ColorGenerator;
import com.march1905.dope.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 4/10/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ANIMATED_ITEMS_COUNT = 10;

    public static final String EXTRA_CATEGORY_ID = "categoryId";
    public static final String EXTRA_CATEGORY_TITLE = "categoryTitle";

    private Context mContext;
    private List<Category> mItems;
    private ColorGenerator mColorGenerator;

    private int lastAnimatedPosition = -1;

    public CategoryAdapter(Context context) {
        mItems = new BundleDataBaseManager().getAllCategories();
        mContext = context;
        mColorGenerator = ColorGenerator.MATERIAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.row_category_list_items, viewGroup, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle onCLick
//                Category item = getItem();
//                Bundle bundle = new Bundle();
//                bundle.putInt(EXTRA_CATEGORY_ID, item.getId());
//                bundle.putString(EXTRA_CATEGORY_TITLE, item.getTitle());
//                ((MainActivity) mContext).displayView(MainActivity.DECKS_FRAG, bundle);
            }
        });
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        runEnterAnimation(viewHolder.itemView,position);
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

    public Category getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_title_category)
        public TextView title;
        @Bind(R.id.txt_subtitle_category)
        public TextView subTitle;
        @Bind(R.id.icon_overflow_category)
        public TextView overflow;
        @Bind(R.id.image_thumbnail_category)
        public ImageView imageView;

        Category mCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(Category category){

            final int position = getAdapterPosition();
            mCategory = category;

            TextDrawable drawable = TextDrawable.builder().buildRound(category.getTitle().substring(0, 1), mColorGenerator.getRandomColor());

            imageView.setImageDrawable(drawable);
            title.setText(category.getTitle());
            subTitle.setText(category.getSubTitle());
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(mContext, view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                    DialogEdit(getItem(position));
                                    return true;
                                case R.id.action_delete:
                                    DialogDelete(getItem(position));
                                    return true;
                            }
                            return false;
                        }
                    });
                }
            });
        }

    }

    public void DialogEdit(final Category category) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alert_category_edit);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText etCategoryName = (EditText) dialog.findViewById(R.id.edtTxtCategoryName);

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etCategoryName.getText().toString().isEmpty()) {
                    category.setTitle(etCategoryName.getText().toString());
                    new BundleDataBaseManager().editFromCategory(category);
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    //TODO:show animation on EditText
                }
            }
        });
    }

    public void DialogDelete(final Category category) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_alert_category_delete);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BundleDataBaseManager().removeFromCategory(category);
                dialog.dismiss();
                mItems.remove(category);
                notifyDataSetChanged();
            }
        });
    }
}