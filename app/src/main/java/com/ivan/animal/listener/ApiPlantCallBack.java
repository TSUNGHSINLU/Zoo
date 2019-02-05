package com.ivan.animal.listener;

import com.ivan.animal.object.PlantItem;
import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public interface ApiPlantCallBack {
    void success(ArrayList<PlantItem> itemList);
    void fail(String error);
}
