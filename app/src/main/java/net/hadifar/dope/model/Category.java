package net.hadifar.dope.model;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */
@DatabaseTable(tableName = "Category")
public class Category extends BaseEntity {

    public Category() {
        //needed by ORMlite
         }

    public Category(int id, String title, String subtitle) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }




    public class ColumnName {
        public static final String ID = "id";
//        public static final String TITLE = "title";
//        public static final String SUB_TITLE = "subTitle";
    }
}
