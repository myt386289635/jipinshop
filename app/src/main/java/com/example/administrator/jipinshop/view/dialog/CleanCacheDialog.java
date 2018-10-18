package com.example.administrator.jipinshop.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class CleanCacheDialog extends BottomSheetDialogFragment {

    public static final String TAG = "CleanCacheDialog";

    private TextView dialog_clean;
    private TextView dialog_diss;
    private OnItemDialog mOnItemDialog;

    public void setOnItemDialog(OnItemDialog onItemDialog) {
        mOnItemDialog = onItemDialog;
    }

    public static CleanCacheDialog getInstance() {
        CleanCacheDialog fragment = new CleanCacheDialog();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_clean_cache, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
//        WindowManager.LayoutParams lp= getDialog().getWindow().getAttributes();
//        lp.dimAmount= 0.3f;
//        lp.alpha = 0.7f;
//        getDialog().getWindow().setAttributes(lp);
//        getDialog().getWindow().findViewById(R.id.touch_outside).setBackgroundColor(getResources().getColor(R.color.color_4D151515));
    }

    private void initView(View view) {
        dialog_clean = view.findViewById(R.id.dialog_clean);
        dialog_diss = view.findViewById(R.id.dialog_diss);

        dialog_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemDialog != null){
                    mOnItemDialog.onItemDialog(v);
                }
                dismiss();
            }
        });
    }

    public interface OnItemDialog{
        void  onItemDialog(View view);
    }

//    @Override
//    public int getTheme() {
//        return R.style.BottomSheetDialogStyle;
//    }
}
