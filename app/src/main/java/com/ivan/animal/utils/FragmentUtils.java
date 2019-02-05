package com.ivan.animal.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Fragment Utils.
 * Created by Ivan on 2019/02/03.
 *
 * @author IvanLu
 */
public class FragmentUtils {

    private static final String TAG = FragmentUtils.class.getSimpleName();

    public static void replaceFragment(FragmentManager manager, Class<? extends Fragment> clazz, int widgetId,
                                       Bundle bundle, boolean addFragmentToStack, boolean commitAllowingStateLoss) {

        if (clazz != null && manager != null) {
            Fragment targetFragment = manager.findFragmentByTag(clazz.getName());
            if (targetFragment == null) {
                try {
                    targetFragment = clazz.newInstance();
                    if (bundle != null) {
                        targetFragment.setArguments(bundle);
                    }
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(widgetId, targetFragment, clazz.getName());
                      if (addFragmentToStack) {
                        transaction.addToBackStack(clazz.getName());
                    }
                    if (commitAllowingStateLoss) {
                        transaction.commitAllowingStateLoss();
                    } else {
                        transaction.commit();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "error" + e);
                }
            } else {
                manager.popBackStack(targetFragment.getTag(), 0);
            }
        }
    }
}
