package com.ivan.animal.application;

import android.app.Application;
import android.util.Log;
import com.ivan.animal.manager.ImageLoaderManager;
import com.ivan.animal.manager.VolleyManager;

/**
 * Main Application.
 * Created by Ivan on 2019/02/03.
 *
 *  @author IvanLu
 */
public class BaseApplication extends Application {

    private final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderManager.getInstance(getApplicationContext());
        VolleyManager.getInstance(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory");
        ImageLoaderManager.getInstance(getApplicationContext()).clearMemoryCache();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
