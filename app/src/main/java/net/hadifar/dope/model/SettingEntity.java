package net.hadifar.dope.model;

/**
 * Created by Amir on 5/3/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class SettingEntity {

    int settingType;
    Integer title;
    Object subtitle;

    public SettingEntity(int settingType, Integer titleResId, Object subTitleResId) {
        this.settingType = settingType;
        this.title = titleResId;
        this.subtitle = subTitleResId;
    }

    public int getSettingType() {
        return settingType;
    }

    public Integer getTitle() {
        return title;
    }

    public Object getSubtitle() {
        return subtitle;
    }


}
