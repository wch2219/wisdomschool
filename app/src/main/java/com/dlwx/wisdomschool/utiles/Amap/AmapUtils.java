package com.dlwx.wisdomschool.utiles.Amap;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dlwx.baselib.utiles.LogUtiles;

/**
 * 高德定位工具类
 */

public class AmapUtils {
    //声明mLocationOption对象
    private static AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    private static AMapLocationClient mLocationClient = null;
    public static String city = "";
    private static double latitude;
    private static double longitude;

    public static void initialization(Context ctx) {
        //初始化定位
        mLocationClient = new AMapLocationClient(ctx);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public static AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //解析定位结果
                    city = aMapLocation.getCity();
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    mLocationClient.stopLocation();
                }else{
                    city = "全国";
                }
                if ( locationListener != null) {
                    locationListener.getResultAdd(city,latitude,longitude);
                    locationListener.getaMapLocation(aMapLocation);
                }
                LogUtiles.LogI(city);
           }
        }
    };
    public static LocationListener locationListener;
    public interface LocationListener {

        void getResultAdd(String city,double latitude,double longitude);
         void getaMapLocation(AMapLocation aMapLocation);
    }
    public static void setLocationListener(LocationListener location){
        locationListener = location;
    }
}
