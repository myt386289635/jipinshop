package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.evaluation.EvaluationDetailActivity;
import com.example.administrator.jipinshop.activity.home.find.FindDetailActivity;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class UserAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;
    private final int EVA = 3;

    private List<UserPageBean.ListBean> mList;
    private Context mContext;
    private OnListener mOnListener;
    private String headImage = "";
    private String name = "";
    private int fans = 0;
    private int isfans= 0;

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFans() {
        return fans;
    }

    public void setIsfans(int isfans) {
        this.isfans = isfans;
    }

    public void setOnListener(OnListener onListener) {
        mOnListener = onListener;
    }

    public UserAdapter(List<UserPageBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD;
        } else if(mList.get(position - 1).getSmallTitle().equals("0")){
            return EVA;
        } else {
            return CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       RecyclerView.ViewHolder holder = null;
       switch (viewType){
           case HEAD:
               View headView = LayoutInflater.from(mContext).inflate(R.layout.item_user_head,parent,false);
               holder = new HeadViewHolder(headView);
               break;
           case CONTENT:
               View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_user_find,parent,false);
               holder = new FindViewHolder(contentView);
               break;
           case EVA:
               View evaView = LayoutInflater.from(mContext).inflate(R.layout.item_user_content,parent,false);
               holder = new EvaViewHolder(evaView);
               break;

       }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headViewHolder.setStatusBarHight(mContext);
                headViewHolder.title_back.setOnClickListener(v -> {
                     if(mOnListener != null){
                         mOnListener.onFinish();
                     }
                });
                GlideApp.loderCircleImage(mContext,headImage,headViewHolder.user_headImage,0,0);
                GlideApp.loderBlurImage(mContext,headImage,headViewHolder.user_image);

                headViewHolder.user_name.setText(name);
                headViewHolder.user_attentionNum.setText("粉丝数:" + fans);
                if(isfans == 0){
                    headViewHolder.item_attention.setBackgroundResource(R.drawable.bg_my_attentioned);
                    headViewHolder.item_attention.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
                    headViewHolder.item_attention.setText("+关注");
                }else {
                    headViewHolder.item_attention.setBackgroundResource(R.drawable.bg_my_attention);
                    headViewHolder.item_attention.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
                    headViewHolder.item_attention.setText("已关注");
                }
                headViewHolder.item_attention.setOnClickListener(v -> {
                    if(isfans == 0){
                        //关注
                        if(mOnListener != null){
                            mOnListener.onAttenInsItem(1);
                        }
                    }else {
                        //取消关注
                        if(mOnListener != null){
                            mOnListener.onAttenDecItem(1);
                        }
                    }
                });
                break;
            case EVA://评测
                EvaViewHolder contentViewHolder = (EvaViewHolder) holder;
                GlideApp.loderRoundImage(mContext,mList.get(position -1).getImg(),contentViewHolder.content_image);
//                ImageManager.displayRoundImage(mList.get(position -1).getImg(),contentViewHolder.content_image,0,0,10);
                contentViewHolder.content_lookNum.setText(mList.get(position -1).getVisitCount());
                if(TextUtils.isEmpty(mList.get(position - 1).getShowTime())){
                    contentViewHolder.content_time.setText(mList.get(position - 1).getPublishTime());
                }else {
                    contentViewHolder.content_time.setText(mList.get(position - 1).getShowTime());
                }
                contentViewHolder.content_commentNum.setText(mList.get(position - 1).getCommentNum());
                contentViewHolder.content_title.setText(mList.get(position - 1).getTitle());
                contentViewHolder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, EvaluationDetailActivity.class)
                            .putExtra("id",mList.get(position - 1).getArticleId())
                    );
                });
                break;
            case CONTENT://发现
                FindViewHolder findViewHolder = (FindViewHolder) holder;
                GlideApp.loderRoundImage(mContext,mList.get(position -1).getImg(),findViewHolder.mItemImage);
//                ImageManager.displayRoundImage(mList.get(position -1).getImg(),findViewHolder.mItemImage,0,0,10);
                findViewHolder.mItemName.setText(mList.get(position - 1).getTitle());
                findViewHolder.mItemDescription.setText(mList.get(position -1).getSmallTitle());
                findViewHolder.mItemLookNum.setText(mList.get(position -1).getVisitCount());
                if(TextUtils.isEmpty(mList.get(position - 1).getShowTime())){
                    findViewHolder.mItemTime.setText(mList.get(position - 1).getPublishTime());
                }else {
                    findViewHolder.mItemTime.setText(mList.get(position - 1).getShowTime());
                }
                findViewHolder.itemView.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, FindDetailActivity.class)
                            .putExtra("id",mList.get(position - 1).getArticleId())
                    );
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 1: mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ImageView user_image;
        private LinearLayout status_bar;
        private ImageView title_back;
        private ImageView user_headImage;
        private TextView user_name;
        private TextView user_attentionNum;
        private TextView item_attention;

        public HeadViewHolder(View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            status_bar = itemView.findViewById(R.id.status_bar);
            title_back = itemView.findViewById(R.id.title_back);
            user_headImage = itemView.findViewById(R.id.user_headImage);
            user_name = itemView.findViewById(R.id.user_name);
            user_attentionNum = itemView.findViewById(R.id.user_attentionNum);
            item_attention = itemView.findViewById(R.id.item_attention);
        }

        public void setStatusBarHight(Context context){
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
                ViewGroup.LayoutParams layoutParams = status_bar.getLayoutParams();
                layoutParams.height = statusBarHeight;
            }
        }
    }

    class EvaViewHolder extends RecyclerView.ViewHolder{

        private ImageView content_image ;
        private TextView content_time;
        private TextView content_commentNum;
        private TextView content_lookNum;
        private TextView content_title;


        public EvaViewHolder(View itemView) {
            super(itemView);
            content_image = itemView.findViewById(R.id.content_image);
            content_time = itemView.findViewById(R.id.content_time);
            content_commentNum = itemView.findViewById(R.id.content_commentNum);
            content_lookNum = itemView.findViewById(R.id.content_lookNum);
            content_title = itemView.findViewById(R.id.content_title);
        }
    }

    class FindViewHolder extends RecyclerView.ViewHolder{
        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemDescription;
        private TextView mItemTime;
        private TextView mItemLookNum;
        public FindViewHolder(View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_image);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemDescription = itemView.findViewById(R.id.item_description);
            mItemTime = itemView.findViewById(R.id.item_time);
            mItemLookNum = itemView.findViewById(R.id.item_lookNum);
        }
    }

    public interface OnListener{
        void onFinish();

        void onAttenInsItem(int pos);
        void onAttenDecItem(int pos);

    }
}
