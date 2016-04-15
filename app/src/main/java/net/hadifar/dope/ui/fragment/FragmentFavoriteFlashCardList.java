package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.model.FlashCardFavoritedItems;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.ui.widget.TextDrawable;
import net.hadifar.dope.utils.ColorGenerator;
import net.hadifar.dope.R;
import net.hadifar.dope.storage.AppDataBaseManager;

import java.util.List;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentFavoriteFlashCardList extends BaseListFragment {

    private RecyclerView mRecyclerView;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private CharSequence mTitle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this.setHasOptionsMenu(true); // We use this so we can have specific ActionBar actions/icons for this fragment
        return inflater.inflate(R.layout.fragment_favorite_flashcard_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.drawer_item_favorites);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favoriteFlashcardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new HeadersAdapter());

    }

    @Override
    public void onRootClick(BaseEntity entity) {

    }

    @Override
    public void onMoreClick(View v, BaseEntity entity) {

    }

    public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {

        private List<FlashCardFavoritedItems> mItems;


        public HeadersAdapter() {
            mItems = AppDataBaseManager.getInstance().getFavoritedFlashCardItems();

        }

        @Override
        public HeadersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int itemType) {

            View rootView = getActivity().getLayoutInflater().inflate(R.layout.row_favorite_flashcard_list_items, viewGroup, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_ID, mRecyclerView.getChildAdapterPosition(view));
                    ((MainActivity) getActivity()).displayView(MainActivity.FAVORITE_FRAG_VIEWER, bundle);
                }
            });
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(mItems.get(position).getWord().substring(0, 1), generator.getRandomColor(mItems.get(position).getWord()));
            viewHolder.imageView.setImageDrawable(drawable);
            viewHolder.title.setText(mItems.get(position).getWord());
        }

        public FlashCardFavoritedItems getItem(int position) {
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

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.titleDeck);
                imageView = (ImageView) itemView.findViewById(R.id.imageFlashCard);
            }

        }
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
