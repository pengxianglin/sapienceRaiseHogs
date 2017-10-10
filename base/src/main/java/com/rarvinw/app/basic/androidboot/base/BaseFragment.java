package com.rarvinw.app.basic.androidboot.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.rarvinw.app.basic.androidboot.mvp.IView;
import com.rarvinw.app.basic.androidboot.utils.AlertMsg;

/**
 * Created by newhope on 2016/4/4.
 */
public abstract class BaseFragment extends Fragment implements IView {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoadingView(boolean isShow, String loadingMsg) {

    }

    @Override
    public void showLoadingView(boolean isShow) {
        showLoadingView(isShow, null);
    }

    @Override
    public void showErrorMsg(String msg) {
        showErrorMsg(0, msg);
    }

    @Override
    public void showErrorMsg(int code, String msg) {
        Activity activity = getActivity();

        String[] strs = msg.split(":");
        if (strs.length >= 2) {
            if ("SYS_NO_DATA".equals(strs[1])) {
                Toast.makeText(activity, strs[2], Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (activity != null) {
            if (strs.length >= 2) {
                if ("SYS_NO_DATA".equals(strs[1])) {
                    return;
                }
            }
            if (strs.length >= 3) {
                Toast.makeText(activity, strs[2], Toast.LENGTH_SHORT).show();
            } else if (strs.length >= 2) {
                Toast.makeText(activity, strs[1], Toast.LENGTH_SHORT).show();
            } else if (strs.length >= 1) {
                Toast.makeText(activity, strs[0], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "错误:" + code, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void showAlertMsg(AlertMsg msg) {

    }

    @Override
    public void showMsg(String msg) {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
