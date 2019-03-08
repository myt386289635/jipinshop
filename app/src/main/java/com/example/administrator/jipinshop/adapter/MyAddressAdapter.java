package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.AddressBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe
 */
public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder>{

    private List<AddressBean.DataBean> mList;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public MyAddressAdapter(List<AddressBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_name.setText(mList.get(position).getUsername());
        holder.item_number.setText(mList.get(position).getMobile());
        holder.item_address.setText(mList.get(position).getArea().replace("-","") + mList.get(position).getAddress());
        if(TextUtils.isEmpty(mList.get(position).getStatus()) || mList.get(position).getStatus().equals("0")){
            //不是默认地址
            holder.item_unsel.setImageResource(R.mipmap.address_unsel);
            holder.item_addressText.setText("设为默认地址");
            holder.item_addressText.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
        }else {
            //是默认
            holder.item_unsel.setImageResource(R.mipmap.address_sel);
            holder.item_addressText.setText("已设为默认地址");
            holder.item_addressText.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
        }

        holder.item_delete.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onItemDelete(position);
            }
        });
        holder.item_edit.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onItemEdit(position);
            }
        });
        holder.item_addressText.setOnClickListener(v -> {
            if (mOnClickItem != null){
                mOnClickItem.onItemDefault(position);
            }
        });
        holder.item_unsel.setOnClickListener(v -> {
            if (mOnClickItem != null){
                mOnClickItem.onItemDefault(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_name;
        private TextView item_number;
        private ImageView item_delete;
        private TextView item_address;
        private ImageView item_unsel;
        private TextView item_addressText;
        private TextView item_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_number = itemView.findViewById(R.id.item_number);
            item_delete = itemView.findViewById(R.id.item_delete);
            item_address = itemView.findViewById(R.id.item_address);
            item_unsel = itemView.findViewById(R.id.item_unsel);
            item_addressText = itemView.findViewById(R.id.item_addressText);
            item_edit = itemView.findViewById(R.id.item_edit);
        }
    }

    public interface OnClickItem{
        void onItemEdit(int position);//编辑地址
        void onItemDelete(int position);//删除地址
        void onItemDefault(int position);//设置默认地址
    }
}
