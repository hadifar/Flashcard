package com.march1905.dope.ui.fragment;

import android.app.Dialog;
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
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.model.Deck;
import com.march1905.dope.ui.activity.MainActivity;
import com.march1905.dope.ui.fragment.dialogs.NewDeckDialog;
import com.march1905.dope.ui.widget.TextDrawable;
import com.march1905.dope.utils.ColorGenerator;

import java.util.List;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentDecks extends DefaultFragment implements NewDeckDialog.OnDBChangedListener{

    public static final String EXTRA_DECK_ID = "deckId";
    public static final String EXTRA_DECK_TITLE = "deckTitle";

    Bundle mBundle;

    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private RecyclerView mRecyclerView;
    private List<Deck> mItems;
    private HeadersAdapter adapter;

    private CharSequence mTitle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_decks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBundle = getArguments();
        getActivity().setTitle(mBundle.getString(FragmentCategory.EXTRA_CATEGORY_TITLE));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_deck);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HeadersAdapter();
        mRecyclerView.setAdapter(adapter);

//        if (mBundle.getInt(CategoryAdapter.EXTRA_CATEGORY_ID)>2) {
//            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_deck);
//            fab.setVisibility(View.VISIBLE);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    NewDeckDialog newDeckDialog = new NewDeckDialog();
//                    newDeckDialog.setBundle(mBundle);
//                    newDeckDialog.setListener(FragmentDecks.this);
//                    newDeckDialog.show(getFragmentManager(),"NewDeckDialog");
//                }
//            });
//        }
    }


    @Override
    public void onDBChanged() {
        mItems = BundleDataBaseManager.getInstance().getDecksForCategoryId(mBundle.getInt(FragmentCategory.EXTRA_CATEGORY_ID));
//        adapter.notifyDataSetChanged();
    }


    public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {


        public HeadersAdapter() {
            mItems = BundleDataBaseManager.getInstance().getDecksForCategoryId(mBundle.getInt(FragmentCategory.EXTRA_CATEGORY_ID));
        }

        @Override
        public HeadersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int itemType) {

            View rootView = getActivity().getLayoutInflater().inflate(R.layout.row_deck_list_items, viewGroup, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Deck item = getItem(mRecyclerView.getChildAdapterPosition(view));
//                    bundle.putInt(CategoryAdapter.EXTRA_CATEGORY_ID, item.getCategoryId());
                    bundle.putInt(EXTRA_DECK_ID, item.getId());
                    bundle.putString(EXTRA_DECK_TITLE, item.getTitle());
                    ((MainActivity) getActivity()).displayView(MainActivity.FLASHCARDS_FRAG, bundle);
                }
            });
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final int position) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(mItems.get(position).getTitle().substring(0, 1), generator.getRandomColor());
            viewHolder.imageView.setImageDrawable(drawable);
            viewHolder.title.setText(mItems.get(position).getTitle());
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

        public Deck getItem(int position) {
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
            public ImageView imageView;
            public TextView overflow;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.titleDeck);
                imageView = (ImageView)itemView.findViewById(R.id.imageDeck);
                overflow = (TextView)itemView.findViewById(R.id.icon_overflow_category);
            }

        }
    }


    public void DialogEdit(final Deck deck) {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_alert_deck_edit);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//
//        final EditText editText = (EditText) dialog.findViewById(R.id.edtTxtDeckName);
//
//        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!editText.getText().toString().isEmpty()) {
//                    deck.setTitle(editText.getText().toString());
//                    BundleDataBaseManager.getInstance().editFromDeck(deck);
//                    adapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                } else {
//                    //TODO:show animation on EditText
//                }
//            }
//        });
    }

    public void DialogDelete(final Deck deck) {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_alert_deck_delete);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//
//
//        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BundleDataBaseManager.getInstance().removeFromDeck(deck);
//                mItems.removeItem(deck);
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//        });
    }

}
