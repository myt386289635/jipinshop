package com.example.administrator.jipinshop.view.dialog.city;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.CityUtils;


/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe
 */
public class CityPickerDialog extends BaseDialog {
    private TextView tv_cancel;
    private TextView tv_sure;
    private TextView tv_title;
    private WheelView province_wheel;
    private WheelView       city_wheel;
    private WheelView       county_wheel;
    private OnClickCallback callback;
    /*省*/
    private int             p;
    /*市*/
    private int             c;
    /*县*/
    private int             x;
    /*省*/
    private String province;
    /*市*/
    private String city;
    /*县*/
    private String county;

    public CityPickerDialog(Context context) {
        super(context);
        final CityUtils cityUtils = new CityUtils(mContext);
        this.dialog = new Dialog(mContext, R.style.picker_dialog);
        dialog.setContentView(R.layout.dialog_city_picker);
        tv_cancel = (TextView) dialog.findViewById(R.id.cancel);
        tv_sure = (TextView) dialog.findViewById(R.id.ok);
        tv_title = (TextView) dialog.findViewById(R.id.title);
        tv_title.setText("请选择城市");
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        province_wheel = (WheelView) dialog.findViewById(R.id.wheel_p);
        city_wheel = (WheelView) dialog.findViewById(R.id.wheel_c);
        county_wheel = (WheelView) dialog.findViewById(R.id.wheel_x);
        //设置默认值
        province = cityUtils.createProvince().get(p);
        city = cityUtils.createCity().get(c);
        county = cityUtils.createdX().get(x);

        province_wheel.setData(cityUtils.createProvince());
        city_wheel.setData(cityUtils.createCity());
        county_wheel.setData(cityUtils.createdX());
        province_wheel.setOnSelectListener((index, text) -> {
            p = index;
            province = text;

            c=0;
            city_wheel.setWheelItemList(cityUtils.createCity(p));
            county_wheel.setWheelItemList(cityUtils.createdX(p, c));
            city = cityUtils.createCity(p).get(0);
            county = cityUtils.createdX(p, c).get(0);

            city_wheel.reset();
            county_wheel.reset();
        });
        city_wheel.setOnSelectListener((index, text) -> {
            c = index;
            city = text;

            county_wheel.setWheelItemList(cityUtils.createdX(p, c));
            county = cityUtils.createdX(p, c).get(0);

            county_wheel.reset();
        });
        county_wheel.setOnSelectListener((index, text) -> {
            x = index;
            county = text;
        });

        setDialogLocation(mContext, dialog);
    }


    public void setCallback(OnClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.cancel:
                if (callback != null) {
                    callback.onCancel();
                }
                break;
            case R.id.ok:
                if (callback != null) {
                    callback.onSure(province, city, county, ">>");
                }
                break;
        }
    }

    public interface OnClickCallback {
        void onCancel();

        /**
         * 返回的数据
         *
         * @param province 省
         * @param city     市
         * @param county   县
         * @param data     包含省市县的数字拼接字符
         */
        void onSure(String province, String city, String county, String data);
    }
}
