package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.ui.widget.TextDrawable;
import net.hadifar.dope.utils.ColorGenerator;
import net.hadifar.dope.R;
import net.hadifar.dope.storage.BundleDataBaseManager;
import net.hadifar.dope.ui.fragment.dialogs.NewFlashCardDialog;

import java.util.List;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentFlashCardsList extends DefaultFragment implements NewFlashCardDialog.OnDBChangedListener {

    public final static String EXTRA_FLASHCARD_ID = "flashcardId";
    public final static String EXTRA_FLASHCARD_TITLE = "flashcardTitle";
    Bundle mBundle;

    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private List<FlashCard> mItems;
    private RecyclerView mRecyclerView;
    private HeadersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //this.setHasOptionsMenu(true); // We use this so we can have specific ActionBar actions/icons for this fragment
        return inflater.inflate(R.layout.fragment_flashcards_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBundle = getArguments();
        setTitle(mBundle.getString(EXTRA_TITLE));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.flashcardsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HeadersAdapter();
        mRecyclerView.setAdapter(adapter);


        //This is for floating Btn
        //TODO uncomment this
//        if (mBundle.getInt(CategoryAdapter.EXTRA_CATEGORY_ID) > 2) {
//            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_new_flashcard);
//            fab.setVisibility(View.VISIBLE);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    NewFlashCardDialog newDeckDialog = new NewFlashCardDialog();
//                    newDeckDialog.setBundle(mBundle);
//                    newDeckDialog.setListener(FragmentFlashCardsList.this);
//                    newDeckDialog.show(getFragmentManager(), "NewFlashCard");
//                }
//            });
//        }


    }


    @Override
    public void onDBChanged() {
        mItems = BundleDataBaseManager.getInstance().getFlashCardsForDeckId(mBundle.getInt(EXTRA_ID));
        adapter.notifyDataSetChanged();
    }

    public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {


        public HeadersAdapter() {
            mItems = BundleDataBaseManager.getInstance().getFlashCardsForDeckId(mBundle.getInt(EXTRA_ID));
        }

        @Override
        public HeadersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int itemType) {

            View rootView = getActivity().getLayoutInflater().inflate(R.layout.row_flashcard_list_items, viewGroup, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_FLASHCARD_TITLE, mBundle.getString(EXTRA_TITLE));
                    bundle.putInt(EXTRA_ID, mBundle.getInt(EXTRA_ID));
                    bundle.putInt(EXTRA_FLASHCARD_ID, mRecyclerView.getChildAdapterPosition(view));

                    ((MainActivity) getActivity()).displayView(MainActivity.FLASHCARDS_VIEWER, bundle);
                }
            });
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            TextDrawable drawable = TextDrawable.builder().buildRound(mItems.get(position).getTitle().substring(0, 1), generator.getRandomColor());
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

        public FlashCard getItem(int position) {
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
                imageView = (ImageView) itemView.findViewById(R.id.imageFlashCard);
                title = (TextView) itemView.findViewById(R.id.titleDeck);
                overflow = (TextView) itemView.findViewById(R.id.icon_overflow_category);
            }

        }
    }


    public void DialogEdit(final FlashCard flashCard) {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_alert_flashcard_edit);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//
//        final EditText editText = (EditText) dialog.findViewById(R.id.edtTxtFlashcardName);
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
//                    flashCard.setWord(editText.getText().toString());
//                     BundleDataBaseManager.getInstance().editFromFlashCard(flashCard);
//                    adapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                } else {
//                    //TODO:show animation on EditText
//                }
//            }
//        });
    }

    public void DialogDelete(final FlashCard flashCard) {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_alert_flashcard_delete);
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
//                 BundleDataBaseManager.getInstance().removeFromFlashCard(flashCard);
//                mItems.removeItem(flashCard);
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//        });
    }
}
