package com.example.administrator.jipinshop.netwrok;


import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommentInsertBean;
import com.example.administrator.jipinshop.bean.EvaluationDetailBean;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.IntegralShopBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.MemberLevelBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SreachResultBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.bean.json.LoginJson;
import com.example.administrator.jipinshop.bean.json.PushMessageJson;

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
import retrofit2.http.QueryMap;

public interface APIService {

    /**
     * 榜单首页接口  已修改
     */
    @GET("qualityshop-api/api/getTopGoodsList")
    Observable<RecommendFragmentBean> ranklist();

    /**
     * 登陆接口   已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/mobileLogin")
    Observable<LoginBean> login(@Field("mobile") String mobile,@Field("code") String code);


    /**
     * 发送验证码    已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/sendMessage")
    Observable<SuccessBean> pushMessage(@Field("mobile") String mobile,@Field("type") String type);

    /**
     * 退出登陆    已修改
     */
    @POST("qualityshop-api/api/logout")
    Observable<SuccessBean> logout();


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
    @GET("qualityshop-api/api/selectAll")
    Observable<FollowBean> concer(@Query("page") String page ,@Query("userId") String userId);


    /**
     * 获取收藏列表
     */
    @GET("qualityshop-api/api/collectAll")
    Observable<FovalBean> collect(@Query("page") String page ,@Query("userId") String userId);

    /**
     * 查询签到7天状态
     */
    @GET("qualityshop-api/api/signinDetail")
    Observable<SignBean> sign(@Query("userId") String userId);

    /**
     * 签到
     */
    @POST("qualityshop-api/api/signin")
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
    @GET("qualityshop-api/api/concernDelete")
    Observable<SuccessBean> concernDelete(@Query("userId") String userId,@Query("attentionUserId") String attentionUserId);

    /**
     * 一键补签
     */
    @POST("qualityshop-api/api/supplement")
    @FormUrlEncoded
    Observable<SupplementBean> Supplement(@Field("userId") String userId);

    /**
     * 关注
     */
    @POST("qualityshop-api/api/concernInsert")
    @FormUrlEncoded
    Observable<SuccessBean> concernInsert(@Field("userId") String userId,@Field("attentionUserId") String attentionUserId);

    /**
     * 抽奖结果
     */
    @POST("qualityshop-api/api/startLuckyDraw")
    @FormUrlEncoded
    Observable<LuckselectBean> luckselect(@Field("userId") String userId);

    /**
     * 上传图片
     */
    @POST("qualityshop-api/api/importCustomer")
    @Multipart
    Observable<ImageBean> importCustomer(@Part("url")String url,@Part MultipartBody.Part importFile);

    /**
     * 榜单tab的字段   已修改
     */
    @GET("qualityshop-api/api/goodsCategoryList")
    Observable<TabBean> goodsCategory();

    /**
     * 获取抽奖图片
     */
    @GET("qualityshop-api/api/prizeList")
    Observable<LuckImageBean> luckselects(@Query("userId") String userId);

    /**
     * 榜单二级菜单列表  已修改
     */
    @GET("qualityshop-api/api/goodsList")
    Observable<HomeCommenBean> goodRank(@Query("category2Id") String goodsId);

    /**
     * 搜索列表
     */
    @GET("qualityshop-api/api/searchGoods")
    Observable<SreachResultBean> searchGoods(@Query("goodsName") String goodsName);

    /**
     * 商品详情  已修改
     */
    @GET("qualityshop-api/api/getGoodsInfo")
    Observable<ShoppingDetailBean> goodsRankDetailList(@Query("goodsId") String goodsId);

    /**
     * 判断用户是否收藏此文章或者商品
     */
    @GET("qualityshop-api/api/collect")
    Observable<SnapSelectBean> isCollect(@QueryMap Map<String,String> param);

