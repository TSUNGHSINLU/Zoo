package com.ivan.animal.listener;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Ivan on 2019/2/3.
 */

public class PreImageLoadingListener implements ImageLoadingListener {

    ImageView.ScaleType preScaleType;
    ImageView.ScaleType scaleType;

    public PreImageLoadingListener(ImageView.ScaleType preScaleType, ImageView.ScaleType scaleType) {
        this.preScaleType = preScaleType;
        this.scaleType = scaleType;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        ImageView imageView = (ImageView) view;
        imageView.setScaleType(preScaleType);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        ImageView imageView = (ImageView) view;
        imageView.setScaleType(scaleType);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
