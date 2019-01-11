package com.example.administrator.jipinshop.netwrok;


import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.CommentBean;
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
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.bean.VoteBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
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
     * 取消关注  已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/follow/delete")
    Observable<SuccessBean> concernDelete(@Field("attentionUserId") String attentionUserId);

    /**
     * 添加关注   已修改
     */
    @POST("qualityshop-api/api/follow")
    @FormUrlEncoded
    Observable<SuccessBean> concernInsert(@Field("attentionUserId") String attentionUserId);

    /**
     * 榜单tab的字段   已修改
     */
    @GET("qualityshop-api/api/goodsCategoryList")
    Observable<TabBean> goodsCategory();

    /**
     * 榜单二级菜单列表  已修改
     */
    @GET("qualityshop-api/api/goodsList")
    Observable<HomeCommenBean> goodRank(@Query("category2Id") String goodsId);

    /**
     * 商品详情  已修改
     */
    @GET("qualityshop-api/api/getGoodsInfo")
    Observable<ShoppingDetailBean> goodsRankDetailList(@Query("goodsId") String goodsId);

    /**
     * 添加收藏    已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/collect/add")
    Observable<SuccessBean> collectInsert(@FieldMap Map<String,String> param);

    /**
     * 删除收藏    已修改
     */
    @GET("qualityshop-api/api/collect/delete")
    Observable<SuccessBean> collectDelete(@QueryMap Map<String,String> param);

    /**
     * 添加点赞   已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/vote/add")
    Observable<VoteBean> snapInsert(@FieldMap Map<String,String> param);

    /**
     * 删除点赞    已修改
     */
    @GET("qualityshop-api/api/vote/delete")
    Observable<VoteBean> snapDelete(@QueryMap Map<String,String> param);

    /**
     * 查看评论列表   已修改
     */
    @GET("qualityshop-api/api/comment/list")
    Observable<CommentBean> comment(@QueryMap Map<String,String> param);

    /**
     * 添加评论     已修改
     */
    @FormUrlEncoded
    @POST("qualityshop-api/api/comment/add")
    Observable<SuccessBean> commentInsert(@Field("targetId") String targetId, @Field("toUserId") String toUserId,
                                          @Field("content") String content, @Field("parentId") String parentId,
                                          @Field("type") String type);

    /**
     * 版本更新   已修改
     */
    @GET("qualityshop-api/api/getAppVersion")
    Observable<AppVersionbean> getAppVersion(@Query("type") String type , @Query("clientVersionCode") String clientVersionCode);


    /**
     * 检测页面状态（登陆后需要检测 关注、收藏、点赞）
     */
    @GET("qualityshop-api/api/view/status")
    Observable<PagerStateBean> pagerState(@Query("type") String type ,@Query("targetId") String targetId);

    /**
     * 获取用户信息  已修改
     */
    @GET("qualityshop-api/api/user/getUserInfo")
    Observable<UserInfoBean> modelUser();

    /**
     * 搜索记录
     */
    @GET("qualityshop-api/api/search/log")
    Observable<SreachHistoryBean> searchLog();

    /**
     * 删除所有搜索记录
     */
    @GET("qualityshop-api/api/search/deleteAll")
    Observable<SuccessBean> searchDeleteAll();

    /**
     * 删除单条搜索记录
     */
    @GET("qualityshop-api/api/search/delete")
    Observable<SuccessBean> searchDelete(@Query("id") String id);

    /**
     * 搜索列表(商品)   已修改
     */
    @GET("qualityshop-api/api/search")
    Observable<SreachResultGoodsBean> searchGoods(@Query("page") String page,@Query("keyword") String keyword,@Query("type")String type);

    /**
     * 搜索列表（发现、评测、试用报告）
     */
    @GET("qualityshop-api/api/search")
    Observable<SreachResultArticlesBean> searchArticles(@Query("page") String page, @Query("keyword") String keyword, @Query("type")String type);

    /**
     * 获取发现tab 已修改
     */
    @GET("qualityshop-api/api/evaluation/categoryList")
    Observable<EvaluationTabBean> findTab();

    /**
     * 获取发现列表  已修改
     */
    @GET("qualityshop-api/api/found/list")
    Observable<FindListBean> findLis(@Query("categoryId") String categoryId,@Query("page") String page);

    /**
     * 获取评测tab  已修改
     */
    @GET("qualityshop-api/api/found/categoryList")
    Observable<EvaluationTabBean> evaTab();

    /**
     * 获取评测列表  已修改
     */
    @GET("qualityshop-api/api/evaluation/list")
    Observable<EvaluationListBean> evaluationList(@Query("categoryId")String categoryId , @Query("page") String page);

    /**
     * 文章详情   已修改
     */
    @GET("qualityshop-api/api/article/detail")
    Observable<FindDetailBean> findDetail(@Query("articleId") String articleId,@Query("type") String type ,@Query("client") String client);

/*************************************************以下是还未修改的接口***********************************************/


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
     * 一键补签
     */
    @POST("qualityshop-api/api/supplement")
    @FormUrlEncoded
    Observable<SupplementBean> Supplement(@Field("userId") String userId);


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
     * 获取抽奖图片
     */
    @GET("qualityshop-api/api/prizeList")
    Observable<LuckImageBean> luckselects(@Query("userId") String userId);


    /**
     * 判断用户是否收藏此文章或者商品
     */
    @GET("qualityshop-api/api/collect")
    Observable<SnapSelectBean> isCollect(@QueryMap Map<String,String> param);


    /**
     * 判断用户是否点赞此文章或者商品
     */
    @GET("qualityshop-api/api/snapSelect")
    Observable<SnapSelectBean> snapSelect(@QueryMap Map<String,String> param);


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
     * 评测详情
     */
    @GET("qualityshop-api/evalWay/selectById")
    Observable<EvaluationDetailBean> evaluationDetail(@Query("evalWayId") String evalWayId);


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

}
