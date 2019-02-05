package com.ivan.animal.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ivan.animal.listener.ApiPlantCallBack;
import com.ivan.animal.manager.VolleyManager;
import com.ivan.animal.object.PlantItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public class PlantDataApi extends BaseDataApi {

    private final String TAG = PlantDataApi.class.getSimpleName();

    private Context context;

    public PlantDataApi(Context context) {
        this.context = context.getApplicationContext();
    }

    public void plantDataApi(final ApiPlantCallBack callback, final String tag, final String filter) {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                createGetPlantDataApiUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            callback.success(parserData(response,filter));
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

    private ArrayList<PlantItem> parserData(JSONObject response, String filter) {
        ArrayList<PlantItem> plantItemList = new ArrayList<>(1);
        JSONObject result = response.optJSONObject("result");
        if (result != null) {
            JSONArray results = result.optJSONArray("results");
            if (results != null) {
                int length = results.length();
                for (int index = 0; index < length; index++) {
                    JSONObject plantItem = results.optJSONObject(index);
                    if (plantItem != null) {
                        PlantItem item = new PlantItem();
                        item.setId(plantItem.optInt("_id"));
                        item.setNameChinese(plantItem.optString("F_Name_Ch"));
                        item.setNameEnglish(plantItem.optString("F_Name_En"));
                        item.setNameLatin(plantItem.optString("F_Name_Latin"));
                        item.setGenus(plantItem.optString("F_Genus"));
                        item.setFamily(plantItem.optString("F_Family"));
                        item.setAlsoKnown(plantItem.optString("F_AlsoKnown"));
                        item.setImageUrl(plantItem.optString("F_Pic01_URL"));
                        item.setLocation(plantItem.optString("F_Location"));
                        item.setBrief(plantItem.optString("F_Brief"));
                        item.setFeature(plantItem.optString("F_Feature"));
                        item.setFunction(plantItem.optString("F_Function&Application"));
                        item.setUpdateDate(plantItem.optString("F_Update"));
                        if(item.getLocation().contains(filter)){
                            plantItemList.add(item);
                        }
                    }
                }
            }
        }
        return plantItemList;
    }

    private String createGetPlantDataApiUrl() {
        Uri.Builder builder = Uri.parse(DATA_API_URL).buildUpon();
        builder.appendQueryParameter(SCOPE, "resourceAquire")
                .appendQueryParameter(RID, "f18de02f-b6c9-47c0-8cda-50efad621c14");
        String url = builder.build().toString();
        Log.d(TAG, "url = " + url);
        return url;
    }
}
