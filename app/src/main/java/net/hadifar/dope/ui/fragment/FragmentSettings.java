package net.hadifar.dope.ui.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.model.SettingEntity;
import net.hadifar.dope.storage.SettingsManager;
import net.hadifar.dope.ui.adapter.BaseAdapter;
import net.hadifar.dope.ui.listeners.OnBindView;
import net.hadifar.dope.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends BaseFragment {

    @Bind(R.id.rv_settings_list)
    RecyclerView recyclerView;


    private final int SETTING_HEADER = 0;
    private final int SETTING_SIMPLE_TEXT = 1;
    private final int SETTING_COMPLEX_TEXT = 2;
    private final int SETTING_SWITCH = 3;
    private final int SETTING_DIALOG = 4;

    private int settingCount = 0;
    private int settingSettings = settingCount++;
    private int settingLanguage = settingCount++;
    private int settingAnimation = settingCount++;
    private int settingSupport = settingCount++;
    private int settingOpenSource = settingCount++;

    List<SettingEntity> settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        BaseAdapter adapter = new SettingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

        initSettings();

        recyclerView.setAdapter(adapter);

    }

    private void initSettings() {
        settings = new ArrayList<>();
        settings.add(new SettingEntity(SETTING_HEADER, R.string.setting_settings, null));
        settings.add(new SettingEntity(SETTING_SIMPLE_TEXT, R.string.setting_language, null));
        settings.add(new SettingEntity(SETTING_SWITCH, R.string.setting_animation, null));
        settings.add(new SettingEntity(SETTING_HEADER, R.string.setting_support, null));
        settings.add(new SettingEntity(SETTING_SIMPLE_TEXT, R.string.setting_open_source_licenses, R.string.setting_license_details));
        settings.add(new SettingEntity(SETTING_SWITCH, R.string.setting_animation, null));
        settings.add(new SettingEntity(SETTING_SWITCH, R.string.setting_animation, null));
        settings.add(new SettingEntity(SETTING_SWITCH, R.string.setting_animation, null));
        settings.add(new SettingEntity(SETTING_SWITCH, R.string.setting_animation, null));

    }

    public class SettingsAdapter extends BaseAdapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int itemType) {

            View rootView;
            switch (itemType) {
                case SETTING_HEADER:
                    rootView = LayoutInflater.from(getActivity()).inflate(R.layout.row_setting_header_items, viewGroup, false);
                    return new HeaderHolder(rootView);
                case SETTING_SIMPLE_TEXT:
                    rootView = LayoutInflater.from(getActivity()).inflate(R.layout.row_setting_items, viewGroup, false);
                    return new SimpleHolder(rootView);
                default:
                    return null;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == settingSettings || position == settingSupport)
                return SETTING_HEADER;
            else
                return SETTING_SIMPLE_TEXT;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
            ((OnBindView) viewHolder).bindView(getItem(position));
        }


        public SettingEntity getItem(int position) {
            return settings.get(position);
        }


        @Override
        public void removeItem(Object entity) {
        }


        @Override
        public void addItem(Object entity) {
        }


        @Override
        public int getItemCount() {
            return settings.size();
        }

        public class HeaderHolder extends RecyclerView.ViewHolder implements OnBindView {

            @Bind(R.id.txt_setting_title)
            TextView setting;

            public HeaderHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindView(SettingEntity settingEntity) {
                setting.setText(settingEntity.getTitle());
            }
        }

        public class SimpleHolder extends RecyclerView.ViewHolder implements OnBindView {

            @Bind(R.id.txt_setting_title)
            TextView title;

            @Bind(R.id.txt_setting_subtitle)
            TextView subtitle;

            public SimpleHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindView(SettingEntity settingEntity) {
                title.setText(settingEntity.getTitle());

                if (settingEntity.getSubtitle() != null) {
                    subtitle.setText(settingEntity.getSubtitle());
                } else {
                    subtitle.setVisibility(View.GONE);
                }
            }
        }

    }

    public class DividerItemDecorator extends RecyclerView.ItemDecoration {

        private int defaultMargin = 0;

        private Drawable mDivider;

        public DividerItemDecorator(Context context) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.default_divider);
            defaultMargin = Utils.dpToPx(16);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.State state) {
            int left = recyclerView.getPaddingLeft();
            int right = recyclerView.getWidth() - recyclerView.getPaddingRight();

            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                int pos = recyclerView.getChildAdapterPosition(child);

                if (pos == settingSettings || pos == settingSupport)
                    continue;


                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int pos = parent.getChildAdapterPosition(view);
            if (pos == settingSupport) {
                outRect.top = defaultMargin;
            }
        }
    }
}
