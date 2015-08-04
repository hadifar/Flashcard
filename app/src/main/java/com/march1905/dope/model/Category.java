package com.march1905.dope.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */
@DatabaseTable(tableName = "Category")
public class Category implements Serializable{

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(dataType = DataType.STRING)
    private String title;

    @DatabaseField(dataType = DataType.STRING)
    private String subTitle;



    public Category() {
        //needed by ORMlite
    }


    public Category(int id, String title, String subTitle) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public class ColumnName {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String SUB_TITLE = "subTitle";
    }
}
