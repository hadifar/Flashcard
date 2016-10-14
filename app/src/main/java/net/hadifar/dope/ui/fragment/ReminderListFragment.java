package net.hadifar.dope.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hadifar.dope.R;
import net.hadifar.dope.model.ApiReminderModel;
import net.hadifar.dope.service.AlarmReceiver;
import net.hadifar.dope.storage.ReminderDatabase;
import net.hadifar.dope.ui.activity.ReminderActivity;
import net.hadifar.dope.ui.adapter.ReminderAdapter;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.ui.listeners.OnItemClickListener;
import net.hadifar.dope.ui.listeners.OnLongClickListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class ReminderListFragment extends BaseListFragment implements OnLongClickListener, OnItemClickListener {

    private ReminderDatabase rb;
    private ReminderAdapter mAdapter;
    private AlarmReceiver alarmReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        ButterKnife.bind(this, rootView);

        mAdapter = new ReminderAdapter(this, this);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new LinearSpaceDecoration(paddingHeight));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        alarmReceiver = new AlarmReceiver();

        rb = new ReminderDatabase(getActivity());

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        List<ApiReminderModel> allReminder = rb.getAllReminders();
        mAdapter.addItems(allReminder);
//        setEmptyViewVisibility(allReminder.size() > 0);
        showFloatingButton();
    }


    @Override
    public void onRootClick(int id) {
        selectReminder(id);
    }

    @Override
    public void onLongPressed(final int id) {
        MessageDialog delete = new MessageDialog();
        delete.init(R.string.nav_reminder, R.string.icon_delete, R.string.msg_delete_reminder, R.string.action_yes, R.string.action_abort, false, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                ApiReminderModel temp = rb.getReminder(id);
                rb.deleteReminder(temp);
                List<ApiReminderModel> allReminders = rb.getAllReminders();
                mAdapter.addItems(allReminders);
                alarmReceiver.cancelAlarm(getActivity(), id);
            }

            @Override
            public void onRightButtonClick(String... s) {
            }
        });
        delete.show(getFragmentManager(), "DeleteReminder");
    }


    // On clicking a reminder item
    private void selectReminder(int mClickID) {
        // Create intent to edit the reminder
        // Put reminder id as extra
        Intent i = new Intent(getActivity(), ReminderActivity.class);
        i.putExtra(ReminderActivity.EXTRA_REMINDER_ID, mClickID);
        startActivity(i);
    }

    @OnClick(R.id.fab_add_new_card)
    public void addReminderClick() {
        Intent intent = new Intent(getActivity(), ReminderActivity.class);
        startActivity(intent);
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        List<ApiReminderModel> allReminder = rb.getAllReminders();
//        mAdapter.addItems(allReminder);
//        if (allReminder.size() > 0) {
//            emptyContainer.setVisibility(View.GONE);
//        } else {
//            emptyContainer.setVisibility(View.VISIBLE);
//        }
//    }

//
//    private void setEmptyViewVisibility(boolean isSizeBiggerThanZero) {
//        emptyContainer.setVisibility(isSizeBiggerThanZero ? View.GONE : View.VISIBLE);
//    }
}
