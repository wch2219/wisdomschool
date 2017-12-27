package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.CityListAdapter1;
import com.dlwx.wisdomschool.adapter.CityListAdapter2;
import com.dlwx.wisdomschool.adapter.SeleteSchoolAdapter;
import com.dlwx.wisdomschool.utiles.CityJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学校地址
 */
public class SchoolAddressActivity extends BaseActivity implements LocationSource, AMapLocationListener
        , PoiSearch.OnPoiSearchListener {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_cityname)
    TextView tvCityname;
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.ll_seletecity)
    LinearLayout ll_seletecity;
    @BindView(R.id.ll_seach)
    LinearLayout ll_seach;

    @BindView(R.id.tv_input)
    TextView tv_input;
    @BindView(R.id.tv_close)
    TextView tv_close;

    MyLocationStyle myLocationStyle;
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private String city;
    private List<CityJson.SanJiLianDBean.CityList.CBean> citys;
    private AlertDialog diashow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_school_address);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        lvList.setAdapter(new SeleteSchoolAdapter(ctx));

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_back, R.id.ll_seletecity, R.id.tv_input, R.id.ll_seach, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_seletecity:
                showPopuCity();
                break;
            case R.id.tv_input:
                showInputPopu();
                break;
            case R.id.ll_seach:
                mapview.setVisibility(View.GONE);
                tv_close.setVisibility(View.VISIBLE);
                tv_input.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_close:
                mapview.setVisibility(View.VISIBLE);
                tv_close.setVisibility(View.GONE);
                tv_input.setVisibility(View.GONE);
                break;

        }
    }


    @Override
    public void save(Bundle savedInstanceState) {
        super.save(savedInstanceState);
        mapview.onCreate(savedInstanceState);
        //初始化地图控制器对象
        AMap aMap = null;
        aMap = mapview.getMap();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位监听
        aMap.setLocationSource(this);
// 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种

    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                city = aMapLocation.getCity();
                tvCityname.setText(city);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("wch", errText);
            }
        }
    }

    /**
     * 检索数据
     *
     * @param city
     */
    private void queryDate(double latitude, double longitude, String city) {
        PoiSearch.Query query = new PoiSearch.Query("", "", city);
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(1);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> pois = poiResult.getPois();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapview.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapview.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapview.onSaveInstanceState(outState);
    }

    private void showPopuCity() {

        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_city, null);
        final MyPopuWindow popu = new MyPopuWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popu.setFocusable(true);
        popu.showAsDropDown(rlBack);
        final ViewHolderPopu viewHolderPopu = new ViewHolderPopu(view);
        CityJson.SanJiLianDBean sanJiLianDBean = CityJson.initJsonData(getApplicationContext());
        final List<CityJson.SanJiLianDBean.CityList> citylist = sanJiLianDBean.getCitylist();
        final CityListAdapter1 cityListAdapter1 = new CityListAdapter1(ctx, citylist);
        viewHolderPopu.lv_pro.setAdapter(cityListAdapter1);
        citys = citylist.get(0).getC();
        viewHolderPopu.lv_city.setAdapter(new CityListAdapter2(ctx, citys));
        viewHolderPopu.lv_pro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                citys = citylist.get(i).getC();
                viewHolderPopu.lv_city.setAdapter(new CityListAdapter2(ctx, citys));
                cityListAdapter1.setPos(i);
            }
        });
        viewHolderPopu.lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvCityname.setText(citys.get(i).getN());
                popu.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                diashow.dismiss();
                break;
            case R.id.btn_aff:
                diashow.dismiss();
                break;
        }
    }

    /**
     * 显示输入窗体
     */
    private void showInputPopu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_input, null);
        builder.setView(view);
        ViewHolderDia viewHolderDia = new ViewHolderDia(view);
        viewHolderDia.tv_close.setOnClickListener(this);
        viewHolderDia.btn_aff.setOnClickListener(this);
        diashow = builder.show();
    }

    private class ViewHolderPopu {
        public View rootView;
        public ListView lv_pro;
        public ListView lv_city;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.lv_pro = (ListView) rootView.findViewById(R.id.lv_pro);
            this.lv_city = (ListView) rootView.findViewById(R.id.lv_city);
        }

    }

    private class ViewHolderDia {
        public View rootView;
        public EditText et_name;
        public TextView tv_close;
        public Button btn_aff;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.et_name = (EditText) rootView.findViewById(R.id.et_name);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.btn_aff = (Button) rootView.findViewById(R.id.btn_aff);
        }

    }
}
