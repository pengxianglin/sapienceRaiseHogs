package com.newhope.smartpig.base.cache;

import android.os.Parcel;

import com.newhope.smartpig.base.cache.serializer.JsonSerializer;
import com.rarvinw.app.basic.androidboot.utils.DBData;

import nl.qbusict.cupboard.annotation.Index;

/**
 * Created by newhope on 2016/4/8.
 */
public class CacheEntity extends DBData {
    String data;
    long time;
    int operate;
    @Index
    String key;

    public static final String COLUME_KEY = "key";

    public CacheEntity(){

    }

    public CacheEntity(Object object, int operate){
        this(ICache.Factory.buildKey(String.valueOf(operate))
                ,object
                ,operate);
    }
    public CacheEntity(String key, Object object, int operate){
        this(key,object, operate,System.currentTimeMillis());
    }

    public String getUid() {
        return key;
    }

    public void setUid(String uid) {
        this.key = uid;
    }

    public CacheEntity(String key , Object object, int operate, long time){
        this.data = new JsonSerializer().serialize(object);
        this.time = time;
        this.key = key;
        this.operate = operate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public <T> T getData(Class<T> type){
        return new JsonSerializer().deserialize(data, type);
    }

    public <T> void setData(T t){
        this.data = new JsonSerializer().serialize(t);
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    @Override
    public String toString() {
        return "CacheEntity{" +
                "data='" + data + '\'' +
                ", time=" + time +
                ", operate=" + operate +
                ", key='" + key + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.data);
        dest.writeLong(this.time);
        dest.writeInt(this.operate);
        dest.writeString(this.key);
    }

    protected CacheEntity(Parcel in) {
        super(in);
        this.data = in.readString();
        this.time = in.readLong();
        this.operate = in.readInt();
        this.key = in.readString();
    }

    public static final Creator<CacheEntity> CREATOR = new Creator<CacheEntity>() {
        @Override
        public CacheEntity createFromParcel(Parcel source) {
            return new CacheEntity(source);
        }

        @Override
        public CacheEntity[] newArray(int size) {
            return new CacheEntity[size];
        }
    };
}
