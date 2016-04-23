package net.hadifar.dope.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import net.hadifar.dope.R;
import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.ui.activity.FlashCardViewerActivity;
import net.hadifar.dope.utils.AnimationHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */


public class FragmentFlashCardContent extends Fragment {

    @Bind(R.id.txtswitcher_word)
    TextSwitcher word;
    @Bind(R.id.popup_layout)
    CardView popupLayout;
    @Bind(R.id.btn_favorite)
    TextView favorite;

    private FlashCard flashCard;
    private boolean isAnswer = false;
    private boolean isPopupVisible = false;


    private AppDataBaseManager dataBaseManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_flashcard_content, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(final View rootView, Bundle savedInstanceState) {

        populateData();

        initExpandView(rootView);

    }

    public void populateData() {

        dataBaseManager = AppDataBaseManager.getInstance();
        //text watcher
        word.setFactory(mFactory);
        word.setText(flashCard.getTitle());

        if (dataBaseManager.isFavoritedFlashCard(flashCard.getId())) {
            favorite.setText(R.string.icon_favorite);
        } else {
            favorite.setText(R.string.icon_favorite_outline);
        }


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
        if (TextUtils.isEmpty(flashCard.getExample3())) {
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

            if (Build.VERSION.SDK_INT < 23) {
                t.setTextAppearance(getActivity(), R.style.Textview_Dark_Medium);
            } else {
                t.setTextAppearance(R.style.Textview_Dark_Medium);
            }
            t.setBackgroundResource(R.color.transparent);
            return t;
        }
    };


    public void setCard(FlashCard card) {
        this.flashCard = card;
    }

    @OnClick(R.id.textLayout)
    public void onClickTextLayout() {
        if (isAnswer) {
            isAnswer = false;
            word.setText(flashCard.getTitle());
        } else {
            isAnswer = true;
            word.setText(flashCard.getPersian());
        }
    }

    @OnClick(R.id.btn_expand)
    public void onClickExpand(View view) {
        if (isPopupVisible) {
            isPopupVisible = false;
            AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_chevron_up));
            AnimationHelper.collapse(popupLayout);
        } else {
            isPopupVisible = true;
            AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_chevron_down));
            AnimationHelper.expand(popupLayout);
        }
    }

    @OnClick(R.id.btn_voice)
    public void onClickVoice(View view) {
        AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_volume_up));
        ((FlashCardViewerActivity) getActivity()).speakOut(flashCard.getTitle());
    }

    @OnClick(R.id.btn_favorite)
    public void onClickFavorite(View v) {
        if (dataBaseManager.isFavoritedFlashCard(flashCard.getId())) {
            dataBaseManager.removeFlashCardItemFromFavorites(flashCard.getId());
            AnimationHelper.changeIconAnim((TextView) v, getString(R.string.icon_favorite_outline));
        } else {
            dataBaseManager.addFlashCardToFavoritedItems(flashCard);
            AnimationHelper.changeIconAnim((TextView) v, getString(R.string.icon_favorite));
        }
    }

}