package com.example.administrator.jipinshop.fragment.tryout.freemodel;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.TryFragmentBinding;

/**
 * @author 莫小婷
 * @create 2019/6/18
 * @Describe 免单首页
 */
public class FreeFragment extends DBBaseFragment{

    private TryFragmentBinding mBinding;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.try_fragment,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {

    }


}
