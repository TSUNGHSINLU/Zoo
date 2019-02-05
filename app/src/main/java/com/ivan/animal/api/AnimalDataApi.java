package com.ivan.animal.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ivan.animal.listener.ApiAnimalCallBack;
import com.ivan.animal.manager.VolleyManager;
import com.ivan.animal.object.AnimalItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public class AnimalDataApi extends BaseDataApi{

    private final String TAG = AnimalDataApi.class.getSimpleName();

    private Context context;

    public AnimalDataApi(Context context) {
        this.context = context.getApplicationContext();
    }

    public void AnimationDataApi(final ApiAnimalCallBack callback, final String tag) {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                createGetAnimalDataApiUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            callback.success(parserData(response));
                        } else {
                            callback.fail(NO_DATA);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = API_ERROR;
                        if (error != null) {
                            errorMessage = error.toString();
                        }
                        callback.fail(errorMessage);
                    }
                }
        );
        VolleyManager.getInstance(context).addToRequestQueue(mJsonObjectRequest, tag);
    }

    public void cancelPendingRequests(String tag) {
        VolleyManager.getInstance(context).cancelPendingRequests(tag);
    }

    private ArrayList<AnimalItem> parserData(JSONObject response) {
        ArrayList<AnimalItem> animalItemList = new ArrayList<>(1);
        JSONObject result = response.optJSONObject("result");
        if (result != null) {
            JSONArray results = result.optJSONArray("results");
            if (results != null) {
                int length = results.length();
                for (int index = 0; index < length; index++) {
                    JSONObject animalItem = results.optJSONObject(index);
                    if (animalItem != null) {
                        AnimalItem item = new AnimalItem();
                        item.setId(animalItem.optInt("_id"));
                        item.setName(animalItem.optString("E_Name"));
                        item.setCategory(animalItem.optString("E_Category"));
                        item.setImageUrl(animalItem.optString("E_Pic_URL"));
                        item.setUrl(animalItem.optString("E_URL"));
                        item.setIntroduction(animalItem.optString("E_Info"));
                        item.setMemo(animalItem.optString("E_Memo"));
                        animalItemList.add(item);
                    }
                }
            }
        }
        return animalItemList;
    }

    private String createGetAnimalDataApiUrl() {
        Uri.Builder builder = Uri.parse(DATA_API_URL).buildUpon();
        builder.appendQueryParameter(SCOPE, "resourceAquire")
                .appendQueryParameter(RID, "5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a");
        String url = builder.build().toString();
        Log.d(TAG, "url = " + url);
        return url;
    }
}
