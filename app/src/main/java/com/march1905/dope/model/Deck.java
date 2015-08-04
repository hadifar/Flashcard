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
@DatabaseTable(tableName = "Deck")
public class Deck implements Serializable {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(dataType = DataType.STRING)
    private String title;

    @DatabaseField(dataType = DataType.INTEGER)
    private int categoryId;
//
//    @DatabaseField(dataType = DataType.STRING)
//    private String subTitle;


    public Deck() {
        //needed by ORMlite
    }

    public Deck(int id, String title, int categoryId) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public class ColumnName {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CATEGORY_ID = "categoryId";
        //public static final String SUB_TITLE = "subTitle";
    }
}
