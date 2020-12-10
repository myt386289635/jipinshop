package com.example.administrator.jipinshop.activity.web.tuanyou.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.math.BigDecimal;

/**
 * <br>
 * function:h5交互基类
 * <p>
 */
class BaseJsObject {
    private String key, value;

    public Activity activity;

    public BaseJsObject(Activity activity) {
        this.activity = activity;
    }

    //拿到设置webView的属性
    @SuppressWarnings("unused")
    @JavascriptInterface
    public void setExtraInfoHead(String key, String value) {
        setKey(key);
        setValue(value);
        Log.e("添加头信息", key + "," + value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return 返回数据给h5
     * @JavascriptInterface 这个注解必须添加，否则js调不到这个方法
     * 这个方法名称也必须要和h5保持一致
     *
     * 高德地图和腾讯地图是GCJ02,百度地图的坐标系是BD09
     * 给的是高德地图的经纬度
     */
    @JavascriptInterface
    public void startNavigate(String startLat, String startLng, String endLat, String endLng) {
        //去做想做的事情。比如导航，直接带着开始和结束的经纬度Intent到导航activity就可以
        if (TextUtils.isEmpty(startLat) || TextUtils.isEmpty(startLng) || TextUtils.isEmpty(endLat)
                || TextUtils.isEmpty(endLng)) {//如果接收的数据不正确，给予提示
            Toast.makeText(activity, "有不正确的数据", Toast.LENGTH_LONG).show();
            return;
        }

        if (checkMapAppsIsExist(activity, "com.baidu.BaiduMap")) {
            //百度地图
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("baidumap://map/direction?origin=name:我的位置"
                    + "|latlng:"+bd_enLat(startLat,startLng) +"," + bd_enlng(startLat,startLng)
                    + "&destination=name:"
                    + "终点"
                    + "|latlng:" + bd_enLat(endLat,endLng) + "," + bd_enlng(endLat,endLng)
                    + "&mode=driving&target=1&coord_type=bd09ll&car_type=TIME"));
            activity.startActivity(intent);
        } else if (checkMapAppsIsExist(activity, "com.autonavi.minimap")) {
            //高德地图
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.autonavi.minimap");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("androidamap://route?sourceApplication=" + R.string.app_name
                    + "&sname=我的位置&dlat=" + endLat
                    + "&dlon=" + endLng
                    + "&dname=" + "终点"
                    + "&dev=0&m=0&t=0"));
            activity.startActivity(intent);
        } else if (checkMapAppsIsExist(activity, "com.tencent.map")) {
            //腾讯地图
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("qqmap://map/routeplan?type=drive&from=我的位置&fromcoord=0,0"
                    + "&to=" + "终点"
                    + "&tocoord=" + endLat + "," + endLng
                    + "&policy=0&referer=myapp"));
            activity.startActivity(intent);
        } else {
            //没有地图app
            String url = "http://api.map.baidu.com/direction?origin=latlng:" +
                    bd_enLat(startLat,startLng)+","+ bd_enlng(startLat,startLng) +
                    "|name:我的位置&destination=name:终点|latlng:" + bd_enLat(endLat,endLng) + "," +
                    bd_enlng(endLat,endLng) + "&mode=driving&target=1&coord_type=bd09ll&car_type=TIME" +
                    "&region=杭州&output=html";
            Intent ExeIntent = new Intent();
            ExeIntent.setAction("android.intent.action.VIEW");
            ExeIntent.setData(Uri.parse(url));
            activity.startActivity(ExeIntent);
            ToastUtil.show("您未安装百度地图，正在为您打开浏览器");
        }

    }

    //检测地图应用是否安装
    public boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    //高德坐标转百度（传入经度、纬度）
    public String bd_enLat(String gg_lat , String gg_lng) {
        double X_PI = Math.PI * 3000.0 / 180.0;
        double y = new BigDecimal(gg_lat).doubleValue();
        double x = new BigDecimal(gg_lng).doubleValue();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bd_lat = z * Math.sin(theta) + 0.006;
        return bd_lat + "";
    }

    //高德坐标转百度（传入经度、纬度）
    public String bd_enlng(String gg_lat , String gg_lng) {
        double X_PI = Math.PI * 3000.0 / 180.0;
        double y = new BigDecimal(gg_lat).doubleValue();
        double x = new BigDecimal(gg_lng).doubleValue();
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        return bd_lng + "";
    }
}
