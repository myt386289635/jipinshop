package com.example.administrator.jipinshop.util;

import android.content.Context;

import com.example.administrator.jipinshop.bean.CityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 城市数据提供公工具类
 */
public class CityUtils {
    private Context mContext;
    private List<CityBean> mList;
    /**
     * 默认的城市
     */
    private List<String> defCity  = new ArrayList<>();
    /**
     * 城市集合
     */
    private List<String> cityList = new ArrayList<>();
    /**
     * 默认的县 区
     */
    private List<String> defX     = new ArrayList<>();
    /**
     * 县 区集合
     */
    private List<String> xList    = new ArrayList<>();

    public CityUtils(Context mContext) {
        this.mContext = mContext;
        String json = init(this.mContext);
        mList = parseProvince(json);
//        cityList.add("");
//        defCity.add("");
        defCity.add("东城区");
        defCity.add("西城区");
        defCity.add("朝阳区");
        defCity.add("丰台区");
        defCity.add("石景山区");
        defCity.add("海淀区");
        defCity.add("门头沟区");
        defCity.add("房山区");
        defCity.add("通州区");
        defCity.add("顺义区");
        defCity.add("昌平区");
        defCity.add("大兴区");
        defCity.add("怀柔区");
        defCity.add("平谷区");
        defCity.add("密云县");
        defCity.add("延庆县");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
        defX.add("");
//        defX.add("全市地区");
//        defX.add("长安区");
//        defX.add("桥东区");
//        defX.add("桥西区");
//        defX.add("新华区");
//        defX.add("井陉矿区");
//        defX.add("井陉县");
//        defX.add("正定县");
//        defX.add("栾城县");
//        defX.add("行唐县");
//        defX.add("灵寿县");
//        defX.add("高邑县");
//        defX.add("深泽县");
//        defX.add("赞皇县");
//        defX.add("无极县");
//        defX.add("平山县");
//        defX.add("元氏县");
//        defX.add("辛集市");
//        defX.add("藁城市");
//        defX.add("晋州市");
//        defX.add("新乐市");
//        defX.add("鹿泉市");
//        defX.add("石家庄市");
//        defX.add("平泉县");
//        defX.add("滦平县");
//        defX.add("隆化县");
//        defX.add("丰宁满族自治县");
//        defX.add("宽城满族自治县");
//        defX.add("围场满族蒙古族自治县");
    }

    /**
     * 从assets中获取数据
     *
     * @param context
     * @return
     */
    private String init(Context context) {
        InputStream inputStream;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = context.getResources().getAssets().open("city.json");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bfr = new BufferedReader(isr);
            String in;
            while ((in = bfr.readLine()) != null) {
                stringBuffer.append(in);
            }
            inputStream.close();
            isr.close();
            bfr.close();
            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<CityBean> parseProvince(String json) {
        if (!json.equals("") && !"".equals(json)) {
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<List<CityBean>>() {
            }.getType());
        } else {
            return null;
        }
    }

    /**
     * 创建省份数据
     */
    public List<String> createProvince() {
        List<String> list = new ArrayList<>();
        for (CityBean bean : mList) {
            list.add(bean.getName());
        }
        return list;
    }

    public List<String> createCity() {
        return defCity;
    }

    /**
     * 根据省份编号获取城市
     *
     * @param pIndex
     * @return
     */
    public List<String> createCity(int pIndex) {
        cityList.clear();
        for (int i = 0; i < mList.size(); i++) {
            if (i == pIndex) {
                for (CityBean bean : mList.get(i).getCities()) {
                    cityList.add(bean.getName());
                }
            }
        }
        return cityList;
    }

    /**
     * 根据省份和城市编号获取县 或者区
     *
     * @param pIndex
     * @param cIndex
     * @return
     */
    public List<String> createdX(int pIndex, int cIndex) {

        List<CityBean> city_list = mList.get(pIndex).getCities();
        for (int i = 0; i < city_list.size(); i++) {
            if (i == cIndex && city_list.get(i).getCities().size() != 0) {
                xList.clear();
                for (int j = 0; j < city_list.get(i).getCities().size(); j++) {
                    xList.add(city_list.get(i).getCities().get(j).getName());
                }
                return xList;
            }
        }
        return defX;
    }

    public List<String> createdX() {
        return defX;
    }
}
