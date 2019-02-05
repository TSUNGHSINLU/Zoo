package com.ivan.animal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ivan.animal.R;
import com.ivan.animal.api.AnimalDataApi;
import com.ivan.animal.listener.ApiAnimalCallBack;
import com.ivan.animal.listener.MainUiControlCallBack;
import com.ivan.animal.object.AnimalItem;
import com.ivan.animal.presenter.CustomPresenterSelector;

import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public class AnimalFragment extends Fragment implements ApiAnimalCallBack {

    private final String TAG = AnimalFragment.class.getSimpleName();

    private AppCompatActivity activity;
    private ArrayObjectAdapter mArrayObjectAdapter;
    private AnimalDataApi animalDataApi;
    private ArrayList<AnimalItem> mAnimalItemList = new ArrayList<>(1);
    private MainUiControlCallBack mainUiControlCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
        mainUiControlCallback = (MainUiControlCallBack)activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
        mainUiControlCallback.setToolbarTitle(activity.getString(R.string.app_name));
        mainUiControlCallback.setDrawerMode(true);
        setList(view);
        callAnimalDataApi();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }

    private void setList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mArrayObjectAdapter = new ArrayObjectAdapter(new CustomPresenterSelector());
        recyclerView.setAdapter(new ItemBridgeAdapter(mArrayObjectAdapter));
    }

    private void callAnimalDataApi() {
        mainUiControlCallback.setProgressBar(true);
        animalDataApi = new AnimalDataApi(activity);
        animalDataApi.AnimationDataApi(this, TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(animalDataApi != null){
            animalDataApi.cancelPendingRequests(TAG);
        }
        if(mAnimalItemList != null){
            mAnimalItemList.clear();
        }
        mainUiControlCallback.setProgressBar(false);
    }

    @Override
    public void success(ArrayList<AnimalItem> animalItemList) {
        if (!this.isAdded()) {
            return;
        }
        if (animalItemList != null && mArrayObjectAdapter != null && mAnimalItemList != null) {
            mAnimalItemList.clear();
            mAnimalItemList.addAll(animalItemList);
            for (int index = 0; index < mAnimalItemList.size(); index++) {
                mArrayObjectAdapter.add(mAnimalItemList.get(index));
            }
        }
        mainUiControlCallback.setProgressBar(false);
    }

    @Override
    public void fail(String error) {
        if (!this.isAdded()) {
            return;
        }
        Log.d(TAG, "error = " + error);
        if (mArrayObjectAdapter != null) {
            mArrayObjectAdapter.add(activity.getString(R.string.default_message));
        }
        mainUiControlCallback.setProgressBar(false);
    }
}
