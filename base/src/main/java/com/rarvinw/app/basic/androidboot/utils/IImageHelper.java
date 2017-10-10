package com.rarvinw.app.basic.androidboot.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.rarvinw.app.basic.androidboot.BasicApplication;

import java.io.IOException;

/**
 * Created by newhope on 2016/4/3.
 */
public interface IImageHelper {
    void displayImage(String url, ImageView target);
    void displayImage(String url, ImageView target,boolean isFit);
    void displayImage(int resId, ImageView target, boolean isFit);
    Bitmap getImage(String url) throws IOException;

    interface IFactory{
        IImageHelper build();
    }
    class Factory implements IFactory{
        static IImageHelper helper = new PicassoImageHelper(BasicApplication.getInstance());
        public IImageHelper build(){
            return helper;
        }
    }
}
