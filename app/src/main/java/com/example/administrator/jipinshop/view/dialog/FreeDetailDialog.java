package com.example.administrator.jipinshop.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.util.sp.CommonDate;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe
 */
public class FreeDetailDialog  extends BottomSheetDialogFragment {

    private TextView dialog_title,dialog_content1,dialog_content2,dialog_cancle,dialog_sure,dialog_goto;
    private LinearLayout dialog_bottomContainer;
    private RelativeLayout dialog_container;
    private ImageView dialog_diss;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public static FreeDetailDialog getInstance(String point,int type) {
        FreeDetailDialog  fragment = new FreeDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putString("point",point);
        bundle.putInt("type",type);//代表极币是否足够
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_free_detail,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_content1 = view.findViewById(R.id.dialog_content1);
        dialog_content2 = view.findViewById(R.id.dialog_content2);
        dialog_cancle = view.findViewById(R.id.dialog_cancle);
        dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog_goto = view.findViewById(R.id.dialog_goto);
        dialog_bottomContainer = view.findViewById(R.id.dialog_bottomContainer);
        dialog_container = view.findViewById(R.id.dialog_container);
        dialog_diss = view.findViewById(R.id.dialog_diss);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dialog_title.getLayoutParams();
        if (getArguments().getInt("type",-1) == 1){
            //足够
            dialog_container.setBackgroundResource(R.mipmap.dialog_free1);
            layoutParams.topMargin = (int) getContext().getResources().getDimension(R.dimen.y86);
            layoutParams.bottomMargin = (int) getContext().getResources().getDimension(R.dimen.y60);
            dialog_title.setText("确认参加将支付"+getArguments().getString("point","0")+"极币，不支持退币哦，祝您好运！");
            dialog_content1.setVisibility(View.GONE);
            dialog_content2.setVisibility(View.GONE);
            dialog_goto.setVisibility(View.GONE);
            dialog_diss.setVisibility(View.GONE);
            dialog_bottomContainer.setVisibility(View.VISIBLE);
        }else {
            //不足够
            dialog_container.setBackgroundResource(R.mipmap.dialog_free2);
            layoutParams.topMargin = (int) getContext().getResources().getDimension(R.dimen.y142);
            layoutParams.bottomMargin = (int) getContext().getResources().getDimension(R.dimen.y38);

            dialog_title.setText("极币数不足，请前往“每日任务”赚取极币");
            dialog_content1.setText("现有极币："+ SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0)+"个");
            dialog_content2.setText("所需极币："+getArguments().getString("point","0")+"个");
            dialog_content1.setVisibility(View.VISIBLE);
            dialog_content2.setVisibility(View.VISIBLE);
            dialog_goto.setVisibility(View.VISIBLE);
            dialog_diss.setVisibility(View.VISIBLE);
            dialog_bottomContainer.setVisibility(View.GONE);
        }
        dialog_title.setLayoutParams(layoutParams);

        dialog_cancle.setOnClickListener(v -> dismiss());
        dialog_goto.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SignActivity.class));
            dismiss();
        });
        dialog_diss.setOnClickListener(v -> dismiss());
        dialog_sure.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItemClick();
            }
            dismiss();
        });
    }

    public interface OnItem{
        void onItemClick();
    }
}
