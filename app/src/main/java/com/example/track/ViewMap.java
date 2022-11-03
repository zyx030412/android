package com.example.track;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.model.AMapCarInfo;

import java.util.ArrayList;
import java.util.List;

public class ViewMap extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_demo);


        AMapLocationClient.updatePrivacyShow(this, true, true);
        AMapLocationClient.updatePrivacyAgree(this, true);


        mapView = findViewById(R.id.demo_map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mapView.getMap();
        }

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位，且将视角移动到中央
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));


        //构建导航组件配置类，没有传入起点，所以起点默认为 “我的位置”
//        AmapNaviParams params = new AmapNaviParams(null, null, null, AmapNaviType.DRIVER, AmapPageType.ROUTE);
        //启动导航组件
//        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);

        //起点
//        Poi start = new Poi("北京首都机场", new LatLng(40.080525,116.603039), "B000A28DAE");
//        //途经点
//        List<Poi> poiList = new ArrayList();
//        poiList.add(new Poi("故宫", new LatLng(39.918058,116.397026), "B000A8UIN8"));
//        //终点
//        Poi end = new Poi("北京大学", new LatLng(39.941823,116.426319), "B000A816R6");
//        // 组件参数配置
//        AmapNaviParams params = new AmapNaviParams(start, poiList, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
//        //语言
//        params.setCarInfo(new AMapCarInfo());
//        /**
//         * 设置播报模式
//         * @param context
//         * @param mode  1-简洁播报 2-详细播报 3-静音模式
//         * @since 7.1.0
//         */
//        params.setRouteStrategy(2);
//        // 启动组件
//        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
//        //设置退出导航组件时摧毁导航实例
//        params.setNeedCalculateRouteWhenPresent(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
