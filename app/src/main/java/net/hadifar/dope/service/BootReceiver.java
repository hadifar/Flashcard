package net.hadifar.dope.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.hadifar.dope.model.ApiReminderModel;
import net.hadifar.dope.storage.ReminderDatabase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            ReminderDatabase rb = new ReminderDatabase(context);
            Calendar mCalendar = Calendar.getInstance();
            long now = mCalendar.getTimeInMillis();
            AlarmReceiver mAlarmReceiver = new AlarmReceiver();
            List<ApiReminderModel> reminders = rb.getAllReminders();
            for (ApiReminderModel rm : reminders) {
                if (rm.getStartTime() >= now) {
                    int mReceivedID = rm.getReminderId();
                    mCalendar.setTimeInMillis(rm.getStartTime());
                    mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                } else {
                    rb.deleteReminder(rm);
                }
            }
        }
    }
}
