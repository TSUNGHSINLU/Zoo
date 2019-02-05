package com.ivan.animal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ivan.animal.R;
import com.ivan.animal.listener.PreImageLoadingListener;
import com.ivan.animal.listener.MainUiControlCallBack;
import com.ivan.animal.manager.ImageLoaderManager;
import com.ivan.animal.object.PlantItem;

/**
 * Created by Ivan on 2019/2/3.
 */

public class PlantContentFragment extends Fragment {

    private final String TAG = PlantContentFragment.class.getSimpleName();

    public static final String BUNDLE_PLANT_ITEM = "bundle_plant_item";

    private AppCompatActivity activity;
    private MainUiControlCallBack mainUiControlCallback;
    private PlantItem plantItem;

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
            plantItem = bundle.getParcelable(BUNDLE_PLANT_ITEM);
        }
        mainUiControlCallback.setDrawerMode(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return inflater.inflate(R.layout.fragment_plant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
        setContentDisplay(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }

    private void setContentDisplay(View view) {
        if (plantItem != null) {
            ImageView image = view.findViewById(R.id.image);
            ImageLoaderManager.getInstance(activity).loadImageUrl(plantItem.getImageUrl(),
                    image,
                    ImageLoaderManager.ExactlyStretchedDisplayImageOptions,
                    new PreImageLoadingListener(ImageView.ScaleType.CENTER_CROP,ImageView.ScaleType.CENTER));
            mainUiControlCallback.setToolbarTitle(plantItem.getNameChinese());
            TextView nameChinese = view.findViewById(R.id.nameChinese);
            nameChinese.setText(plantItem.getNameChinese());
            TextView nameEnglish = view.findViewById(R.id.nameEnglish);
            nameEnglish.setText(plantItem.getNameEnglish());
            TextView nameLatin = view.findViewById(R.id.nameLatin);
            nameLatin.setText(plantItem.getNameLatin());
            TextView alsoKnown = view.findViewById(R.id.alsoKnown);
            alsoKnown.setText(plantItem.getAlsoKnown());
            TextView genus = view.findViewById(R.id.genus);
            genus.setText(plantItem.getGenus());
            TextView family = view.findViewById(R.id.family);
            family.setText(plantItem.getFamily());
            TextView brief = view.findViewById(R.id.brief);
            brief.setText(plantItem.getBrief());
            TextView feature = view.findViewById(R.id.feature);
            feature.setText(plantItem.getFeature());
            TextView function = view.findViewById(R.id.function);
            function.setText(plantItem.getFunction());
            TextView updateDate = view.findViewById(R.id.updateDate);
            updateDate.setText(plantItem.getUpdateDate());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

