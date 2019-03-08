package com.example.administrator.jipinshop.view.dialog.city;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 选择器弹窗基类
 */
public class BaseDialog implements View.OnClickListener {
    protected Dialog dialog;
    protected Context mContext;

    protected BaseDialog(Context context) {
        this.mContext = context;
//        this.dialog = new Dialog(mContext, R.style.picker_dialog);
    }


    protected void setDialogLocation(Context context, Dialog dialog) {
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams lp      = window.getAttributes();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        lp.x = 0;
        lp.y = manager.getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(lp);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}

