package com.newhope.smartpig;

//import com.newhope.multi_city_selector.utils.DBHelper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import com.newhope.smartpig.utils.AppState;
import com.newhope.smartpig.utils.Constants;
import com.newhope.smartpig.utils.IAppState;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.rarvinw.app.basic.androidboot.BasicApplication;
import com.rarvinw.app.basic.androidboot.BasicConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by newhope on 2016/4/5.
 */
public class MyApplication extends BasicApplication {

    private static final String TAG = "MyApplication";
    private AppState appState;

    @Override
    public void onCreate() {
        super.onCreate();
        initMyApp();
        initImageLoader(getApplicationContext());
//        CrashHandler crashHandler = new CrashHandler(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public void finishActivity() {
        System.exit(0);
    }

    private void initMyApp() {
        File dir = new File(Constants.DIR_COMPRESSED);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //replaceFont(null, new TypeFaceManager(this.getResources().getAssets()).getSTHeiti());
    }

    public static final void replaceFont(String fontName, Typeface typeface) {

        if (isVersionGreaterOrEqualToLollipop()) {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("sans-serif", typeface);
            newMap.put("sans-serif-medium", typeface);
            newMap.put("sans-serif-light", typeface);
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Field staticField = Typeface.class
                        .getDeclaredField("DEFAULT");
                staticField.setAccessible(true);
                staticField.set(null, typeface);

                staticField = Typeface.class
                        .getDeclaredField("MONOSPACE");
                staticField.setAccessible(true);
                staticField.set(null, typeface);

                staticField = Typeface.class
                        .getDeclaredField("SERIF");
                staticField.setAccessible(true);
                staticField.set(null, typeface);

                staticField = Typeface.class
                        .getDeclaredField("SANS_SERIF");
                staticField.setAccessible(true);
                staticField.set(null, typeface);

                staticField = Typeface.class
                        .getDeclaredField("DEFAULT_BOLD");
                staticField.setAccessible(true);
                staticField.set(null, typeface);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isVersionGreaterOrEqualToLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void initBasic() {
        super.initBasic();
        setConfig(new BasicConfig() {

            @Override
            public Map<Class, String> configTableInDB() {
                return super.configTableInDB();
            }

            @Override
            public Class[] configAllEntitiesClass() {
                return Constants.ALL_DB_ENTITIES;
            }

            @Override
            public String configApiUrl() {
                return super.configApiUrl();
            }


          /*  @Override
            public Interceptor configNetworkInterceptor() {
                return new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Headers.Builder builder = request.headers().newBuilder();
                        builder.set("Content-Type", "application/x-www-form-urlencoded");
                        Request.Builder requestBuilder = request.newBuilder().headers(builder.build());
//                        requestBuilder.removeHeader("Content-Type");
//                        requestBuilder.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                        return chain.proceed(requestBuilder.build());
                    }
                };
            }*/

            @Override
            public Interceptor configCustomInterceptor() {
                return new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        IAppState state = appState;
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
//
//                        if (state.getLoginInfo() != null && state.getLoginInfo().getToken() != null) {
//                            HttpUrl.Builder builder = request.url().newBuilder()
//                                    .addQueryParameter("token", state.getLoginInfo().getToken())
////                                    .addQueryParameter("token", "123456")
//                                    .addQueryParameter("userId", state.getLoginInfo().getUid());
//                            //.addQueryParameter("userId", "4a9e2217-050e-11e6-a419-005056a77111");
//                            HttpUrl url = builder.build();
//                            request = requestBuilder.header("Connection", "close").url(url).build();
//                            return chain.proceed(request);
//                        }
                        HttpUrl.Builder builder = request.url().newBuilder()
//                                .addQueryParameter("token",state.getLoginInfo().getToken())
                                .addQueryParameter("token", "123456")
                                //       .addQueryParameter("userId", state.getLoginInfo().getUid());
                                .addQueryParameter("userId", "1b7814fe-6070-4112-b3c8-8dc044ee2bc2");
                        HttpUrl url = builder.build();
                        requestBuilder.header("Connection", "close").url(url);
                        //   return chain.proceed(request);

                        return chain.proceed(requestBuilder.build());
                    }
                };
            }
        });
        //创建数据库
/*
        DBHelper helper = new DBHelper(getApplicationContext());
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        //for test;
       /* LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUid("4a9e2217-050e-11e6-a419-005056a77111");
        loginInfo.setName("fygly");
        loginInfo.setAccount("fygly");
        loginInfo.setToken("123456");
        IAppState.Factory.build().setLoginInfo(loginInfo);*/
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public AppState getAppState() {
        return appState;
    }

    public void setAppState(AppState appState) {
        this.appState = appState;
    }
}
