package net.hadifar.dope.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.hadifar.dope.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 4/28/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class SettingsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<String> items;

    public SettingsAdapter(Context context) {
        mContext = context;
        items = Arrays.asList(mContext.getResources().getStringArray(R.array.listArray));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.row_setting_items, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        ((ViewHolder) viewHolder).bindView(getItem(position));

    }


    public String getItem(int position) {
        return items.get(position);
    }


    @Override
    public void removeItem(Object entity) {
    }


    @Override
    public void addItem(Object entity) {
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_setting_title)
        TextView setting;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(String s) {
            setting.setText(s);
        }


    }
}
