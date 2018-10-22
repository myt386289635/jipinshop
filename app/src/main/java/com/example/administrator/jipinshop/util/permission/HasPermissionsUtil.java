package com.example.administrator.jipinshop.util.permission;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.RuntimeRationale;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/20
 * @Describe 请求权限工具
 */
public class HasPermissionsUtil implements HasPermissmionsListener{

    public static void permission(Context context,HasPermissionsUtil hasPermissionsUtil,String... permissions){
        if (AndPermission.hasPermissions(context, permissions)) {
            //有权限了
            hasPermissionsUtil.hasPermissionsSuccess();
        } else {
            requestPermission(context,hasPermissionsUtil,permissions);
        }
    }

    private static void requestPermission(Context context,HasPermissionsUtil hasPermissionsUtil,String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(permissions1 -> {
//                        Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
                    hasPermissionsUtil.hasPermissionsSuccess();
                })
                .onDenied(permissions12 -> {
//                        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions12)) {
                        showSettingDialog(context,hasPermissionsUtil, permissions12);
                    }else {
                        hasPermissionsUtil.hasPermissionsFaile();
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     * 这个是用户勾了再也不要提示后，请求权限失败调用该方法
     */
    private static void showSettingDialog(Context context,HasPermissionsUtil hasPermissionsUtil, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = "点击设置打开" + permissionNames.get(0) + "权限";
        String title = "该操作需要访问您的" + permissionNames.get(0) + "权限";
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> setPermission(context,hasPermissionsUtil))
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    hasPermissionsUtil.rePermissionsFaile();
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private static void setPermission(Context context,HasPermissionsUtil hasPermissionsUtil) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .onComeback(() -> {
//                        Toast.makeText(getContext(), R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    hasPermissionsUtil.settingPermissions();
                })
                .start();
    }

    @Override
    public void hasPermissionsSuccess() {

    }

    @Override
    public void hasPermissionsFaile() {

    }

    @Override
    public void rePermissionsFaile() {

    }

    @Override
    public void settingPermissions() {

    }

}
