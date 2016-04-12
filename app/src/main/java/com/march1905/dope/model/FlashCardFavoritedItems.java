package com.march1905.dope.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.march1905.dope.core.BundleDataBaseManager;

import java.io.Serializable;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

@DatabaseTable(tableName = "FlashCardFavoriteItem")
public class FlashCardFavoritedItems implements Serializable {

    @DatabaseField(dataType = DataType.INTEGER, id = true)
    protected int id;

    @DatabaseField(dataType = DataType.INTEGER)
    protected int deckId;

    @DatabaseField(dataType = DataType.STRING)
    protected String word;

    @DatabaseField(dataType = DataType.STRING)
    protected String persian;

    @DatabaseField(dataType = DataType.STRING)
    protected String synonym;

    @DatabaseField(dataType = DataType.STRING)
    protected String pronunciation;

    @DatabaseField(dataType = DataType.STRING)
    protected String example1;

    @DatabaseField(dataType = DataType.STRING)
    protected String example2;

    @DatabaseField(dataType = DataType.STRING)
    protected String example3;

    public FlashCardFavoritedItems(){
        //needed for Ormlite
    }
    public FlashCardFavoritedItems(FlashCard flashCard) {
        this.id = flashCard.id;
        this.deckId = flashCard.deckId;
        this.word = flashCard.word;
        this.persian = flashCard.persian;
        this.synonym = flashCard.synonym;
        this.pronunciation = flashCard.pronunciation;
        this.example1 = flashCard.example1;
        this.example2 = flashCard.example2;
        this.example3 = flashCard.example3;
    }

    public String getCategoryTitle(int deckId) {
        BundleDataBaseManager db = BundleDataBaseManager.getInstance();
        int catId = db.getDeckItemDao().queryForId(deckId).getCategoryId();
        return db.getMainCategoriesDao().queryForId(catId).getTitle();
    }

    public String getDeckTitle(int deckId){
        return BundleDataBaseManager.getInstance().getDeckItemDao().queryForId(deckId).getTitle();
    }

    public int getId() {
        return id;
    }

    public int getDeckId() {
        return deckId;
    }

    public String getWord() {
        return word;
    }

    public String getPersian() {
        return persian;
    }

    public String getSynonym() {
        return synonym;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getExample1() {
        return example1;
    }

    public String getExample2() {
        return example2;
    }

    public String getExample3() {
        return example3;
    }
}