    /**
     * 添加收藏
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/collectInsert")
    Observable<SuccessBean> collectInsert(@FieldMap Map<String,String> param);

    /**
     * 删除收藏
     */
    @GET("qualityshop-api/api/collectDelete")
    Observable<SuccessBean> collectDelete(@QueryMap Map<String,String> param);

    /**
     * 判断用户是否点赞此文章或者商品
     */
    @GET("qualityshop-api/api/snapSelect")
    Observable<SnapSelectBean> snapSelect(@QueryMap Map<String,String> param);

    /**
     * 添加点赞
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/snapInsert")
    Observable<SuccessBean> snapInsert(@FieldMap Map<String,String> param);

    /**
     * 删除点赞
     */
    @GET("qualityshop-api/api/snapDelete")
    Observable<SuccessBean> snapDelete(@QueryMap Map<String,String> param);

    /**
     * 查看评论列表
     */
    @GET("qualityshop-api/api/comment")
    Observable<CommentBean> comment(@QueryMap Map<String,String> param);

    /**
     * 添加评论
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/commentInsert")
    Observable<CommentInsertBean> commentInsert(@Field("articId") String articId, @Field("userId") String userId,
                                                @Field("content") String content, @Field("parentId") String parentId,
                                                @Field("status") String status);

    /**
     * 获取未读消息
     */
    @GET("qualityshop-api/message/selectCount")
    Observable<UnMessageBean> unMessage(@Query("userId") String userId);

    /**
     * 获取消息列表详情内容
     */
    @GET("qualityshop-api/message/selectAll")
    Observable<SystemMessageBean> messageAll(@Query("page") String page ,@Query("userId") String userId,@Query("type") String type);

    /**
     * 查看未读消息
     */
    @GET("qualityshop-api/message/selectById")
    Observable<SuccessBean> readMsg(@Query("id") String id);

    /**
     * 获取评测tab
     */
    @GET("qualityshop-api/evalWay/selectCategory")
    Observable<EvaluationTabBean> evaTab();

    /**
     * 获取评测列表
     */
    @GET("qualityshop-api/evalWay/selectList")
    Observable<EvaluationListBean> evaluationList(@Query("categoryId")String categoryId , @Query("page") String page, @Query("userId")String userId);

    /**
     * 评测详情
     */
    @GET("qualityshop-api/evalWay/selectById")
    Observable<EvaluationDetailBean> evaluationDetail(@Query("evalWayId") String evalWayId);

    /**
     * 获取发现tab
     */
    @GET("qualityshop-api/findGoods/selectCategory")
    Observable<EvaluationTabBean> findTab();

    /**
     * 获取发现列表
     */
    @GET("qualityshop-api/findGoods/selectList")
    Observable<FindListBean> findLis(@Query("categoryId") String categoryId,@Query("page") String page,@Query("userId") String userId);

    /**
     * 发现详情
     */
    @GET("qualityshop-api/findGoods/selectById")
    Observable<FindDetailBean> findDetail(@Query("findgoodsId") String findgoodsId);

    /**
     * 个人主页
     */
    @GET("qualityshop-api/api/selectConcern")
    Observable<UserPageBean> userPage(@Query("attentionUserId") String attentionUserId,@Query("page") String page);

    /**
     * 积分明细
     */
    @GET("qualityshop-api/api/pointDetail")
    Observable<PointDetailBean> pointDetail(@Query("userId") String userId);

    /**
     * 获取累计积分
     */
    @GET("qualityshop-api/api/totalAddPoint")
    Observable<MemberLevelBean> totalAddPoint(@Query("userId") String userId);

    /**
     * 积分商城
     */
    @GET("qualityshop-api/goodsPoint/selectList")
    Observable<IntegralShopBean> integralShopList(@Query("page") String page);

    /**
     * 版本更新   已修改
     */
    @GET("qualityshop-api/api/getAppVersion")
    Observable<AppVersionbean> getAppVersion(@Query("type") String type , @Query("clientVersionCode") String clientVersionCode);
}
