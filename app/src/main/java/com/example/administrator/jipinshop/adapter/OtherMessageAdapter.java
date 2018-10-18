package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class OtherMessageAdapter extends RecyclerView.Adapter<OtherMessageAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;

    public OtherMessageAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.item_other,parent,false);
       ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageManager.displayRoundImage(MyApplication.imag,holder.item_image,0,0,10);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private RelativeLayout item_container;
        private ImageView item_image;
        private TextView item_name;
        private TextView item_source;
        private TextView item_time;

        public ViewHolder(View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_image =  itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_source = itemView.findViewById(R.id.item_source);
            item_time = itemView.findViewById(R.id.item_time);
        }
    }

}
