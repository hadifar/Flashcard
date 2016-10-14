package net.hadifar.dope.model;

/**
 * Created by Amir on 10/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class ApiReminderModel {

    private int reminderId;
    private String name;
    private long startTime;
    private long endTime;
    private long remindAheadTime;

    public ApiReminderModel() {
    }

    public ApiReminderModel(int reminderId, String name, long startTime, long endTime, long remindAheadTime) {
        this.reminderId = reminderId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remindAheadTime = remindAheadTime;

    }

    public int getReminderId() {
        return reminderId;
    }

    //
//    public String getType() {
//        return type;
//    }
    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getRemindAheadTime() {
        return remindAheadTime;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setRemindAheadTime(long remindAheadTime) {
        this.remindAheadTime = remindAheadTime;
    }
}
