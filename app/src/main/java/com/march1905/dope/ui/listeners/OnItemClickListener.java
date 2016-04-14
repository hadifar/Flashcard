package com.march1905.dope.ui.listeners;

import android.view.View;

import com.march1905.dope.model.BaseEntity;
import com.march1905.dope.model.Category;

/**
 * Created by Amir on 4/11/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public interface OnItemClickListener {

    public void onRootClick(BaseEntity entity);

    public void onMoreClick(View v, BaseEntity entity);
}
