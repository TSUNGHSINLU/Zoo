package com.ivan.animal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ivan.animal.R;
import com.ivan.animal.adapter.ParallaxRecyclerAdapter;
import com.ivan.animal.api.PlantDataApi;
import com.ivan.animal.listener.ApiPlantCallBack;
import com.ivan.animal.listener.PreImageLoadingListener;
import com.ivan.animal.listener.MainUiControlCallBack;
import com.ivan.animal.manager.ImageLoaderManager;
import com.ivan.animal.object.AnimalItem;
import com.ivan.animal.object.PlantItem;
import com.ivan.animal.utils.FragmentUtils;
import com.ivan.animal.utils.UrlUtils;
import java.util.ArrayList;

/**
 * Created by Ivan on 2019/2/3.
 */

public class PlantListFragment extends Fragment implements ApiPlantCallBack {

    private final String TAG = PlantListFragment.class.getSimpleName();

    public static final String BUNDLE_ANIMAL_ITEM = "bundle_animal_item";

    private AppCompatActivity activity;
    private AnimalItem animalItem;
    private PlantDataApi plantDataApi;
    private ArrayList<PlantItem> mPlantItemList = new ArrayList<>(1);
    private MainUiControlCallBack mainUiControlCallback;
    private ParallaxRecyclerAdapter<PlantItem> mRecyclerAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
        mainUiControlCallback = (MainUiControlCallBack)activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            animalItem = bundle.getParcelable(BUNDLE_ANIMAL_ITEM);
        }
        mainUiControlCallback.setDrawerMode(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return inflater.inflate(R.layout.fragment_plant_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
        initRecyclerView(view);
        callPlantDataApi();
    }

    private void initRecyclerView(View view) {

        mRecyclerAdapter = new ParallaxRecyclerAdapter<PlantItem>(mPlantItemList) {
            @Override
            public void onBindViewHolderImpl(final RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<PlantItem> adapter, int i) {
                if (viewHolder instanceof ViewHolder) {
                    if (adapter.getData() != null && adapter.getData().size() > 0) {
                        PlantItem mPlantItem = adapter.getData().get(i - 1);
                        ((ViewHolder) viewHolder).mainContent.setOnClickListener(mOnClickListener);
                        ((ViewHolder) viewHolder).mainContent.setTag(mPlantItem);
                        ImageLoaderManager.getInstance(activity).loadImageUrl(mPlantItem.getImageUrl(),
                                ((ViewHolder) viewHolder).image,
                                ImageLoaderManager.InSamplePowerOfTwoDisplayImageOptions,
                                new PreImageLoadingListener(ImageView.ScaleType.CENTER_CROP,ImageView.ScaleType.CENTER));
                        ((ViewHolder) viewHolder).name.setText(mPlantItem.getNameChinese());
                        ((ViewHolder) viewHolder).alsoKnown.setText(mPlantItem.getAlsoKnown());
                    }
                } else {
                    if(adapter.getData() == null || adapter.getData().size() == 0){
                        ((FirstViewHolder) viewHolder).headerFunction.setVisibility(View.GONE);
                    } else {
                        ((FirstViewHolder) viewHolder).headerFunction.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<PlantItem> adapter, int i) {
                if (VIEW_TYPES.FIRST_VIEW == i) {
                    return new FirstViewHolder(activity.getLayoutInflater().inflate(R.layout.recycler_plant_first_parallax_item, viewGroup, false));
                } else {
                    return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.recycler_plant_parallax_item, viewGroup, false));
                }
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<PlantItem> adapter) {
                return mPlantItemList.size() + 1;
            }
        };

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        View mHeaderView = activity.getLayoutInflater().inflate(R.layout.listview_plant_content_header, mRecyclerView, false);
        setContentDisplay(mHeaderView);
        mRecyclerAdapter.setParallaxHeader(mHeaderView, mRecyclerView);
        mRecyclerAdapter.setData(mPlantItemList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PlantItem item = (PlantItem)v.getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PlantContentFragment.BUNDLE_PLANT_ITEM, item);
            FragmentUtils.replaceFragment(activity.getSupportFragmentManager(),
                    PlantContentFragment.class,
                    R.id.container,
                    bundle, true, false);
        }
    };

    private void callPlantDataApi() {
        mainUiControlCallback.setProgressBar(true);
        plantDataApi = new PlantDataApi(activity);
        plantDataApi.plantDataApi(this, TAG, animalItem.getName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }

    private void setContentDisplay(View view) {
        if (animalItem != null) {
            ImageView image = view.findViewById(R.id.image);
            ImageLoaderManager.getInstance(activity).loadImageUrl(animalItem.getImageUrl(),
                    image,
                    ImageLoaderManager.ExactlyStretchedDisplayImageOptions,
                    new PreImageLoadingListener(ImageView.ScaleType.CENTER_CROP,ImageView.ScaleType.CENTER));
            mainUiControlCallback.setToolbarTitle(animalItem.getName());
            TextView introduction = view.findViewById(R.id.introduction);
            introduction.setText(animalItem.getIntroduction());
            TextView category = view.findViewById(R.id.category);
            category.setText(animalItem.getCategory());
            TextView memo = view.findViewById(R.id.memo);
            if (TextUtils.isEmpty(animalItem.getMemo())) {
                memo.setText(activity.getString(R.string.memo_default));
            } else {
                memo.setText(animalItem.getMemo());
            }
            TextView url = view.findViewById(R.id.url);
            url.setTag(animalItem.getUrl());
            url.setOnClickListener(urlOnclickListener);
        }
    }

    private View.OnClickListener urlOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = (String)v.getTag();
            UrlUtils.openBrowserURL(activity, url);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(plantDataApi != null){
            plantDataApi.cancelPendingRequests(TAG);
        }
        if(mPlantItemList != null){
            mPlantItemList.clear();
        }
        mainUiControlCallback.setProgressBar(false);
    }

    @Override
    public void success(ArrayList<PlantItem> plantItemList) {
        if (!this.isAdded()) {
            return;
        }
        if (plantItemList != null && mPlantItemList != null && mRecyclerAdapter != null) {
            mPlantItemList.clear();
            mPlantItemList.addAll(plantItemList);
            mRecyclerAdapter.notifyDataSetChanged();
        }
        mainUiControlCallback.setProgressBar(false);
    }

    @Override
    public void fail(String error) {
        if (!this.isAdded()) {
            return;
        }
        Log.d(TAG, "error = " + error);
        mainUiControlCallback.setProgressBar(false);
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder {

        LinearLayout headerFunction;
        TextView contentDescriptionView;

        public FirstViewHolder(View itemView) {
            super(itemView);
            headerFunction = itemView.findViewById(R.id.headerFunction);
            contentDescriptionView = itemView.findViewById(R.id.contentDescriptionView);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mainContent;
        ImageView image;
        TextView name;
        TextView alsoKnown;

        public ViewHolder(View itemView) {
            super(itemView);
            mainContent = itemView.findViewById(R.id.mainContent);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            alsoKnown = itemView.findViewById(R.id.alsoKnown);
        }
    }

}


