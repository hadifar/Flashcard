package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.utils.AnimationHelper;
import net.hadifar.dope.R;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentFlashCardContent extends BaseFragment /*implements TextToSpeech.OnInitListener */{

    TextToSpeech mTextToSpeech;
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

//        mTextToSpeech = new TextToSpeech(this, this);

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


        //void button
//        final TextView voice = (TextView) rootView.findViewById(R.id.buttonVoice);
//        voice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AnimationHelper.changeIconAnim((TextView) view, getString(R.string.icon_volume_up));
//                MainActivity.speakOut(flashCard.getWord());
//            }
//        });

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

//
//    @Override
//    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//
//            int result = mTextToSpeech.setLanguage(Locale.US);
//
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Toast.makeText(this, R.string.your_language_not_supported_please_install, Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, R.string.please_install_google_voice, Toast.LENGTH_LONG).show();
//            Intent installIntent = new Intent();
//            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//            startActivity(installIntent);
//        }
//
//    }
//
//
//    public void onDestroy() {
//        if (mTextToSpeech != null) {
//            mTextToSpeech.stop();
//            mTextToSpeech.shutdown();
//        }
//        super.onDestroy();
//    }
//
//    public static void speakOut(String text) {
//        if (Build.VERSION.SDK_INT < 21)
//            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//        else
//            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//    }


}
