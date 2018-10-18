package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.TextViewDel;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;

    public ScoreAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_score,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (int i = 0; i < 4; i++) {
            View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
            TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            if (i == 0) {
                textView.setText("榨汁");
            } else if (i == 1) {
                textView.setText("料理机");
            } else if (i == 2) {
                textView.setText("智能电动");
            } else {
                textView.setText("便携");
            }
            holder.item_tags.addView(itemTypeView);
        }
        holder.item_priceOld.setColor(R.color.color_ACACAC);
        holder.item_priceOld.setTv(true);
        ImageManager.displayRoundImage(MyApplication.imag,holder.item_image,0,0,10);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private FlexboxLayout item_tags;
        private TextView item_price;
        private TextViewDel item_priceOld;
        private TextView item_lookNum;
        private TextView item_scoreMoney;
        private TextView item_scoreNum;

        public ViewHolder(View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_tags = itemView.findViewById(R.id.item_tags);
            item_price = itemView.findViewById(R.id.item_price);
            item_priceOld = itemView.findViewById(R.id.item_priceOld);
            item_lookNum = itemView.findViewById(R.id.item_lookNum);
            item_scoreMoney = itemView.findViewById(R.id.item_scoreMoney);
            item_scoreNum = itemView.findViewById(R.id.item_scoreNum);

        }
    }
}
