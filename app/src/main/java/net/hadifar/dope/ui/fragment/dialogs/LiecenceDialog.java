package net.hadifar.dope.ui.fragment.dialogs;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.adapter.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 10/13/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class LiecenceDialog extends BaseDialog {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View view = inflater.inflate(R.layout.dialog_list, container, false);
        ButterKnife.bind(this, view);


        RecyclerView list = (RecyclerView) view.findViewById(R.id.rv_list);
        list.setAdapter(new LAdapter());

        Button cancel = (Button) view.findViewById(R.id.btn_dialog_right);
        cancel.setText(R.string.btn_ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.title_open_source_licenses);

        return view;
    }

    public class LAdapter extends BaseAdapter {

        String[] lTitle;
        String[] lSubtitle;

        public LAdapter() {
            lTitle = getResources().getStringArray(R.array.list_licence_title);
            lSubtitle = getResources().getStringArray(R.array.list_licence_description);
        }

        @Override
        public void addItem(Object entity) {

        }

        @Override
        public void removeItem(Object entity) {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_simple, parent, false);
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).bindView(position);
        }

        @Override
        public int getItemCount() {
            return lTitle != null ? lTitle.length : 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.txt_title)
            TextView title;
            @Bind(R.id.txt_subtitle)
            TextView description;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindView(int pos) {
                title.setText(lTitle[pos]);
                description.setText(lSubtitle[pos]);
            }
        }
    }


}
