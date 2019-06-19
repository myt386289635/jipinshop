package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.TryBean;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/20
 * @Describe 试用首页adapter
 */
public class TryAdapter extends RecyclerView.Adapter {

    private static final int Head = 11;
    private static final int Head2 = 12;
    private static final int Content1 = 1;
    private static final int Content2 = 2;

    private OnItemClick mOnItemClick;
    private Context mContext;
    private List<TryBean.DataBean.TrialListBean> mTrialListBeans;
    private List<TryBean.DataBean.ReportListBean> mReportListBeans;
    private List<EvaluationTabBean.DataBean.AdListBean> mAdListBeans;
    private Boolean isFlag;

    public void setAdListBeans(List<EvaluationTabBean.DataBean.AdListBean> adListBeans) {
        mAdListBeans = adListBeans;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public TryAdapter(Context context, List<TryBean.DataBean.TrialListBean> trialListBeans, List<TryBean.DataBean.ReportListBean> reportListBeans) {
        mContext = context;
        mTrialListBeans = trialListBeans;
        mReportListBeans = reportListBeans;
        this.isFlag = true;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 ){
            return Head;
        } else if (position == mTrialListBeans.size() + 1 || position == mTrialListBeans.size() + mReportListBeans.size() + 2){
            return Head2;
        } else if( 1 <= position  && position <= mTrialListBeans.size()){
            return Content1;
        } else {
            return Content2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder  = null;
        switch (viewType){
            case Head:
                View HeadView = LayoutInflater.from(mContext).inflate(R.layout.item_try_head,viewGroup,false);
                holder = new HeadViewHolder(HeadView);
                break;
            case Head2:
                View HeadView2 = LayoutInflater.from(mContext).inflate(R.layout.item_try_head2,viewGroup,false);
                holder = new HeadViewHolder2(HeadView2);
                break;
            case Content1:
                View Content1View = LayoutInflater.from(mContext).inflate(R.layout.item_try_content1,viewGroup,false);
                holder = new Content1ViewHolder(Content1View);
                break;
            case Content2:
                View Content2View = LayoutInflater.from(mContext).inflate(R.layout.item_try_content2,viewGroup,false);
                holder = new Content2ViewHolder(Content2View);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case Head://轮播图
                HeadViewHolder  headViewHolder = (HeadViewHolder) holder;
                if(mAdListBeans.size() > 1){
                    if (isFlag) {
                        headViewHolder.view_pager.setVisibility(View.VISIBLE);
                        headViewHolder.detail_point.setVisibility(View.VISIBLE);
                        headViewHolder. head_image.setVisibility(View.GONE);
                        headViewHolder.initBanner();
                        new Thread(() -> {
                            while (true) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                headViewHolder.handler.sendEmptyMessage(100);
                            }
                        }).start();
                        isFlag = false;
                    }
                }else {
                    headViewHolder.view_pager.setVisibility(View.GONE);
                    headViewHolder.detail_point.setVisibility(View.GONE);
                    headViewHolder.head_image.setVisibility(View.VISIBLE);
                    if(mAdListBeans.size() == 0 || TextUtils.isEmpty(mAdListBeans.get(0).getImg())){
                        GlideApp.loderImage(mContext,R.mipmap.try_banner,headViewHolder.head_image,0,0);
                    }else {
                        GlideApp.loderImage(mContext,mAdListBeans.get(0).getImg(),headViewHolder.head_image,0,0);
                    }
                }
                break;
            case Head2:
                HeadViewHolder2 headViewHolder2 = (HeadViewHolder2) holder;
                if (position == mTrialListBeans.size() + 1){
                    headViewHolder2.try_title.setVisibility(View.VISIBLE);
                    headViewHolder2.try_all.setOnClickListener(v -> {
                        if(mOnItemClick != null){
                            mOnItemClick.onHeadClick("免费试用");
                        }
                    });
                }else {
                    headViewHolder2.try_title.setVisibility(View.GONE);
                    headViewHolder2.try_all.setOnClickListener(v -> {
                        if(mOnItemClick != null){
                            mOnItemClick.onHeadClick("试用报告");
                        }
                    });
                }
                break;
            case Content1:
                Content1ViewHolder content1ViewHolder = (Content1ViewHolder) holder;
                int content1Position = position - 1;
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) content1ViewHolder.try_image.getLayoutParams();
//                if(position == 1){
//                    layoutParams.topMargin = 0;
//                }else {
//                    layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y24);
//                }
//                content1ViewHolder.try_image.setLayoutParams(layoutParams);
                content1ViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnItemClick != null){
                        mOnItemClick.onItemDetailClick(content1Position);
                    }
                });
                GlideApp.loderImage(mContext,mTrialListBeans.get(content1Position).getImg(),content1ViewHolder.try_image,0,0);
                content1ViewHolder.try_name.setText(mTrialListBeans.get(content1Position).getName());
                content1ViewHolder.try_num.setText(mTrialListBeans.get(content1Position).getCount() + "");
                content1ViewHolder.try_price.setText("¥" + mTrialListBeans.get(content1Position).getActualPrice());
                if(mTrialListBeans.get(content1Position).getStatus() == 2){
                    //进行中
                    content1ViewHolder.try_button.setVisibility(View.VISIBLE);
                    content1ViewHolder.try_button1.setVisibility(View.INVISIBLE);
                } else if (mTrialListBeans.get(content1Position).getStatus() == 3) {
                    //试用中
                    content1ViewHolder.try_button.setVisibility(View.INVISIBLE);
                    content1ViewHolder.try_button1.setVisibility(View.VISIBLE);
                    content1ViewHolder.try_button1.setText("试用中");
                }else {
                    //已结束
                    content1ViewHolder.try_button.setVisibility(View.INVISIBLE);
                    content1ViewHolder.try_button1.setVisibility(View.VISIBLE);
                    content1ViewHolder.try_button1.setText("已结束");
                }
                break;
            case Content2:
                Content2ViewHolder content2ViewHolder = (Content2ViewHolder) holder;
                int content2Position = position - 2 - mTrialListBeans.size();
                content2ViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnItemClick != null){
                        mOnItemClick.onItemReportClick(content2Position);
                    }
                });
                GlideApp.loderImage(mContext,mReportListBeans.get(content2Position).getImg(),content2ViewHolder.try_image,0,0);
                content2ViewHolder.try_name.setText(mReportListBeans.get(content2Position).getTitle());
                content2ViewHolder.try_nickName.setText(mReportListBeans.get(content2Position).getUser().getNickname());
                GlideApp.loderCircleImage(mContext,mReportListBeans.get(content2Position).getUser().getAvatar(),content2ViewHolder.try_head,0,0);
                content2ViewHolder.try_pv.setText(mReportListBeans.get(content2Position).getPv() + "阅读");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTrialListBeans.size() == 0  ? 0 : mTrialListBeans.size() + mReportListBeans.size() + 3;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ImageView head_image;
        private ViewPager view_pager;
        private LinearLayout detail_point;

        private HomePagerAdapter mPagerAdapter;
        private List<ImageView> point;

        private Handler.Callback mCallback = msg -> {
            if(msg.what == 100){
                if (view_pager != null) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                }
            }
            return true;
        };
        private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setTag(true);//标志是悬浮的view
            head_image = itemView.findViewById(R.id.head_image);
            view_pager = itemView.findViewById(R.id.view_pager);
            detail_point = itemView.findViewById(R.id.detail_point);

            mPagerAdapter = new HomePagerAdapter(mContext);
            point = new ArrayList<>();
            mPagerAdapter.setList(mAdListBeans);
            mPagerAdapter.setPoint(point);
            mPagerAdapter.setViewPager(view_pager);
            mPagerAdapter.setImgCenter(false);
            view_pager.setAdapter(mPagerAdapter);
        }

        public void initBanner(){
            view_pager.setCurrentItem(0);
            point.clear();
            detail_point.removeAllViews();
            for (int i = 0; i < mAdListBeans.size(); i++) {
                ImageView imageView = new ImageView(mContext);
                point.add(imageView);
                if (i == 0) {
                    imageView.setImageResource(R.drawable.banner_down);
                } else {
                    imageView.setImageResource(R.drawable.banner_up);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin =mContext.getResources().getDimensionPixelSize(R.dimen.x4);
                layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x4);
                detail_point.addView(imageView, layoutParams);
            }
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    class HeadViewHolder2 extends RecyclerView.ViewHolder{

        private TextView try_title,try_all;

        public HeadViewHolder2(@NonNull View itemView) {
            super(itemView);

            try_title = itemView.findViewById(R.id.try_title);
            try_all = itemView.findViewById(R.id.try_all);
        }
    }

    class Content1ViewHolder extends RecyclerView.ViewHolder{

        private ImageView try_image;
        private TextView try_name,try_num,try_price,try_button,try_button1;

        public Content1ViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setTag(false);//标志不是悬浮的view
            try_image = itemView.findViewById(R.id.try_image);
            try_name = itemView.findViewById(R.id.try_name);
            try_num = itemView.findViewById(R.id.try_num);
            try_price = itemView.findViewById(R.id.try_price);
            try_button = itemView.findViewById(R.id.try_button);
            try_button1 = itemView.findViewById(R.id.try_button1);
        }
    }

    class Content2ViewHolder extends RecyclerView.ViewHolder{

        private ImageView try_image,try_head;
        private TextView try_name,try_nickName,try_pv;

        public Content2ViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setTag(false);//标志不是悬浮的view
            try_image = itemView.findViewById(R.id.try_image);
            try_head = itemView.findViewById(R.id.try_head);
            try_name = itemView.findViewById(R.id.try_name);
            try_nickName = itemView.findViewById(R.id.try_nickName);
            try_pv = itemView.findViewById(R.id.try_pv);

        }
    }

    public interface OnItemClick{
        void onHeadClick(String type);
        void onItemDetailClick(int position);
        void onItemReportClick(int position);
    }
}
