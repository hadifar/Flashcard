package net.hadifar.dope.ui.adapter;


import android.support.v7.widget.RecyclerView;

import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.utils.ColorGenerator;

/**
 * Created by Amir on 4/17/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    //left blank
    public abstract void addItem(BaseEntity entity);

    public abstract void removeItem(BaseEntity entity);
}