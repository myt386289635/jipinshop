package com.example.administrator.jipinshop.util.sp;

/**
 * @author 莫小婷
 * @create 2018/8/16
 * @Describe 公用数据
 */
public class CommonDate {

    //榜单的tab (sp文件名：spUtils  默认)
    public static final String FIRST = "FIRST";//是否是第一次下载
    public static final String SubTab = "SubTab";//榜单的二级菜单以及一级菜单
    public static final String EvaluationTab = "EvaluationTab";//评测的tab
    public static final String FindTab = "FindTab";//发现的tab
    public static final String startPage = "startPage";//启动页
    public static final String FIRSTSHOP = "FIRSTSHOP";//新人首次进入商品详情打开商品详情引导页
    public static final String FIRSTCHEAP = "FIRSTCHEAP";//新人首次进入特惠购打开新手指导
    public static final String FIRSTNEWPEOPLE = "FIRSTNEWPEOPLE";//新人首次进入0元购页面打开新手指导
    public static final String POPID = "POPID";//活动popid为了不弹出活动
    public static final String CLIP = "clip";//粘贴板内容存稿
    public static final String AD = "AD";//广告


    //用户数据表（sp文件名：user）
    public static final String USER = "user";//表名
    public static final String userId = "userId";//用户id
    public static final String token = "token";//用户token
    public static final String userNickName = "userNickName";//用户昵称
    public static final String userNickImg = "userNickImg";//用户头像
    public static final String userAcutalName = "userAcutalName";//用户真实姓名
    public static final String userMemberGrade = "userMemberGrade";//用户角色  0：公司编辑 1：普通用户 2：达人用户
    public static final String userGender = "userGender";//用户性别
    public static final String userBirthday = "userBirthday";//用户生日
    public static final String userPhone = "userPhone";//用户手机号
    public static final String userPoint = "userPoint";//用户极币（int类型）
    public static final String bindMobile = "bindMobile";//是否绑定手机
    public static final String bindWeixin = "bindWeixin";//是否绑定微信
    public static final String bindWeibo = "bindWeibo";//是否绑定微博
    public static final String qrCode = "qrCode";//邀请码
    public static final String relationId= "relationId";//用于判断是否有淘宝授权
    public static final String bgImg="bgImg";//用户背景图片
    public static final String userSign="userSign";//用户签名
    public static final String wechat = "wechat";//微信号

}
