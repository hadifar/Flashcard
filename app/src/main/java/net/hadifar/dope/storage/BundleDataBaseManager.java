package net.hadifar.dope.storage;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import net.hadifar.dope.AppConfig;
import net.hadifar.dope.model.Category;
import net.hadifar.dope.model.Deck;
import net.hadifar.dope.model.FlashCard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class BundleDataBaseManager extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "dope";
    private static final int DATABASE_VERSION = 2;

    private RuntimeExceptionDao<Category, Integer> mainCategoriesDao = null;
    private RuntimeExceptionDao<Deck, Integer> deckItemsDao = null;
    private RuntimeExceptionDao<FlashCard, Integer> flashcardDao = null;

    private static BundleDataBaseManager instance = null;

    private BundleDataBaseManager() {
        super(AppConfig.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized BundleDataBaseManager getInstance() {
        if (instance == null)
            instance = new BundleDataBaseManager();
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Deck.class);
            TableUtils.createTable(connectionSource, FlashCard.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            switch (newVersion) {
                case 2:
                    TableUtils.dropTable(connectionSource, Category.class, true);
                    TableUtils.dropTable(connectionSource, Deck.class, true);
                    TableUtils.dropTable(connectionSource, FlashCard.class, true);
                    onCreate(sqLiteDatabase, connectionSource);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        SQLiteDatabase db = getWritableDatabase();
        db.close();
    }


    //Category region
    public RuntimeExceptionDao<Category, Integer> getMainCategoriesDao() {
        if (mainCategoriesDao == null) {
            mainCategoriesDao = getRuntimeExceptionDao(Category.class);
        }
        return mainCategoriesDao;
    }

    public void addToCategory(Category category) {
        getMainCategoriesDao().createIfNotExists(category);
    }

    public int getLastCategoryId() {
        try {
            return getMainCategoriesDao().query(getMainCategoriesDao().queryBuilder().orderBy(Category.ColumnName.ID, false).limit(1).prepare()).get(0).getId();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Category> getAllCategories() {
        return getMainCategoriesDao().queryForAll();
    }


    //TODO:Change this in next version , it should be change with foreign key
    public void removeFromCategory(Category category) {
        List<Deck> decks = getDecksForCategoryId(category.getId());
        for (Deck deck : decks) {
            List<FlashCard> flashCardsList = getFlashCardsForDeckId(deck.getId());
            for (FlashCard flashCard : flashCardsList) {
                getFlashCardDao().delete(flashCard);
            }
            getDeckItemDao().delete(deck);
        }
        getMainCategoriesDao().delete(category);
    }

    public void editFromCategory(Category category) {
        getMainCategoriesDao().createOrUpdate(category);
    }

    //deck region
    public RuntimeExceptionDao<Deck, Integer> getDeckItemDao() {
        if (deckItemsDao == null) {
            deckItemsDao = getRuntimeExceptionDao(Deck.class);
        }
        return deckItemsDao;
    }

    public int getLastDeckId() {
        try {
            return getDeckItemDao().query(getDeckItemDao().queryBuilder().orderBy(Deck.ColumnName.ID, false).limit(1).prepare()).get(0).getId();
        } catch (Exception e) {
            return 0;
        }
    }


    public void addToDecks(Deck deck) {
        getDeckItemDao().createIfNotExists(deck);
    }

    public void editFromDeck(Deck deck) {
        getDeckItemDao().createOrUpdate(deck);
    }


    public void removeFromDeck(Deck deck) {
        List<FlashCard> flashCardsList = getFlashCardsForDeckId(deck.getId());
        for (FlashCard flashCard : flashCardsList) {
            getFlashCardDao().delete(flashCard);
        }
        getDeckItemDao().delete(deck);
    }

    public List<Deck> getDecksForCategoryId(int categoryId) {
        QueryBuilder<Deck, Integer> queryBuilder = getDeckItemDao().queryBuilder();
        Where<Deck, Integer> where = queryBuilder.where();

        try {
            where.eq(Deck.ColumnName.CATEGORY_ID, categoryId);
            queryBuilder.orderBy(Deck.ColumnName.CATEGORY_ID, true);
            return getDeckItemDao().query(queryBuilder.prepare());

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Deck>();
        }
    }


    //flash card region
    public RuntimeExceptionDao<FlashCard, Integer> getFlashCardDao() {
        if (flashcardDao == null) {
            flashcardDao = getRuntimeExceptionDao(FlashCard.class);
        }
        return flashcardDao;
    }

    public void addToFlashCard(FlashCard flashCard) {
        getFlashCardDao().createIfNotExists(flashCard);
    }

    public int getLastFlashCardId() {
        try {
            return getFlashCardDao().query(getFlashCardDao().queryBuilder().orderBy(FlashCard.ColumnName.ID, false).limit(1).prepare()).get(0).getId();
        } catch (Exception e) {
            return 0;
        }
    }

    public void editFromFlashCard(FlashCard flashCard) {
        getFlashCardDao().createOrUpdate(flashCard);
    }

    public void removeFromFlashCard(FlashCard flashCard) {
        long pre = getFlashCardDao().countOf();
        getFlashCardDao().delete(flashCard);
        long s = getFlashCardDao().countOf();

    }

    public List<FlashCard> getFlashCardsForDeckId(int deckId) {
        QueryBuilder<FlashCard, Integer> queryBuilder = getFlashCardDao().queryBuilder();
        Where<FlashCard, Integer> where = queryBuilder.where();

        try {

            where.eq(FlashCard.ColumnName.DECK_ID, deckId);
            queryBuilder.orderBy(Deck.ColumnName.ID, true);
            return getFlashCardDao().query(queryBuilder.prepare());

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<FlashCard>();
        }

    }

    public void clearTables(Class... dataClasses) {
        SQLiteDatabase db = getWritableDatabase();
        this.connectionSource = new AndroidConnectionSource(db);
        try {
            for (Class cls : dataClasses)
                TableUtils.clearTable(connectionSource, cls);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAllTables() {
        clearTables(Category.class, FlashCard.class, Deck.class);
    }
}
