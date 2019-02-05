package com.ivan.animal.manager;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Volley network processor
 * Created by Ivan on 2017/1/14.
 *
 * @author IvanLu
 */
public class VolleyManager {

    private final String TAG = VolleyManager.class.getSimpleName();

    private static VolleyManager INSTANCE;
    private RequestQueue mRequestQueue;

    private VolleyManager(Context context) {
        initRequestQueue(context);
    }

    private void initRequestQueue(Context context) {
        getRequestQueue(context);
    }

    public static VolleyManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VolleyManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VolleyManager(context);
                }
            }
        }
        return INSTANCE;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        mRequestQueue.add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
