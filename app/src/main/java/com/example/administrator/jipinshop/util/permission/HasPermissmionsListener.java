package com.example.administrator.jipinshop.util.permission;

/**
 * @author 莫小婷
 * @create 2018/10/20
 * @Describe
 */
public interface HasPermissmionsListener {

    //获取成功调用
    public  void hasPermissionsSuccess();

    //获取失败（没有勾选提示）调用
    public  void hasPermissionsFaile();

    //勾选了“不再提示”后拒绝调用
    public  void rePermissionsFaile();

    //勾选了“不再提示”后去设置返回调用
    public  void settingPermissions();

}
