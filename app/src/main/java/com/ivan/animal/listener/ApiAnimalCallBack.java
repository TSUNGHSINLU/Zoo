package com.ivan.animal.listener;

import com.ivan.animal.object.AnimalItem;

import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public interface ApiAnimalCallBack {
    void success(ArrayList<AnimalItem> itemList);
    void fail(String error);
}
