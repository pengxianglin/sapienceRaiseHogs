package com.rarvinw.app.basic.androidboot.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by newhope on 2016/4/28.
 */
public class DBData implements Parcelable {

    public static final String FIELD_FOREIGN = "_parent";

    private static final String TAG = "DBData";
    private Long _id;

    public Long getDBid() {
        return _id;
    }

    /*public void setDBid(int  id){
        Log.d(TAG,"setDBid : "+id);
        if(_id == null){
            this._id = (long)id;
        }
    }*/
    public void setDBid(long id) {
        Log.d(TAG, "setDBid : " + id);
        if (_id == null) {
            this._id = (long) id;
        }
    }

    public String getUid() {
        return null;
    }

    public String getUniqualId() {
        return getUid();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
    }

    public DBData() {
    }

    protected DBData(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
    }

}
