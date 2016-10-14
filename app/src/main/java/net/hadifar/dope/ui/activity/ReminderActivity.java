package net.hadifar.dope.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import net.hadifar.dope.R;
import net.hadifar.dope.model.ApiReminderModel;
import net.hadifar.dope.service.AlarmReceiver;
import net.hadifar.dope.storage.ReminderDatabase;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.utils.TimeUtility;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class ReminderActivity extends BaseActivity {

    // Values for orientation change
    public static final String EXTRA_REMINDER_ID = "reminderID";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.reminder_title)
    EditText titleEditTxt;
    @Bind(R.id.reminder_date)
    EditText dateEditTxt;
    @Bind(R.id.reminder_from)
    EditText timeFromEditTxt;
    @Bind(R.id.reminder_to)
    EditText timeToEditTxt;

    private long startTimeStamp;
    private long endTimeStamp;
    private long timeRemainAheadTime;
    private String title;
    private int mReceivedID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_add_reminder);
        ButterKnife.bind(this);
        setupToolbar();
        initializeValue(savedInstanceState);
        setupViews();
    }

    private void setupViews() {

        titleEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title = s.toString().trim();
                titleEditTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                invalidateOptionsMenu();
            }
        });
    }

    private void initializeValue(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mReceivedID = getIntent().getIntExtra(EXTRA_REMINDER_ID, -1);
        } else {
            mReceivedID = savedInstanceState.getInt(EXTRA_REMINDER_ID, -1);
        }
        if (mReceivedID == -1) { //new reminder
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
            dateEditTxt.setText(TimeUtility.getFullDateFormat(calendar.getTimeInMillis()));
            timeFromEditTxt.setText(TimeUtility.getHMformat(calendar.getTimeInMillis()));
            startTimeStamp = calendar.getTimeInMillis();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            timeToEditTxt.setText(TimeUtility.getHMformat(calendar.getTimeInMillis()));
            endTimeStamp = calendar.getTimeInMillis();
            timeRemainAheadTime = 0;
        } else { //edit mode
            ReminderDatabase rb = new ReminderDatabase(this);
            ApiReminderModel reminder = rb.getReminder(mReceivedID);
            titleEditTxt.setText(reminder.getName());
            dateEditTxt.setText(TimeUtility.getFullDateFormat(reminder.getStartTime()));
            timeFromEditTxt.setText(TimeUtility.getHMformat(reminder.getStartTime()));
            timeToEditTxt.setText(TimeUtility.getHMformat(reminder.getEndTime()));
            startTimeStamp = reminder.getStartTime();
            endTimeStamp = reminder.getEndTime();
            title = reminder.getName();
        }

    }

    protected void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        setTitle(R.string.title_new_reminder);
    }

    // On clicking Time picker
    @OnClick(R.id.reminder_from)
    public void setTimeFrom(View v) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, 1);
        now.set(Calendar.MINUTE, 0);
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(startTimeStamp);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        startTimeStamp = c.getTimeInMillis();   //this line just update our time stamp
                        timeFromEditTxt.setText(TimeUtility.getHMformat(startTimeStamp));
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @OnClick(R.id.reminder_to)
    public void setTimeTo(View v) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, 1);
        now.set(Calendar.MINUTE, 0);
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(startTimeStamp);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        endTimeStamp = c.getTimeInMillis();
                        timeToEditTxt.setText(TimeUtility.getHMformat(endTimeStamp));
                    }
                },
                now.get(Calendar.HOUR_OF_DAY) + 1,
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    //     On clicking Date picker
    @OnClick(R.id.reminder_date)
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startTimeStamp = c.getTimeInMillis();
                        dateEditTxt.setText(TimeUtility.getFullDateFormat(c.getTimeInMillis()));
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void saveReminder() {
        Calendar calendar = Calendar.getInstance();
        if (startTimeStamp < calendar.getTimeInMillis()) {
            // this is old and not possible
            MessageDialog messageDialog = new MessageDialog();
            messageDialog.init(R.string.title_reminder_time_is_passed, R.string.icon_alarm, R.string.msg_reminder_time_is_passed, R.string.btn_ok, null, false, new DialogButtonsClickListener() {
                @Override
                public void onLeftButtonClick() {
                }

                @Override
                public void onRightButtonClick(String... s) {
                }
            });
            messageDialog.show(getSupportFragmentManager(), "ShowDialog");
        } else {
            if (mReceivedID == -1) {
                addReminderService();
            } else {
                updateReminderService();
            }
        }
    }

    private void addReminderService() {
        saveToDb(mReceivedID);
    }

    private void updateReminderService() {
        ReminderDatabase rb = new ReminderDatabase(ReminderActivity.this);
        rb.updateReminder(new ApiReminderModel(mReceivedID, title, startTimeStamp, endTimeStamp, timeRemainAheadTime));
        finish();
    }

    private void saveToDb(int id) {
        ReminderDatabase rb = new ReminderDatabase(this);
        // Creating Reminder
        String name = titleEditTxt.getText().toString();
        rb.addReminder(new ApiReminderModel(id, name, startTimeStamp, endTimeStamp, timeRemainAheadTime));
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(startTimeStamp);
        new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, id);
        finish();
    }

    // Creating the menuItem
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        if (mReceivedID == -1) {
            menu.findItem(R.id.action_delete).setVisible(false);
        } else {
            menu.findItem(R.id.action_delete).setVisible(true);
        }
        if (TextUtils.isEmpty(title)) {
            menu.findItem(R.id.action_save).setIcon(R.drawable.ic_done_disable);
        } else {
            menu.findItem(R.id.action_save).setIcon(R.drawable.ic_done_gray);
        }
        return true;
    }

    // On clicking menuItem buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                if (titleEditTxt.getText().toString().length() == 0) {
                    titleEditTxt.setError(getString(R.string.msg_error_title_can_not_be_empty));
                } else {
                    saveReminder();
                }
                return true;
            case R.id.action_delete:
                deleteReminder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteReminder() {
        ReminderDatabase rb = new ReminderDatabase(this);
        ApiReminderModel deletedReminder = rb.getReminder(mReceivedID);
        rb.deleteReminder(deletedReminder);
        new AlarmReceiver().cancelAlarm(this, mReceivedID);
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_REMINDER_ID, mReceivedID);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_back_to_left, R.anim.slide_in_back_from_right);
    }
}
