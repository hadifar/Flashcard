package com.march1905.dope.ui.listeners;

import android.view.View;

import com.march1905.dope.model.Category;

/**
 * Created by Amir on 4/11/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public interface OnCategoryItemClickListener {

    public void onRootCategoryClick( Category category);
    public void onMoreClick(View v , Category category);
}
