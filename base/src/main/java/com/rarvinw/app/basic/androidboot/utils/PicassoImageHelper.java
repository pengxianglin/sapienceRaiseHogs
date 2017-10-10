package com.rarvinw.app.basic.androidboot.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.nereo.multi_image_selector.utils.FileUtils;

/**
 * Created by newhope on 2016/4/3.
 */
public class PicassoImageHelper implements IImageHelper {
    private Context context;
    private static final String HTTP = "http://";

    public PicassoImageHelper(Context context) {
        this.context = context;
    }

    Handler mHandler = new Handler();
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void displayImage(String url, ImageView target) {
        displayImage(url, target, true);
    }

    public void displayImage(final String url, final ImageView target, boolean isFit) {
        if (url.contains(HTTP)) {
            Picasso.with(context).load(url).fit().centerInside().into(target);
        } else {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        Bitmap bitmap = FileUtils.decodeSampledBitmapFromResource(url, 720, 1080);

                        @Override
                        public void run() {
                            target.setImageBitmap(bitmap);
                        }
                    });
                }
            });
//            new Thread(new Runnable() {
//            Bitmap bitmap = FileUtils.decodeSampledBitmapFromResource(url, 100, 100);
//                @Override
//                public void run() {
//            target.setImageBitmap(bitmap);
//                }
//            }).start();
        }
    }

    public Bitmap getImage(String url) throws IOException {
        return Picasso.with(context).load(url).get();
    }

    @Override
    public void displayImage(int resId, ImageView target, boolean isFit) {
        if (isFit) {
            Picasso.with(context).load(resId).fit().centerInside().into(target);
        } else {
            Picasso.with(context).load(resId).into(target);
        }

    }
}
