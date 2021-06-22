package com.example.administrator.jipinshop.activity.address.add;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.bean.CitysBean;
import com.example.administrator.jipinshop.databinding.ActivityCreateaddressBinding;
import com.example.administrator.jipinshop.util.PickerUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe 新增地址/修改地址
 */
public class CreateAddressActivity extends BaseActivity implements View.OnClickListener, CreateAddressView {

    private ActivityCreateaddressBinding mBinding;
    private AddressBean.DataBean mBean;
    private Dialog mDialog;
    private String cityTxt;
    private PickerUtil mCityPickerUtil; //地址选择器

    @Inject
    CreateAddressPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_createaddress);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra("title") + "");
        mBean = (AddressBean.DataBean) getIntent().getSerializableExtra("date");

        if(mBean != null){
            mBinding.editName.setText(mBean.getUsername());
            mBinding.editNumber.setText(mBean.getMobile());
            cityTxt = mBean.getArea();
            mBinding.editArea.setText(cityTxt.replace("-"," "));
            mBinding.editAddress.setText(mBean.getAddress());
        }

        showKeyboardDelayed(mBinding.editName);
        mBinding.editName.setSelection(mBinding.editName.getText().length());

        onEditCity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_add:
                //保存
                if (TextUtils.isEmpty(mBinding.editName.getText().toString().trim())){
                    ToastUtil.show("收件人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.editNumber.getText().toString().trim())){
                    ToastUtil.show("联系方式不能为空");
                    return;
                }
                if (mBinding.editNumber.getText().toString().trim().length() < 11){
                    ToastUtil.show("联系方式不正确，请检查");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.editAddress.getText().toString().trim())){
                    ToastUtil.show("详细地址不能为空");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                mDialog.show();
                if(mBean != null){
                    //修改地址
                    mPresenter.addressUpdate(mBean.getId(),mBinding.editName.getText().toString(),
                            mBinding.editNumber.getText().toString(),
                            mBinding.editAddress.getText().toString(),
                            cityTxt,this.bindToLifecycle());
                }else {
                    //新增地址
                    mPresenter.addressAdd(mBinding.editName.getText().toString(),
                            mBinding.editNumber.getText().toString(),
                            mBinding.editAddress.getText().toString(),
                            cityTxt,this.bindToLifecycle());
                }
                break;
            case R.id.areaContainer:
                //选择收货地区
                mCityPickerUtil.showTextPiker();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideSoftInput() {
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    public void onSuccess() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        setResult(201);
        finish();
        ToastUtil.show("成功");
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 选择城市
     */
    public void onEditCity() {
        //初始化数据
        String json = init();
        List<CitysBean> cityBeans = parseProvince(json);//所有的省
        List<List<String>> options2Items = new ArrayList<>();//所有的市
        List<List<List<String>>> options3Items = new ArrayList<>();//所有的区
        initDate(cityBeans,options2Items, options3Items);
        mCityPickerUtil = new PickerUtil();
        mCityPickerUtil.initAddress(this, cityBeans, options2Items, options3Items, date -> {
            cityTxt = date;
            mBinding.editArea.setText(cityTxt.replace("-"," "));
        });
    }

    //从assets中获取数据
    private String init() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("province.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //json解析
    private List<CitysBean> parseProvince(String json) {
        List<CitysBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(json);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CitysBean entity = gson.fromJson(data.optJSONObject(i).toString(), CitysBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    //获取其他数据
    private void initDate(List<CitysBean> jsonBean , List<List<String>> options2Items, List<List<List<String>>> options3Items){
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            options2Items.add(CityList);
            //添加地区数据
            options3Items.add(Province_AreaList);
        }
    }
}
