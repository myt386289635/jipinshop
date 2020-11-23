package com.example.administrator.jipinshop.fragment.index;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.IndexPicBinding;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/4/17
 * @Describe 引导页图片
 */
public class IndexPicFragment extends DBBaseFragment implements View.OnClickListener {

    @Inject
    AppStatisticalUtil appStatisticalUtil;
    private IndexPicBinding mBinding;

    public static IndexPicFragment getInstence(){
        IndexPicFragment fragment = new IndexPicFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.index_pic,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.index_video:
                //查看视频
                appStatisticalUtil.addEvent("yindao1_shipin",this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
                startActivity(new Intent(getContext(), HomeNewActivity.class)
                        .putExtra("type", HomeNewActivity.indexVideo)
                );
                break;
            case R.id.index_main:
                //进入首页
                appStatisticalUtil.addEvent("yindao1_shouye",this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
                startActivity(new Intent(getContext(), MainActivity.class));
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
        }
    }
}
