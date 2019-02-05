package com.ivan.animal.presenter;

import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import com.ivan.animal.object.AnimalItem;

/**
 * Presenter selector
 * Created by Ivan on 2017/1/14.
 *
 * @author IvanLu
 */
public class CustomPresenterSelector extends PresenterSelector {

    private final static int DEFAULT = 0;
    private final static int ANIMAL_DATA = 1;
    private Presenter[] mPresenterData;

    public CustomPresenterSelector() {
        mPresenterData = new Presenter[3];
        mPresenterData[DEFAULT] = new DefaultPresenter();
        mPresenterData[ANIMAL_DATA] = new AnimalPresenter();
    }

    @Override
    public Presenter getPresenter(Object item) {
        if (item instanceof AnimalItem) {
            return mPresenterData[ANIMAL_DATA];
        } else {
            return mPresenterData[DEFAULT];
        }
    }

    @Override
    public Presenter[] getPresenters() {
        return mPresenterData;
    }
}
