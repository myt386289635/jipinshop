package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.BlurTransformation;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;



/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class UserAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;

    private List<String> mList;
    private Context mContext;
    private OnListener mOnListener;

    public void setOnListener(OnListener onListener) {
        mOnListener = onListener;
    }

    public UserAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD;
        }else {
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
               View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_user_content,parent,false);
               holder = new ContentViewHolder(contentView);
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
                headViewHolder.title_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if(mOnListener != null){
                             mOnListener.onFinish();
                         }
                    }
                });
                ImageManager.displayCircleImage(MyApplication.imag,headViewHolder.user_headImage,0,0);
//                ImageManager.displayImage(MyApplication.imag,headViewHolder.user_image,0,0);
                Glide.with(mContext)
                        .load(MyApplication.imag)
                        .crossFade(500)
                        .bitmapTransform(new BlurTransformation(mContext,5,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(headViewHolder.user_image);
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                ImageManager.displayRoundImage(MyApplication.imag,contentViewHolder.content_image,0,0,10);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10 + 1;
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

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ImageView content_image ;
        private TextView content_time;
        private TextView content_commentNum;
        private TextView content_lookNum;


        public ContentViewHolder(View itemView) {
            super(itemView);
            content_image = itemView.findViewById(R.id.content_image);
            content_time = itemView.findViewById(R.id.content_time);
            content_commentNum = itemView.findViewById(R.id.content_commentNum);
            content_lookNum = itemView.findViewById(R.id.content_lookNum);
        }
    }

    public interface OnListener{
        void onFinish();
    }
}
