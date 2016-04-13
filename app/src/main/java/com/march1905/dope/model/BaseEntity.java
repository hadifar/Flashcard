package com.march1905.dope.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Amir on 4/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public abstract class BaseEntity {

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(dataType = DataType.STRING)
    public String title;

    @DatabaseField(dataType = DataType.STRING)
    public String subtitle;


    public abstract int getId();
    public abstract String getTitle();
    public abstract String getSubtitle();
}
