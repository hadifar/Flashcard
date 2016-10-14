package net.hadifar.dope.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.hadifar.dope.model.ApiReminderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class ReminderDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ReminderDatabase";
    // Table name
    private static final String TABLE_REMINDERS = "ReminderTable";
    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_START_TIME_STAMP = "startTimeStamp";
    private static final String KEY_END_TIME_STAMP = "endTimeStamp";
    private static final String KEY_REMAIN_AHEAD_DELAY = "timeRemainAhead";

    public ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_START_TIME_STAMP + " INTEGER,"
                + KEY_END_TIME_STAMP + " INTEGER,"
                + KEY_REMAIN_AHEAD_DELAY + " INTEGER" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    public void addReminder(ApiReminderModel reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, reminder.getReminderId());
        values.put(KEY_TITLE, reminder.getName());
        values.put(KEY_START_TIME_STAMP, reminder.getStartTime());
        values.put(KEY_END_TIME_STAMP, reminder.getEndTime());
        values.put(KEY_REMAIN_AHEAD_DELAY, reminder.getRemindAheadTime());
        // Inserting Row
        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    public void addReminderList(List<ApiReminderModel> list) {
        if (list == null || list.size() == 0) return;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (ApiReminderModel reminder : list) {
            values.put(KEY_ID, reminder.getReminderId());
            values.put(KEY_TITLE, reminder.getName());
            values.put(KEY_START_TIME_STAMP, reminder.getStartTime());
            values.put(KEY_END_TIME_STAMP, reminder.getEndTime());
            values.put(KEY_REMAIN_AHEAD_DELAY, reminder.getRemindAheadTime());
            // Inserting Row
            db.insert(TABLE_REMINDERS, null, values);
        }
        db.close();
    }

    // Getting single Reminder
    public ApiReminderModel getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_START_TIME_STAMP,
                                KEY_END_TIME_STAMP,
                                KEY_REMAIN_AHEAD_DELAY
                        }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ApiReminderModel apiReminderModel = new ApiReminderModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getLong(2), cursor.getLong(3), cursor.getLong(4));
        cursor.close();
        return apiReminderModel;
//
    }

    // Getting all Reminders
    public List<ApiReminderModel> getAllReminders() {
        List<ApiReminderModel> reminderList = new ArrayList<>();
        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ApiReminderModel reminder = new ApiReminderModel();
                reminder.setReminderId(Integer.parseInt(cursor.getString(0)));
                reminder.setName(cursor.getString(1));
                reminder.setStartTime(cursor.getLong(2));
                reminder.setEndTime(cursor.getLong(3));
                reminder.setRemindAheadTime(cursor.getLong(4));
                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reminderList;
    }

    // Getting Reminders Count
    public int getRemindersCount() {
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    //     Updating single Reminder
    public int updateReminder(ApiReminderModel reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, reminder.getReminderId());
        values.put(KEY_TITLE, reminder.getName());
        values.put(KEY_START_TIME_STAMP, reminder.getStartTime());
        values.put(KEY_END_TIME_STAMP, reminder.getEndTime());
        values.put(KEY_REMAIN_AHEAD_DELAY, reminder.getRemindAheadTime());
//        Updating row
        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getReminderId())});
    }

    // Deleting single Reminder
    public void deleteReminder(ApiReminderModel reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getReminderId())});
        db.close();
    }

    public void deleteAllReminders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_REMINDERS);
        db.close();
    }
}
