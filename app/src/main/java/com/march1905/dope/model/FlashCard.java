package com.march1905.dope.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */
@DatabaseTable(tableName = "FlashCard")
public class FlashCard extends BaseEntity implements Serializable {

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

    @DatabaseField(dataType = DataType.INTEGER)
    protected int deckId;


    public FlashCard() {
        //needed by ORMlite
    }

    public FlashCard(FlashCardFavoritedItems favItem) {
        this.id = favItem.getId();
        this.word = favItem.getWord();
        this.persian = favItem.getPersian();
        this.pronunciation = favItem.getPronunciation();
        this.synonym = favItem.getSynonym();
        this.example1 = favItem.getExample1();
        this.example2 = favItem.getExample2();
        this.example3 = favItem.getExample3();
    }

    public FlashCard(int id, String word, String persian, String synonym, String pronunciation, String example1, String example2, String example3, int deckId) {
        this.id = id;
        this.word = word;
        this.title = word;
        this.deckId = deckId;
        this.pronunciation = pronunciation;
        this.persian = persian;
        this.synonym = synonym;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPersian() {
        return persian;
    }

    public void setPersian(String persian) {
        this.persian = persian;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }

    public String getExample3() {
        return example3;
    }

    public void setExample3(String example3) {
        this.example3 = example3;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return word;
    }

    @Override
    public String getSubtitle() {
        return null;
    }

    public class ColumnName {
        public static final String ID = "id";
        public static final String DECK_ID = "deckId";
        public static final String TEXT = "";
    }


}
