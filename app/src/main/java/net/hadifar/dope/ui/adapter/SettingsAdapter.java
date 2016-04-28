package net.hadifar.dope.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.hadifar.dope.model.BaseEntity;

/**
 * Created by Amir on 4/28/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class SettingsAdapter extends BaseAdapter {

    private Context context;

    public SettingsAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void addItem(BaseEntity entity) {
    }

    @Override
    public void removeItem(BaseEntity entity) {
    }
}
