package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import net.hadifar.dope.R;
import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.ui.activity.FlashCardViewerActivity;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.utils.AnimationHelper;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */


public class FragmentFlashCardContent extends Fragment {


    private FlashCard flashCard;
    private boolean isAnswer = false;
    private boolean isPopupVisible = false;


    private AppDataBaseManager dataBaseManager = AppDataBaseManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flashcard_content, container, false);
    }

    @Override
    public void onViewCreated(final View rootView, Bundle savedInstanceState) {

        initCardView(rootView);

        initExpandView(rootView);

    }

    public void initCardView(View rootView) {

        //text watcher
        final TextSwitcher word = (TextSwitcher) rootView.findViewById(R.id.flashcardWord);
        word.setFactory(mFactory);
        word.setText(flashCard.getTitle());

        //use whole layout to increase touch area
        final LinearLayout textLayout = (LinearLayout) rootView.findViewById(R.id.textLayout);
        textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnswer) {
                    isAnswer = false;
                    word.setText(flashCard.getTitle());
                } else {
                    isAnswer = true;
                    word.setText(flashCard.getPersian());
                }
            }
        });

        //expand layout
        final CardView popupLayout = (CardView) rootView.findViewById(R.id.popupLayout);
        //button expand
        final TextView buttonExpand = (TextView) rootView.findViewById(R.id.buttonExpand);
        buttonExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) {
                    isPopupVisible = false;
                    AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_chevron_down));
                    AnimationHelper.collapse(popupLayout);
                } else {
                    isPopupVisible = true;
                    AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_chevron_up));
                    AnimationHelper.expand(popupLayout);
                }
            }
        });

        //bookmark button
        final TextView bookmark = (TextView) rootView.findViewById(R.id.buttonFavorite);
        if (dataBaseManager.isFavoritedFlashCard(flashCard.getId())) {
            bookmark.setText(R.string.icon_favorite);
        } else {
            bookmark.setText(R.string.icon_favorite_outline);
        }
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBaseManager.isFavoritedFlashCard(flashCard.getId())) {
                    dataBaseManager.removeFlashCardItemFromFavorites(flashCard.getId());
                    AnimationHelper.changeIconAnim((TextView) v, getString(R.string.icon_favorite_outline));
                } else {
                    dataBaseManager.addFlashCardToFavoritedItems(flashCard);
                    AnimationHelper.changeIconAnim((TextView) v, getString(R.string.icon_favorite));
                }
            }
        });


        final TextView voice = (TextView) rootView.findViewById(R.id.buttonVoice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_volume_up));
                ((FlashCardViewerActivity) getActivity()).speakOut(flashCard.getTitle());
            }
        });

    }

    private void initExpandView(View rootView) {

        TextView pronunciation = (TextView) rootView.findViewById(R.id.pronunciation);
        pronunciation.setText(flashCard.getPronunciation());

        TextView synonym = (TextView) rootView.findViewById(R.id.synonym);
        synonym.setText(flashCard.getSynonym());

        TextView ex1 = (TextView) rootView.findViewById(R.id.example1);
        ex1.setText("1 - " + flashCard.getExample1());

        TextView ex2 = (TextView) rootView.findViewById(R.id.example2);
        ex2.setText("2 - " + flashCard.getExample2());

        TextView ex3 = (TextView) rootView.findViewById(R.id.example3);
        if (flashCard.getExample3() == null || flashCard.getExample3().equals("")) {
            ex3.setVisibility(View.INVISIBLE);
        } else
            ex3.setText("3 - " + flashCard.getExample3());
    }


    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            // Create a new TextView
            TextView t = new TextView(getActivity());
            t.setLayoutParams(new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.MATCH_PARENT, TextSwitcher.LayoutParams.MATCH_PARENT));
            t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(getActivity(), R.style.Textview_Dark_Large);
            t.setBackgroundResource(R.color.transparent);
            return t;
        }
    };

    public void setCard(FlashCard card) {
        this.flashCard = card;
    }


}