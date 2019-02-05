package com.ivan.animal.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ivan.animal.R;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * ImageLoader processor
 * Created by Ivan on 2017/1/14.
 *
 * @author IvanLu
 */
public class ImageLoaderManager {

    private static ImageLoaderManager INSTANCE;
    private ImageLoader imageLoader;

    private ImageLoaderManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    public static ImageLoaderManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ImageLoaderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoaderManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public void loadImageUrl(String url, ImageView imageView, DisplayImageOptions displayImageOptions, ImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, displayImageOptions, listener);
    }

    public void clearMemoryCache() {
        imageLoader.clearMemoryCache();
    }

    public void clearDiscCache() {
        imageLoader.clearDiskCache();
    }

    public static DisplayImageOptions InSamplePowerOfTwoDisplayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.emptyImageColor)
            .showImageForEmptyUri(R.color.emptyImageColor)
            .showImageOnFail(R.color.emptyImageColor)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions ExactlyStretchedDisplayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.emptyImageColor)
            .showImageForEmptyUri(R.color.emptyImageColor)
            .showImageOnFail(R.color.emptyImageColor)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

}
