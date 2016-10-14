package net.hadifar.dope.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.model.ApiReminderModel;
import net.hadifar.dope.ui.listeners.OnItemClickListener;
import net.hadifar.dope.ui.listeners.OnLongClickListener;
import net.hadifar.dope.utils.TimeUtility;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class ReminderAdapter extends BaseAdapter {

    private List<ApiReminderModel> reminders;

    private OnItemClickListener onItemClickListener;
    private OnLongClickListener onLongClickListener;

    public ReminderAdapter(OnItemClickListener listener, OnLongClickListener onLongClickListener) {
        this.onItemClickListener = listener;
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_reminder, parent, false);
        return new ReminderHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReminderHolder) holder).bindView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return reminders != null ? reminders.size() : 0;
    }

    public ApiReminderModel getItem(int position) {
        return reminders.get(position);
    }

    public void addItems(List<ApiReminderModel> allReminders) {
        reminders = allReminders;
        notifyDataSetChanged();
    }

    @Override
    public void addItem(Object entity) {

    }

    @Override
    public void removeItem(Object entity) {

    }

    public class ReminderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_thumbnail)
        ImageView imageView;
        @Bind(R.id.txt_title)
        TextView title;
        @Bind(R.id.txt_subtitle)
        TextView subtitle;
        @Bind(R.id.root)
        View root;

        ApiReminderModel reminder;

        public ReminderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(ApiReminderModel item) {

            reminder = item;
            title.setText(item.getName());
            subtitle.setText(TimeUtility.getFullFormat(item.getStartTime()));

            root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClickListener.onLongPressed(reminder.getReminderId());
                    return false;
                }
            });

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onRootClick(reminder.getReminderId());
                }
            });

        }


    }
}
