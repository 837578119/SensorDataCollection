package com.bba.datacollection;

import android.app.Application;

/**
 * Created by uy001247 on 12/3/2020.
 */

public class AndroidApplication extends Application {
    private static AndroidApplication instance;
    private String net_status;
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }
    public AndroidApplication getInstance(){
        return instance;
    }
    public String getNetStatus(){
        return net_status;
    }
    public void setNetStatus(String s){
        net_status = s;
    }
}
