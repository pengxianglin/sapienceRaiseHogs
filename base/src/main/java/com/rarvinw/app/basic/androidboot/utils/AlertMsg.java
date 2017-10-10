package com.rarvinw.app.basic.androidboot.utils;

import android.content.DialogInterface;

/**
 * Created by newhope on 2016/4/7.
 */
public class AlertMsg {
    private String title;
    private String content;
    private String ok;
    private String cancel;
    private DialogInterface.OnClickListener onPositiveClick;
    private DialogInterface.OnClickListener onNegativeClick;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public DialogInterface.OnClickListener getOnPositiveClick() {
        return onPositiveClick;
    }

    public void setOnPositiveClick(DialogInterface.OnClickListener onPositiveClick) {
        this.onPositiveClick = onPositiveClick;
    }

    public DialogInterface.OnClickListener getOnNegativeClick() {
        return onNegativeClick;
    }

    public void setOnNegativeClick(DialogInterface.OnClickListener onNegativeClick) {
        this.onNegativeClick = onNegativeClick;
    }
}
