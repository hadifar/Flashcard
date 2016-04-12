package com.march1905.dope.core;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.march1905.dope.model.FlashCard;
import com.march1905.dope.model.FlashCardFavoritedItems;

import java.sql.SQLException;
import java.util.List;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class AppDataBaseManager extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "appData";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<FlashCardFavoritedItems, Integer> flashCardFavoriteItemsDao = null;


    private static AppDataBaseManager instance = new AppDataBaseManager();

    private AppDataBaseManager() {
        super(AppConfig.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AppDataBaseManager getInstance(){
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FlashCardFavoritedItems.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            switch (newVersion) {
                case 1:
                    TableUtils.createTable(connectionSource, FlashCardFavoritedItems.class);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() {
        super.close();
        flashCardFavoriteItemsDao = null;
    }


    public RuntimeExceptionDao<FlashCardFavoritedItems, Integer> getFlashCardFavoritedItemsDao() {
        if (flashCardFavoriteItemsDao == null) {
            flashCardFavoriteItemsDao = getRuntimeExceptionDao(FlashCardFavoritedItems.class);
        }
        return flashCardFavoriteItemsDao;
    }

    public boolean isFavoritedFlashCard(int itemId) {
        return getFlashCardFavoritedItemsDao().idExists(itemId);
    }

    public void addFlashCardToFavoritedItems(FlashCard item) {
        FlashCardFavoritedItems favoritedItem = new FlashCardFavoritedItems(item);
        getFlashCardFavoritedItemsDao().createIfNotExists(favoritedItem);
    }

    public void addFlashCardToFavoritedItems(FlashCardFavoritedItems item) {
        getFlashCardFavoritedItemsDao().createIfNotExists(item);
    }

    public void removeFlashCardItemFromFavorites(int itemId) {
        getFlashCardFavoritedItemsDao().deleteById(itemId);
    }

    public List<FlashCardFavoritedItems> getFavoritedFlashCardItems() {
        return getFlashCardFavoritedItemsDao().queryForAll();
    }


}
