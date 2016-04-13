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
public class Category extends BaseEntity implements Serializable {

    public Category() {
        //needed by ORMlite
    }

    public Category(int id, String title, String subTitle) {
        this.id = id;
        this.title = title;
        this.subtitle = subTitle;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subtitle = subTitle;
    }

    public class ColumnName {
        public static final String ID = "id";
//        public static final String TITLE = "title";
//        public static final String SUB_TITLE = "subTitle";
    }
}
