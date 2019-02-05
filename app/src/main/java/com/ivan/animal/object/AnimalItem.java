package com.ivan.animal.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan on 2019/2/3.
 */

public class AnimalItem implements Parcelable {

    private int Id;
    private String name;
    private String category;
    private String imageUrl;
    private String url;
    private String introduction;
    private String memo;

    public AnimalItem(){

    }

    protected AnimalItem(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        category = in.readString();
        imageUrl = in.readString();
        url = in.readString();
        introduction = in.readString();
        memo = in.readString();
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {

        return Id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getMemo() {
        return memo;
    }

    public static final Creator<AnimalItem> CREATOR = new Creator<AnimalItem>() {
        @Override
        public AnimalItem createFromParcel(Parcel in) {
            return new AnimalItem(in);
        }

        @Override
        public AnimalItem[] newArray(int size) {
            return new AnimalItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(imageUrl);
        dest.writeString(url);
        dest.writeString(introduction);
        dest.writeString(memo);
    }
}
