package com.example.administrator.jipinshop.fragment.tryout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;

/**
 * @author 莫小婷
 * @create 2018/12/26
 * @Describe 试用模块
 */
public class TryFragment extends DBBaseFragment{

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.try_fragment,container,false);
        return view;
    }

    @Override
    public void initView() {

    }
}
