package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.model.FlashCardFavoritedItems;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.ui.activity.FavFlashCardViewerActivity;
import net.hadifar.dope.ui.adapter.BaseAdapter;
import net.hadifar.dope.ui.widget.TextDrawable;
import net.hadifar.dope.utils.ColorGenerator;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_flashcard_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.nav_favorite);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favoriteFlashcardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new HeadersAdapter());

    }


    public class HeadersAdapter extends BaseAdapter {

        private List<FlashCardFavoritedItems> mItems;


        public HeadersAdapter() {
            mItems = AppDataBaseManager.getInstance().getFavoritedFlashCardItems();

        }

        @Override
        public HeadersAdapter.HeaderHolder onCreateViewHolder(ViewGroup viewGroup, final int itemType) {

            View rootView = getActivity().getLayoutInflater().inflate(R.layout.row_base_list_items, viewGroup, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_ID, mRecyclerView.getChildAdapterPosition(view));
                    getActivity().startActivity(FavFlashCardViewerActivity.createIntent(getActivity(), mRecyclerView.getChildAdapterPosition(view)));
                }
            });
            return new HeaderHolder(rootView);
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

        public class HeaderHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView imageView;

            public HeaderHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.txt_title_base);
                imageView = (ImageView) itemView.findViewById(R.id.img_thumbnail_base);
            }

        }

        @Override
        public void addItem(Object entity) {

        }

        @Override
        public void removeItem(Object entity) {

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextDrawable drawable = TextDrawable.builder().buildRound(mItems.get(position).getWord().substring(0, 1), generator.getRandomColor(mItems.get(position).getWord()));
            HeaderHolder holder1 = ((HeaderHolder) holder);
            holder1.imageView.setImageDrawable(drawable);
            holder1.title.setText(mItems.get(position).getWord());
        }
    }

}
