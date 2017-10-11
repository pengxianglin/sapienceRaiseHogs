package com.newhope.smartpig.base;

/**
 * Created by newhope on 2016/4/20.
 */
public class DataLoader<P, T, S>{

    public int getOperType(){return -1;}
    public T loadDataFromMemory(P param) {
        return null;
    }
    public T loadDataFromNetwork(P param) throws Throwable {
        return null;
    }
    public T loadDataFromDB(P param){
        return null;
    }

    public void saveDataToDB(T data){}
    public void saveDataToMemory(T data){}
    public S saveDataToNetwork(T data) throws Throwable{return null;}
}