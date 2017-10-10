package com.rarvinw.app.basic.androidboot.assign;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.rarvinw.app.basic.androidboot.base.BaseActivity;
import com.rarvinw.app.basic.androidboot.mvp.BasePresenter;
import com.rarvinw.app.basic.androidboot.mvp.IPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newhope on 2016/4/5.
 */
public abstract class NewBaseActivity<T extends IPresenter> extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int CODE_REQUEST_PERMISSION = 100;
    private static final String TAG = "NewBaseActivity";

    private T presenter;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        presenter = initPresenter();
        presenter.init();
        presenter.attachView(this);
    }

    private void checkAllPermission(final String[] permissions) {
        Log.d(TAG, "checkAllPermission:" + permissions);
        List<String> deny = new ArrayList<>();
        if (permissions != null && permissions.length != 0) {
            for (int i = 0; i < permissions.length; ++i) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permissions[i])) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        deny.add(permissions[i]);
                    }
                }
            }
        }
        if (deny.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("权限设置");
            builder.setMessage("请设置必要的权限");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions(NewBaseActivity.this, permissions, CODE_REQUEST_PERMISSION);
                }
            });
            builder.create().show();
        } else {
            ActivityCompat.requestPermissions(this, permissions, CODE_REQUEST_PERMISSION);
        }
    }

    protected abstract void initView();

    protected abstract T initPresenter();

    protected T getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView(false);
        presenter.suspend();
        presenter = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_PERMISSION) {
            List<String> deny = new ArrayList<>();
            List<String> unAuthorized = new ArrayList<>();

            for (int i = 0; i < permissions.length; ++i) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permissions[i])) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        deny.add(permissions[i]);
                    } else {
                        unAuthorized.add(permissions[i]);
                    }
                }
            }
            if (unAuthorized.size() != 0) {
                if (onPermissionCheckCallback != null) {
                    onPermissionCheckCallback.onPermissionDenied(unAuthorized);
                }
            } else if (deny.size() != 0) {
                checkAllPermission(permissions);
            } else {
                if (onPermissionCheckCallback != null) {
                    onPermissionCheckCallback.onPermissionGranted();
                }
            }
        }
    }


    protected void doRunAfterCheckPermission(String[] permissions, OnPermissionCheckCallback callback) {
        this.onPermissionCheckCallback = callback;
        checkAllPermission(permissions);
    }

    protected OnPermissionCheckCallback onPermissionCheckCallback;

    public OnPermissionCheckCallback getOnPermissionOkCallback() {
        return onPermissionCheckCallback;
    }

    public void setOnPermissionOkCallback(OnPermissionCheckCallback onPermissionOkCallback) {
        this.onPermissionCheckCallback = onPermissionOkCallback;
    }

    public static interface OnPermissionCheckCallback {
        public void onPermissionGranted();

        public void onPermissionDenied(List<String> unAuthorized);
    }
}
