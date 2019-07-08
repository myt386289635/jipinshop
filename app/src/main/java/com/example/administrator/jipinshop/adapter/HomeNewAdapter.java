package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TitleBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.databinding.ItemHomeHead1Binding;
import com.example.administrator.jipinshop.databinding.ItemHomeHead2Binding;
import com.example.administrator.jipinshop.databinding.ItemHomeHead3Binding;
import com.example.administrator.jipinshop.fragment.home.HomeNewPresenter;
import com.example.administrator.jipinshop.fragment.home.commen.tab.HomeCommenTabFragment;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/28
 * @Describe 榜单首页adapter
 */
public class HomeNewAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int HEADIMAGE = 2;
    private static final int CONTENT = 3;

    private List<TitleBean> mTabBeans;
    private List<TopCategorysListBean.DataBean> mList;
    private Context mContext;
    private HomePageAdapter mPagerAdapter;
    private HomeNewPresenter mPresenter;
    private TabBean tabBean;
    private int set = 0;//记录已经选中的位置
    private OnClickItem mOnClickItem;
    private String newShopImage = "";

    public void setNewShopImage(String newShopImage) {
        this.newShopImage = newShopImage;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public void setTabBean(TabBean tabBean) {
        this.tabBean = tabBean;
    }

    public void setPresenter(HomeNewPresenter presenter) {
        mPresenter = presenter;
    }

    public void setPagerAdapter(HomePageAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
    }

    public HomeNewAdapter(Context context) {
        mContext = context;
    }

    public void setTabBeans(List<TitleBean> tabBeans) {
        mTabBeans = tabBeans;
    }

    public void setList(List<TopCategorysListBean.DataBean> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD;
        }else if (position == 1){
            return HEADIMAGE;
        }else {
            return CONTENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i){
            case HEAD:
                ItemHomeHead1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_home_head1,viewGroup,false);
                holder = new Head1ViewHolder(binding);
                break;
            case HEADIMAGE:
                ItemHomeHead2Binding homeHead2Binding = DataBindingUtil.inflate( LayoutInflater.from(mContext),R.layout.item_home_head2,viewGroup,false);
                holder = new Head2ViewHolder(homeHead2Binding);
                break;
            case CONTENT:
                ItemHomeHead3Binding head3Binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_home_head3,viewGroup,false);
                holder = new ContentViewHolder(head3Binding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case HEAD:
                Head1ViewHolder head1ViewHolder = (Head1ViewHolder) viewHolder;
                head1ViewHolder.initViewPager(set);
                head1ViewHolder.mTabAdapter.notifyDataSetChanged();
                break;
            case HEADIMAGE:
                Head2ViewHolder head2ViewHolder = (Head2ViewHolder) viewHolder;
                head2ViewHolder.itemView.setOnClickListener(v -> {
                    if (mOnClickItem != null){
                        mOnClickItem.onClickNewShop();
                    }
                });
                GlideApp.loderRoundImage(mContext,newShopImage,head2ViewHolder.homeHead2Binding.homeImage);
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
                int pos = position - 2;
                contentViewHolder.itemView.setOnClickListener(v -> {
                    if (mOnClickItem != null){
                        mOnClickItem.onClickNewDetail(pos);
                    }
                });
                GlideApp.loderRoundImage(mContext,mList.get(pos).getTopImg(),contentViewHolder.head3Binding.homeImage);
                contentViewHolder.head3Binding.homeText.setText(mList.get(pos).getTopTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() != 0 && mTabBeans.size() != 0){
            return mList.size() + 2;
        }else if (mTabBeans.size() != 0){
            return  1;
        }else {
            return 0;
        }
    }

    class Head1ViewHolder extends RecyclerView.ViewHolder implements HomeTabAdapter.OnClickItem, ViewPager.OnPageChangeListener {

        private LinearLayoutManager mLinearLayoutManager;
        private ItemHomeHead1Binding mBinding;
        private HomeTabAdapter mTabAdapter;

        private List<Fragment> tabFragments;
        private List<ImageView> point;

        public Head1ViewHolder(@NonNull  ItemHomeHead1Binding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL,false);
            mBinding.recyclerView.setLayoutManager(mLinearLayoutManager);
            mTabAdapter = new HomeTabAdapter(mTabBeans,mContext);
            mTabAdapter.setOnClickItem(this);
            mBinding.recyclerView.setAdapter(mTabAdapter);

            tabFragments = new ArrayList<>();
            point = new ArrayList<>();
            mPagerAdapter.setFragments(tabFragments);
            mBinding.itemViewpager.setAdapter(mPagerAdapter);
            mBinding.itemViewpager.addOnPageChangeListener(this);
        }

        @Override
        public void onClickItem(int pos) {
            final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            int des = 0;
            if ((pos - 1) - firstPosition > 0) {
                ////从左边到点击到右边
                des = mBinding.recyclerView.getChildAt((pos - 1) - firstPosition).getLeft();
                mBinding.recyclerView.smoothScrollBy(des, 0);
            } else if (pos - 1 >= 0 && (pos - 1) - firstPosition <= 0) {
                //从右边点击到左边
                mBinding.recyclerView.smoothScrollToPosition(pos - 1);
            }
            mTabBeans.get(set).setTag(false);
            mTabBeans.get(pos).setTag(true);
            mTabAdapter.notifyDataSetChanged();
            set = pos;
            initViewPager(pos);
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < point.size(); i++) {
                if (i == position % tabFragments.size()) {
                    point.get(i).setImageResource(R.drawable.banner_down2);
                } else {
                    point.get(i).setImageResource(R.drawable.banner_up2);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }

        /**
         * 初始化二级菜单
         */
        public void initViewPager(int pos) {
            if (tabBean != null && tabBean.getData().get(pos).getChildren().size() != 0) {
                tabFragments.clear();//清空数据
                if (tabBean.getData().get(pos).getChildren().size() <= 10) {
                    tabFragments.add(HomeCommenTabFragment.getInstance(0, pos));
                    mBinding.point.setVisibility(View.GONE);
                } else {
                    mBinding.point.setVisibility(View.VISIBLE);
                    for (int i = 0; i < tabBean.getData().get(pos).getChildren().size(); i = i + 10) {
                        tabFragments.add(HomeCommenTabFragment.getInstance(i / 10, pos));
                    }
                    mPresenter.initBanner(tabFragments, mContext, point, mBinding.point);
                }
                mPagerAdapter.updateData(tabFragments);
            } else if (tabBean != null && tabBean.getData().get(pos).getChildren().size() == 0) {
                tabFragments.clear();//清空数据
                mPagerAdapter.updateData(tabFragments);
            }
            mBinding.itemViewpager.resetHeight(pos);
        }
    }

    class Head2ViewHolder extends RecyclerView.ViewHolder{

        private ItemHomeHead2Binding homeHead2Binding;

        public Head2ViewHolder(@NonNull ItemHomeHead2Binding homeHead2Binding) {
            super(homeHead2Binding.getRoot());
            this.homeHead2Binding = homeHead2Binding;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemHomeHead3Binding head3Binding;

        public ContentViewHolder(@NonNull ItemHomeHead3Binding head3Binding) {
            super(head3Binding.getRoot());
            this.head3Binding = head3Binding;
        }
    }

    public interface OnClickItem{
        void onClickNewShop();
        void onClickNewDetail(int position);
    }
}
