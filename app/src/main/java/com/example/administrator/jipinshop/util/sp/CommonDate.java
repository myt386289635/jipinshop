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


    //数据缓存表(sp文件名：netCache)
//    public static final String NETCACHE = "netCache";
//    public static final String RecommendFragmentDATA = "RecommendFragment";
//    public static final String HealthFragmentDATA = "HealthFragment";
//    public static final String KitchenFragmentDATA = "KitchenFragment";
//    public static final String HouseholdFragmentDATA = "HouseholdFragment";
//    public static final String ElectricityFragmentDATA = "ElectricityFragment";
//    public static final String CommonEvaluationFragmentDATA1 = "CommonEvaluationFragment1";
//    public static final String CommonEvaluationFragmentDATA2 = "CommonEvaluationFragment2";
//    public static final String CommonEvaluationFragmentDATA3 = "CommonEvaluationFragment3";
//    public static final String CommonEvaluationFragmentDATA4 = "CommonEvaluationFragment4";
//    public static final String CommonEvaluationFragmentDATA5 = "CommonEvaluationFragment5";
//    public static final String CommonFindFragmentDATA1 = "CommonFindFragment1";
//    public static final String CommonFindFragmentDATA2 = "CommonFindFragment2";
//    public static final String CommonFindFragmentDATA3 = "CommonFindFragment3";
//    public static final String CommonFindFragmentDATA4 = "CommonFindFragment4";
//    public static final String CommonFindFragmentDATA5 = "CommonFindFragment5";



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

    public static final String alipAccount = "alipAccount";//支付宝账号
    public static final String alipName = "alipName";//支付宝绑定的真实姓名

}
