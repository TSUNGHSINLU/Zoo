package com.ivan.animal.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan on 2019/2/3.
 */

public class PlantItem implements Parcelable {

    private int Id;
    private String nameChinese;
    private String nameEnglish;
    private String nameLatin;
    private String genus;
    private String family;
    private String alsoKnown;
    private String imageUrl;
    private String location;
    private String brief;
    private String feature;
    private String function;
    private String updateDate;

    public PlantItem(){

    }

    protected PlantItem(Parcel in) {
        Id = in.readInt();
        nameChinese = in.readString();
        nameEnglish = in.readString();
        nameLatin = in.readString();
        genus = in.readString();
        family = in.readString();
        alsoKnown = in.readString();
        imageUrl = in.readString();
        location = in.readString();
        brief = in.readString();
        feature = in.readString();
        function = in.readString();
        updateDate = in.readString();
    }

    public static final Creator<PlantItem> CREATOR = new Creator<PlantItem>() {
        @Override
        public PlantItem createFromParcel(Parcel in) {
            return new PlantItem(in);
        }

        @Override
        public PlantItem[] newArray(int size) {
            return new PlantItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(nameChinese);
        dest.writeString(nameEnglish);
        dest.writeString(nameLatin);
        dest.writeString(genus);
        dest.writeString(family);
        dest.writeString(alsoKnown);
        dest.writeString(imageUrl);
        dest.writeString(family);
        dest.writeString(location);
        dest.writeString(brief);
        dest.writeString(feature);
        dest.writeString(function);
        dest.writeString(updateDate);
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNameChinese(String nameChinese) {
        this.nameChinese = nameChinese;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setAlsoKnown(String alsoKnown) {
        this.alsoKnown = alsoKnown;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getId() {

        return Id;
    }

    public String getNameChinese() {
        return nameChinese;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public String getGenus() {
        return genus;
    }

    public String getFamily() {
        return family;
    }

    public String getAlsoKnown() {
        return alsoKnown;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getBrief() {
        return brief;
    }

    public String getFeature() {
        return feature;
    }

    public String getFunction() {
        return function;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
