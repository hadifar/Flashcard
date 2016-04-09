package com.march1905.dope.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.march1905.dope.R;
import com.march1905.dope.activity.MainActivity;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.customui.TextDrawable;
import com.march1905.dope.fragment.dialogs.FragmentNewCategory;
import com.march1905.dope.model.Category;
import com.march1905.dope.utils.ColorGenerator;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentCategories extends DefaultFragment {

    public static final String EXTRA_CATEGORY_ID = "categoryId";
    public static final String EXTRA_CATEGORY_TITLE = "categoryTitle";

    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private RecyclerView mRecyclerView;
    private HeadersAdapter adapter;
    private CharSequence mTitle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //this.setHasOptionsMenu(true); // We use this so we can have specific ActionBar actions/icons for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HeadersAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addNewCategory);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNewCategory newCategoryDialog = new FragmentNewCategory();
                newCategoryDialog.show(getFragmentManager(), "NewCategoryDialog");
            }
        });
    }


    public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {

        private Context mContext;
        private List<Category> mItems;


        public HeadersAdapter(Context context) {
            mItems = new BundleDataBaseManager().getAllCategories();
            mContext = context;
        }

        @Override
        public HeadersAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

            View rootView = getActivity().getLayoutInflater().inflate(R.layout.row_category_list_items, viewGroup, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Category item = getItem(mRecyclerView.getChildAdapterPosition(view));
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_CATEGORY_ID, item.getId());
                    bundle.putString(EXTRA_CATEGORY_TITLE, item.getTitle());
                    ((MainActivity) getActivity()).displayView(MainActivity.DECKS_FRAG, bundle);
                }
            });
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(mItems.get(position).getTitle().substring(0, 1), generator.getRandomColor());

            viewHolder.imageView.setImageDrawable(drawable);
            viewHolder.title.setText(mItems.get(position).getTitle());
            viewHolder.subTitle.setText(mItems.get(position).getSubTitle());
            viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                    DialogEdit(mItems.get(position));
                                    return true;
                                case R.id.action_delete:
                                    DialogDelete(mItems.get(position));
                                    return true;
                            }
                            return false;
                        }
                    });
                }
            });
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
            public TextView title;
            public TextView subTitle;
            public TextView overflow;
            public ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                subTitle = (TextView) itemView.findViewById(R.id.subTitle);
                overflow = (TextView) itemView.findViewById(R.id.overflow);
                imageView = (ImageView) itemView.findViewById(R.id.imageCategory);
            }

        }

    }

    public void DialogEdit(final Category category) {
        final Dialog dialog = new Dialog(getActivity());
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
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    //TODO:show animation on EditText
                }
            }
        });
    }

    public void DialogDelete(final Category category) {
        final Dialog dialog = new Dialog(getActivity());
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
                adapter.mItems.remove(category);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Get/Backup current title
        mTitle = getActivity().getTitle();


    }

    @Override
    public void onDestroy() {
        // Set title back
        getActivity().setTitle(mTitle);
        super.onDestroy();
    }

}
