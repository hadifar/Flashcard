package net.hadifar.dope.ui.listeners;

import android.view.View;

import net.hadifar.dope.model.BaseEntity;

/**
 * Created by Amir on 4/11/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public interface OnCardClickListener {

    public void onRootClick(BaseEntity entity);

    public void onMoreClick(View v, BaseEntity entity);
}
