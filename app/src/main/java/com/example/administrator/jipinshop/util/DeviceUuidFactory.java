package com.example.administrator.jipinshop.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.ta.utdid2.device.UTDevice;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 莫小婷
 * @create 2019/4/10
 * @Describe 获取唯一设备ID
 */
public class DeviceUuidFactory {
    protected static final String PREFS_FILE = "device_id";

    protected static final String PREFS_DEVICE_ID = "device_id";

    protected static UUID uuid;

    private static String deviceType = "0";

    private static final String TYPE_ANDROID_ID = "1";

    private static final String TYPE_DEVICE_ID = "2";

    private static final String TYPE_RANDOM_UUID = "3";

    @SuppressLint("MissingPermission")//屏蔽android lint错误
    public DeviceUuidFactory(Context context) {
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                deviceType = TYPE_ANDROID_ID;
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                if (deviceId != null
                                        && !"0123456789abcdef".equals(deviceId.toLowerCase())
                                        && !"000000000000000".equals(deviceId.toLowerCase())) {
                                    deviceType = TYPE_DEVICE_ID;
                                    uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("utf8"));
                                } else {
                                    deviceType = TYPE_RANDOM_UUID;
                                    uuid = UUID.randomUUID();
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            deviceType = TYPE_RANDOM_UUID;
                            uuid = UUID.randomUUID();
                        } finally {
                            uuid = UUID.fromString(deviceType + uuid.toString());
                        }

                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
                    }
                }
            }
        }
    }

    public String getDeviceUuid() {
//        Log.e("moxiaoting", "------>获取的设备ID号为：" + uuid.toString());
        return uuid.toString();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取UTDID
     */
    public static Map<String,String> getIdfa(Context context) {
        Map<String,String> map = new HashMap<>();
        String idfa = UTDevice.getUtdid(context);
        if (!TextUtils.isEmpty(idfa) || !idfa.equals("ffffffffffffffffffffffff")){
            map.put("deviceType","UTDID");
            map.put("deviceValue",UTDevice.getUtdid(context));
        }
        return map;
    }

    /**
     * 获取AndroidID
     * 当设备重新初始化或者刷机的时候，会被重置
     */
    public static String getAndroidID(Context context) {
        String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }

    /**
     * 获取IMEI
     */
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return imei;
            }
            imei = telephonyManager.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

}
