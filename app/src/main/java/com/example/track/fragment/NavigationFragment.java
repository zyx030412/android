package com.example.track.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviPoi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;

import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearchV2;
import com.amap.api.services.route.Navi;
import com.example.track.InputTipsActivity;
import com.example.track.MainActivity;
import com.example.track.NavigationActivity;
import com.example.track.R;
import com.example.track.entity.Trip;
import com.example.track.model.PoiOverPlayModel;
import com.example.track.entity.Constants;
import com.example.track.entity.ToastUtil;
import com.example.track.service.StoreTripService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class NavigationFragment extends Fragment implements
        AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,
        OnPoiSearchListener, View.OnClickListener {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private AMap mAMap;
    private String mKeyWords = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private TextView mKeywordsTextView;
    private Marker mPoiMarker;
    private ImageView mCleanKeyWords;
    private RelativeLayout relativeLayout;
    private Button navigationButton;

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;
    public static final int RESULT_CODE_KEYWORDS = 102;

    public AMap.OnMarkerClickListener mOnMarkerClickListener = new OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            relativeLayout.setVisibility(View.VISIBLE);
            LatLng thisLatLng = marker.getPosition();
            String title = marker.getTitle();
            String poiId = marker.getId();
            Poi end = new Poi(title,thisLatLng,poiId);

            navigationButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AmapNaviParams params = new AmapNaviParams(null, null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);

                    AMapCarInfo carInfo = new AMapCarInfo();
                    carInfo.setCarNumber("浙A12345");
                    carInfo.setCarType("1");             //设置车辆类型,0:小车; 1:货车. 默认0(小车).
                    carInfo.setVehicleAxis("6");         //设置货车的轴数，mCarType = 1时候生效，取值[0-255]，默认为2
                    carInfo.setVehicleHeight("3.56");    //设置货车的高度，单位：米，mCarType = 1时候生效，取值[0-25.5],默认1.6米
                    carInfo.setVehicleLength("7.3");     //设置货车的最大长度，单位：米，mCarType = 1时候生效，取值[0-25]，默认6米
                    carInfo.setVehicleWidth("2.5");      //设置货车的最大宽度，单位：米，mCarType = 1时候生效，取值[0-25.5]，默认2.5米
                    carInfo.setVehicleSize("2");         //设置货车的大小，1-微型货车 2-轻型/小型货车 3-中型货车 4-重型货车，默认为2
                    carInfo.setVehicleLoad("25.99");     //设置货车的总重，即车重+核定载重，单位：吨，mCarType = 1时候生效，取值[0-6553.5]
                    carInfo.setVehicleWeight("20");      //设置货车的核定载重，单位：吨，mCarType = 1时候生效，取值[0-6553.5]
                    carInfo.setRestriction(true);        //设置是否躲避车辆限行，true代表躲避车辆限行，false代表不躲避车辆限行,默认为true
                    carInfo.setVehicleLoadSwitch(true);   //设置货车重量是否参与算路，true-重量会参与算路；false-重量不会参与算路。默认为false

                    params.setCarInfo(carInfo);
                    /**
                     * 设置播报模式
                     * @param context
                     * @param mode  1-简洁播报 2-详细播报 3-静音模式
                     * @since 7.1.0
                     */
                    params.setRouteStrategy(2);
                    NaviPoi thisStart = params.getStart();
                    NaviPoi thisEnd = params.getEnd();
                    String address = null;
                    // 启动组件
                    AmapNaviPage.getInstance().showRouteActivity(getContext(), params, null);

                    try {
                        mLocationClient = new AMapLocationClient(getContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //初始化AMapLocationClientOption对象
                    mLocationOption = new AMapLocationClientOption();
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    mLocationOption.setOnceLocation(true);
                    mLocationOption.setOnceLocationLatest(true);
                    mLocationOption.setNeedAddress(true);


                    mLocationListener = new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation amapLocation) {
                            if (amapLocation != null) {
                                if (amapLocation.getErrorCode() == 0) {
                                    //可在其中解析amapLocation获取相应内容。
                                    String startLa = String.valueOf(amapLocation.getLatitude());
                                    String startLo = String.valueOf(amapLocation.getLongitude());
                                    String startAddress = amapLocation.getAddress();
                                    String endLa = String.valueOf(thisEnd.getCoordinate().latitude);
                                    String endLo = String.valueOf(thisEnd.getCoordinate().latitude);
                                    String endAddress = thisEnd.getName();
                                    Date date = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String startTime = simpleDateFormat.format(date);
                                    Trip trip = new Trip(1145.14,"20",15,startTime,startTime,startAddress,endAddress,startLo,endLo,startLa,endLa,MainActivity.getUser());
                                    new StoreTripService().storeTrip(trip);
                                    System.out.println(trip.toString());
                                }else {
                                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                    Log.e("AmapError","location Error, ErrCode:"
                                            + amapLocation.getErrorCode() + ", errInfo:"
                                            + amapLocation.getErrorInfo());
                                }
                            }
                        }
                    };


                    mLocationClient.setLocationOption(mLocationOption);
                    mLocationClient.setLocationListener(mLocationListener);
                    mLocationClient.startLocation();


                    //设置退出导航组件时摧毁导航实例
                    params.setNeedCalculateRouteWhenPresent(true);
                    relativeLayout.setVisibility(View.GONE);
                }
            });
            return false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_demo,container,false);
        mCleanKeyWords = v.findViewById(R.id.clean_keywords);
        mCleanKeyWords.setOnClickListener(this);
        relativeLayout = v.findViewById(R.id.map_navi_bottom);
        navigationButton = v.findViewById(R.id.map_navi_button);
        init(v);
        mKeyWords = "";
        mAMap.setOnMarkerClickListener(mOnMarkerClickListener);



        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    /**
     * 初始化AMap对象
     */
    private void init(View v) {
        if (mAMap == null) {
            Fragment fragment = getChildFragmentManager().findFragmentById(R.id.map);
            SupportMapFragment supportMapFragment = (SupportMapFragment) fragment;
            mAMap = supportMapFragment.getMap();

            setUpMap();
        }
        mKeywordsTextView = v.findViewById(R.id.main_keywords);
        mKeywordsTextView.setOnClickListener(this);
    }

    /**
     * 设置页面监听
     */
    private void setUpMap() {
        mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        mAMap.getUiSettings().setRotateGesturesEnabled(false);

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位，且将视角移动到中央
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。

        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        relativeLayout.setVisibility(View.GONE);
//        mAMap.getMyLocation().get

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this.getContext());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + mKeyWords);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keywords) throws AMapException {
        showProgressDialog();// 显示进度框
        currentPage = 1;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, "", Constants.DEFAULT_CITY);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查第一页
        query.setPageNum(currentPage);

        poiSearch = new PoiSearch(this.getContext(),query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        return view;
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(this.getContext(), infomation);

    }


    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        mAMap.clear();// 清理之前的图标
                        PoiOverPlayModel poiOverlay = new PoiOverPlayModel(mAMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.show(this.getContext(),
                                R.string.no_result);
                    }
                }
            } else {
                ToastUtil.show(this.getContext(),
                        R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getContext(), rCode);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        // TODO Auto-generated method stub

    }

    /**
     * 输入提示activity选择结果后的处理逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_INPUTTIPS && data
                != null) {
            mAMap.clear();
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);
            if (tip.getPoiID() == null || tip.getPoiID().equals("")) {
                try {
                    doSearchQuery(tip.getName());
                } catch (AMapException e) {
                    e.printStackTrace();
                }
            } else {
                addTipMarker(tip);
            }
            mKeywordsTextView.setText(tip.getName());
            if(!tip.getName().equals("")){
                mCleanKeyWords.setVisibility(View.VISIBLE);
            }
        } else if (resultCode == RESULT_CODE_KEYWORDS && data != null) {
            mAMap.clear();
            String keywords = data.getStringExtra(Constants.KEY_WORDS_NAME);
            if(keywords != null && !keywords.equals("")){
                try {
                    doSearchQuery(keywords);
                } catch (AMapException e) {
                    e.printStackTrace();
                }
            }
            mKeywordsTextView.setText(keywords);
            if(!keywords.equals("")){
                mCleanKeyWords.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 用marker展示输入提示list选中数据
     *
     * @param tip
     */
    private void addTipMarker(Tip tip) {
        if (tip == null) {
            return;
        }
        mPoiMarker = mAMap.addMarker(new MarkerOptions());
        LatLonPoint point = tip.getPoint();
        if (point != null) {
            LatLng markerPosition = new LatLng(point.getLatitude(), point.getLongitude());
            mPoiMarker.setPosition(markerPosition);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));
        }
        mPoiMarker.setTitle(tip.getName());
        mPoiMarker.setSnippet(tip.getAddress());

    }




    /**
     * 点击事件回调方法
     */
    @Override
    public void onClick(View v) { switch (v.getId()) {
            case R.id.main_keywords:
                Intent intent = new Intent(this.getContext(), InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.clean_keywords:
                mKeywordsTextView.setText("");
                mAMap.clear();
                mCleanKeyWords.setVisibility(View.GONE);
            default:
                break;
        }
    }
}
