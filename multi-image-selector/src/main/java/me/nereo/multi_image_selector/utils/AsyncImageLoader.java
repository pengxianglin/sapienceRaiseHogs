package me.nereo.multi_image_selector.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

    Context mContext;

    public AsyncImageLoader(Context _mContext) {
        this.mContext = _mContext;
    }

    public Drawable loadDrawable(final String imageUrl,
                                 final ImageCallback imageCallback) {

        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded(message.obj.toString());
            }
        };

        new Thread() {
            @Override
            public void run() {
                Message message = handler.obtainMessage(0, imageUrl);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public interface ImageCallback {
        public void imageLoaded(String imageUrl);
    }
}
