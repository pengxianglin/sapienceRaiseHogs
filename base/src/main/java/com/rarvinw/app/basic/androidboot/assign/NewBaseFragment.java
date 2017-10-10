package com.rarvinw.app.basic.androidboot.assign;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rarvinw.app.basic.androidboot.base.BaseFragment;
import com.rarvinw.app.basic.androidboot.mvp.IPresenter;

/**
 * Created by newhope on 2016/4/5.
 */
public abstract class NewBaseFragment<T extends IPresenter> extends BaseFragment {
    T presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=super.onCreateView(inflater,container,savedInstanceState);
        presenter = initPresenter();
        presenter.init();
        presenter.attachView(this);
        return view;
    }

    protected abstract T initPresenter();
    protected T getPresenter(){
        return presenter;
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        presenter.detachView(false);
        presenter.suspend();
        presenter = null;
        super.onDestroyView();
    }

    //仅能在Fragment显示时调用
    @Override
    public String getPreferenceString(String str) {
        SharedPreferences preferences = getActivity().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String ret = preferences.getString(str, "");
        return ret;
    }

    //仅能在Fragment显示时调用
    @Override
    public void addPreferenceData(String key, String value) {
        SharedPreferences preferences = getActivity().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
