package com.newhope.smartpig.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * Created by Administrator on 2016-4-22.
 */
public class CommonActivity extends AppCompatActivity {


    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity+fragment方式，在加载页面的时候是先加载activity，在加载fragment，中间有间隔，感觉像是黑屏一下，这里起到一个缓冲作用
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    public String getPreferenceString(String str) {
        SharedPreferences preferences = getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String ret = preferences.getString(str, "");
        return ret;
    }

    public void addPreferenceData(String key, String value) {
        SharedPreferences preferences = getSharedPreferences("user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    @Override
    public void onBackPressed() {
        closed();
    }


    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public void closed() {
        if (isUpdate()) {
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closed();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
