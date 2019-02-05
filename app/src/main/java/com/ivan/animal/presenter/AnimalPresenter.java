package com.ivan.animal.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v17.leanback.widget.Presenter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ivan.animal.R;
import com.ivan.animal.fragment.PlantListFragment;
import com.ivan.animal.listener.PreImageLoadingListener;
import com.ivan.animal.manager.ImageLoaderManager;
import com.ivan.animal.object.AnimalItem;
import com.ivan.animal.utils.FragmentUtils;

/**
 * Created by Ivan on 2019/2/3.
 */

public class AnimalPresenter extends Presenter {

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cardview_animal_item, parent, false);
        return new AnimalPresenter.AnimalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        AnimalItem animalItem = (AnimalItem) item;
        AnimalViewHolder mAnimalViewHolder = (AnimalPresenter.AnimalViewHolder) viewHolder;
        mAnimalViewHolder.cardView.setOnClickListener(functionOnClickListener);
        mAnimalViewHolder.cardView.setTag(animalItem);
        mAnimalViewHolder.name.setText(animalItem.getName());
        mAnimalViewHolder.category.setText(animalItem.getCategory());
        if (TextUtils.isEmpty(animalItem.getMemo())) {
            mAnimalViewHolder.memo.setText(context.getString(R.string.memo_default));
        } else {
            mAnimalViewHolder.memo.setText(animalItem.getMemo());
        }
        mAnimalViewHolder.introduction.setText(animalItem.getIntroduction());
        ImageLoaderManager.getInstance(context).loadImageUrl(animalItem.getImageUrl(),
                mAnimalViewHolder.image,
                ImageLoaderManager.InSamplePowerOfTwoDisplayImageOptions,
                new PreImageLoadingListener(ImageView.ScaleType.CENTER_INSIDE,ImageView.ScaleType.CENTER_INSIDE));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }

    private View.OnClickListener functionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AnimalItem item = (AnimalItem) v.getTag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PlantListFragment.BUNDLE_ANIMAL_ITEM, item);
            FragmentUtils.replaceFragment(((AppCompatActivity) context).getSupportFragmentManager(),
                    PlantListFragment.class,
                    R.id.container,
                    bundle, true, false);
        }
    };

    private class AnimalViewHolder extends ViewHolder {

        CardView cardView;
        TextView name;
        TextView category;
        TextView introduction;
        TextView memo;
        ImageView image;

        AnimalViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card_view);
            name = view.findViewById(R.id.name);
            category = view.findViewById(R.id.category);
            introduction = view.findViewById(R.id.introduction);
            memo = view.findViewById(R.id.memo);
            image = view.findViewById(R.id.image);
        }
    }
}

