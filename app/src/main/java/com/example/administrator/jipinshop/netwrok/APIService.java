package com.example.administrator.jipinshop.netwrok;


import com.example.administrator.jipinshop.bean.ElectricityFragmentBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.bean.HealthFragmentBean;
import com.example.administrator.jipinshop.bean.HouseholdFragmentBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.KitchenFragmentBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.json.LoginJson;
import com.example.administrator.jipinshop.bean.json.PushMessageJson;
import com.example.administrator.jipinshop.bean.LuckselectBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {

    /**
     * 榜单首页接口
     */
    @GET("qualityshop-api/api/goodsRankList")
    Observable<RecommendFragmentBean> ranklist();

    /**
     * 登陆接口
     * @param loginJson
     */
    @POST("qualityshop-api/api/Messagelogin")
    Observable<LoginBean> login(@Body LoginJson loginJson);


    /**
     * 发送验证码
     */
    @POST("qualityshop-api/api/pushMessage")
    Observable<SuccessBean> pushMessage(@Body PushMessageJson pushMessageJson);

    /**
     * 退出登陆
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/logout")
    Observable<SuccessBean> logout(@Field("userId") String userId);


    /**
     * 用户信息修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/ModelUserUpdate")
    Observable<SuccessBean> userUpdate(@FieldMap Map<String,String> param);

    /**
     * 获取佣金金额
     */
    @GET("qualityshop-api/api/account")
    Observable<AccountBean> account(@Query("userId") String userId);

    /**
     * 获取用户信息
     */
    @GET("qualityshop-api/api/modelUser")
    Observable<UserInfoBean> modelUser(@Query("userId") String userId);

    /**
     * 获取关注列表
     */
    @GET("qualityshop-api/api/concer")
    Observable<FollowBean> concer(@Query("page") String page ,@Query("userId") String userId);


    /**
     * 获取收藏列表
     */
    @GET("qualityshop-api/api/collect")
    Observable<FovalBean> collect(@Query("page") String page ,@Query("userId") String userId);

    /**
     * 查询签到7天状态
     */
    @GET("qualityshop-api/api/sign")
    Observable<SignBean> sign(@Query("userId") String userId);

    /**
     * 签到
     */
    @POST("qualityshop-api/api/signInsert")
    @FormUrlEncoded
    Observable<SignInsertBean> signInsert(@Field("userId") String userId);


    /**
     * 提现
     */
    @POST("qualityshop-api/api/lipay")
    @FormUrlEncoded
    Observable<SuccessBean> lipay(@Field("userId") String userId,@Field("phone") String phone,
                                  @Field("amount") String amount,@Field("real_name") String real_name);

    /**
     * 提现记录
     */
    @GET("qualityshop-api/api/alipay")
    Observable<RecordBean> alipay(@Query("userId") String userId);

    /**
     * 我要反馈
     */
    @POST("qualityshop-api/api/feedBackInsert")
    @FormUrlEncoded
    Observable<SuccessBean> feedBack(@Field("userId") String userId,@Field("content") String content);

    /**
     * 取消关注
     */
    @POST("qualityshop-api/api/concerDelete")
    @FormUrlEncoded
    Observable<SuccessBean> concerDelete(@Field("concerId") String concerId);

    /**
     * 一键补签
     */
    @POST("qualityshop-api/api/Supplement")
    @FormUrlEncoded
    Observable<SupplementBean> Supplement(@Field("userId") String userId);

    /**
     * 关注
     */
    @POST("qualityshop-api/api/concerInsert")
    @FormUrlEncoded
    Observable<SuccessBean> concerInsert(@Field("userId") String userId,@Field("attentionUserId") String attentionUserId);

    /**
     * 抽奖结果
     */
    @POST("qualityshop-api/api/luckselect")
    @FormUrlEncoded
    Observable<LuckselectBean> luckselect(@Field("userId") String userId);

    /**
     * 上传图片
     */
    @POST("qualityshop-api/api/importCustomer")
    @Multipart
    Observable<ImageBean> importCustomer(@Part MultipartBody.Part importFile);

    /**
     * 榜单tab的字段
     */
    @GET("qualityshop-api/api/goodsCategory")
    Observable<TabBean> goodsCategory();

    /**
     * 获取抽奖图片
     */
    @POST("qualityshop-api/api/luckselects")
    Observable<LuckImageBean> luckselects();

    /**
     * 榜单二级菜单列表
     */
    @GET("qualityshop-api/api/goodRank")
    Observable<HealthFragmentBean> goodRank(@Query("mark") String mark , @Query("goodsId") String goodsId);

    /**
     * 榜单二级菜单列表
     */
    @GET("qualityshop-api/api/goodRank")
    Observable<KitchenFragmentBean> goodRank2(@Query("mark") String mark , @Query("goodsId") String goodsId);

    /**
     * 榜单二级菜单列表
     */
    @GET("qualityshop-api/api/goodRank")
    Observable<HouseholdFragmentBean> goodRank3(@Query("mark") String mark , @Query("goodsId") String goodsId);

    /**
     * 榜单二级菜单列表
     */
    @GET("qualityshop-api/api/goodRank")
    Observable<ElectricityFragmentBean> goodRank4(@Query("mark") String mark , @Query("goodsId") String goodsId);
}
