package net.hadifar.dope.ui.activity;

import android.os.Bundle;

import net.hadifar.dope.R;

import butterknife.ButterKnife;

/**
 * Created by Amir on 9/19/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class SearchActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_back_to_left, R.anim.slide_in_back_from_right);
    }
}
