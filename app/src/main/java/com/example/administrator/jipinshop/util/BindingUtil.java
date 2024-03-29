package com.example.administrator.jipinshop.util;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.html.CustomerTagHandler_1;
import com.example.administrator.jipinshop.util.html.HtmlParser;
import com.example.administrator.jipinshop.view.glide.GlideApp;

/**
 * @author 莫小婷
 * @create 2019/3/15
 * @Describe BindingAdapter工具类
 */
public class BindingUtil {

    /**
     * 我的订单页面
     */
    @BindingAdapter("bind:text")
    public static void setText(TextView view, int status){
        if(status == 1){
            view.setText("待发货");
        }else if(status == 2){
            view.setText("待收货");
        }else {
            view.setText("已完成");
        }
    }

    /**
     * 我的订单页面————item_time
     */
    @BindingAdapter({"bind:sendStatus","bind:sendTime","bind:finishTime","bind:payTime"})
    public static void setSendTime(TextView view, int status,String sendTime,String finishTime,String payTime){
        String html = "";
        if(status == 1){
            html = "下单时间：<font color='#de151515'>"+payTime+"</font>";
        }else if(status == 2){
            html = "发件时间：<font color='#de151515'>"+sendTime+"</font>";
        }else {
            html = "收件时间：<font color='#de151515'>"+finishTime+"</font>";
        }
        view.setText(Html.fromHtml(html));
    }

    @BindingAdapter("bind:srcRound")
    public static void setImage(ImageView imageView, String src){
        GlideApp.loderRoundImage(imageView.getContext(),src,imageView, R.color.transparent,R.color.transparent);
    }

    @BindingAdapter("bind:src")
    public static void setImageSrc(ImageView imageView, String src){
        GlideApp.loderImage(imageView.getContext(),src,imageView, R.color.transparent,R.color.transparent);
    }

    @BindingAdapter("bind:srcCircle")
    public static void setImageCircle(ImageView imageView, String src){
        GlideApp.loderCircleImage(imageView.getContext(),src,imageView, R.color.transparent,R.color.transparent);
    }

    @BindingAdapter("bind:srcTopRound")
    public static void setImageTopRound(ImageView imageView , String src){
        GlideApp.loderTopRoundImage(imageView.getContext(),src,imageView, (int) imageView.getContext().getResources().getDimension(R.dimen.x10));
    }

    @BindingAdapter("bind:srcTop30Round")
    public static void setImageTop30Round(ImageView imageView , String src){
        GlideApp.loderTopRoundImage(imageView.getContext(),src,imageView,30);
    }

    /**
     * 我的试用报告页面————审核状态情况 item_statue
     */
    @BindingAdapter({"bind:trialStatue","bind:trialReason","bind:trialReportStatus"})
    public static void setTrialStatue(TextView view, int status , String reason , int reportStatus){
        switch (status){
            case 3://申请失败
            case -1://申请失败
                view.setText(reason);
                break;
            case 0://申请中
                view.setText("审核中");
                break;
            case 2://申请成功
                if (reportStatus == 1){
                    //报告审核中
                    view.setText("报告审核中");
                }else if (reportStatus == 0){
                    //草稿箱
                    view.setText("未提交报告");
                }else if (reportStatus == 2){
                    //已发布
                    view.setText("报告审核通过");
                }else if (reportStatus == -1){
                    //未通过
                    view.setText("报告审核未通过");
                }else {
                    //-2  未提交或者其他情况
                    view.setText("未提交报告");
                }
                break;
            case 1://确认参与
                view.setText("申请成功");
                break;
            default:
                view.setText("null");
                break;
        }
    }

    /**
     * 我的试用报告页面————审核描述 item_describe
     */
    @BindingAdapter({"bind:trialStatue","bind:trialReportStatus","bind:activitiesEndTime","bind:reportEndTime","bind:checkTime"})
    public static void setTrialDescribe(TextView view, int status , int reportStatus ,String activitiesEndTime ,String reportEndTime,String checkTime){
        switch (status){
            case 0://申请中
                view.setText("预计"+ activitiesEndTime + "公布名单");
                break;
            case 2://申请成功
                if (reportStatus == 2){
                    //已发布
                    view.setText("恭喜你获得永久使用商品");
                }else {
                    view.setText("请在" + reportEndTime + "前提交试用报告");
                }
                break;
            case 1://确认参与
                view.setText("请在" + checkTime + "前确认");
                break;
            default:
                view.setText("null");
                break;
        }
    }

    /**
     * 我的试用报告页面————item_bottomLayout1
     */
    @BindingAdapter({"bind:trialStatue","bind:trialReportStatus"})
    public static void setTrialBottom(RelativeLayout view, int status , int reportStatus){
        switch (status){
            case 2://申请成功
               if (reportStatus == 2 || reportStatus == 1){
                    //已发布  或者 报告审核中
                   view.setVisibility(View.GONE);
                } else {
                    //-2  未提交或者其他情况
                   view.setVisibility(View.VISIBLE);
                }
                break;
            case 1://确认参与
                view.setVisibility(View.VISIBLE);
                break;
            default:
                view.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 我的试用报告页面————item_sure
     */
    @BindingAdapter({"bind:trialStatue","bind:trialReportStatus"})
    public static void setTrialSure(TextView view, int status , int reportStatus){
        switch (status){
            case 2://申请成功
                if (reportStatus == 0){
                    //草稿箱
                    view.setText("继续编辑报告");
                }else if (reportStatus == -1){
                    //未通过
                    view.setText("重新撰写报告");
                }else {
                    //-2  未提交或者其他情况
                    view.setText("撰写试用报告");
                }
                break;
            case 1://确认参与
                view.setText("确认参与");
                break;
            default:
                view.setText("null");
                break;
        }
    }

    /**********************以下是 item_budget 收支明细里的**************************/

    @BindingAdapter({"bind:tkStatus","bind:preFee"})
    public static void setbudgetPrice(TextView view, int tkStatus , String preFee){
        switch (tkStatus){
            case 13:
                view.setText("¥" + preFee);
                view.setTextColor(0xFFA4A4A4);
                break;
            default:
                view.setText("+¥" + preFee);
                view.setTextColor(0xFFE25838);
                break;
        }
    }

    @BindingAdapter({"bind:tkStatus"})
    public static void setbudgetState(TextView view, int tkStatus ){
        switch (tkStatus){
            case 13:
                view.setText("订单失效");
                break;
            case 3:
                view.setText("订单结算");
                break;
            case 12:
                view.setText("订单付款");
                break;
            case 14:
                view.setText("订单成功");
                break;
            default:
                view.setText("");
                break;
        }
    }

    @BindingAdapter({"bind:status"})
    public static void setWithdrawStatus(TextView view, int status){
        switch (status){
            case -1:
                view.setText("审核失败");
                break;
            case 0:
                view.setText("审核中");
                break;
            case 1:
                view.setText("提现成功");
                break;
        }
    }

    @BindingAdapter({"bind:textColor"})
    public static void setWithdrawColor(TextView view, int status){
        switch (status){
            case -1:
                view.setTextColor(0xFFE25838);
                break;
            case 0:
                view.setTextColor(0xFFE25838);
                break;
            case 1:
                view.setTextColor(0xFFE25838);
                break;
        }
    }

    /**********************以下是 item_team 我的团队里的**************************/

    @BindingAdapter({"bind:shareCommission"})
    public static void setshareCommission(TextView view, String shareCommission){
        String html = "分佣 <font color='#E25838'> <b>¥"+ shareCommission +"</b></font>";
        view.setText(Html.fromHtml(html));
    }

    /*******************************以下是免单首页里的  item_free_one/item_free_two************************************/

    @BindingAdapter({"bind:userCount"})
    public static void setSaledFree(TextView view, int userCount){
        String html = "已抢<font color='#E25838'>"+ userCount +"</font>件";
        view.setText(Html.fromHtml(html));
    }

    @BindingAdapter({"bind:Freestatus","bind:applyStatus"})
    public static void setFreeButton(TextView view , int Freestatus, int applyStatus){
        switch (applyStatus){
            case -1://未申请
                if (Freestatus == 0){
                    view.setText("即将开始");
                    view.setBackgroundColor(0xFFF76D20);
                }else if (Freestatus == 1){
                    //进行中
                    view.setText("免费抢购");
                    view.setBackgroundColor(0xFFE25838);
                }else {//已售罄
                    view.setText("已售罄");
                    view.setBackgroundColor(0xFFD8D8D8);
                }
                break;
            case 1://申请了已付款
                view.setText("前往购买");
                view.setBackgroundColor(0xFFE25838);
                break;
        }
    }

    @BindingAdapter({"bind:actualPrice"})
    public static void setActualPrice(TextView view ,String actualPrice){
        if (!TextUtils.isEmpty(actualPrice)){
            String[] price = actualPrice.split("\\.");
            String html = "";
            if (price.length == 2){
                html = "¥ <font size='30'>"+ price[0] +"</font>." + price[1];
            }else if (price.length == 1){
                html = "¥ <font size='30'>"+ price[0] +"</font>.00";
            }
            view.setText(HtmlParser.buildSpannedText(html,new CustomerTagHandler_1()));
        }
    }

    /*******************************以下是我的免单里的************************************/
    @BindingAdapter({"bind:myFreeType"})
    public static void setTextitem_textTag(TextView view ,int myFreeType){
        switch (myFreeType){
            case 1:
                view.setText("待确认");
                break;
            case 3:
                view.setText("已结算");
                break;
            case -1:
                view.setText("已失效");
                break;
        }
    }

    @BindingAdapter({"bind:myFreeType", "bind:myFreeRate" , "bind:myFreePrice"})
    public static void setTextitem_freeNotice(TextView view ,int myFreeType, double myFreeRate , double myFreePrice){
        switch (myFreeType){
            case 1:
                view.setText("免"+ (myFreeRate * 100) +"%,预估返:¥" + myFreePrice);
                break;
            case 3:
                view.setText("免"+ (myFreeRate * 100) +"%,返现:¥" + myFreePrice);
                break;
            case -1:
                view.setText("免"+ (myFreeRate * 100) +"%,返现:¥0");
                break;
        }
    }

    /*******************************以下是我的订单（淘宝订单）里的************************************/
    @BindingAdapter({"bind:source","bind:type"})
    public static void setTextitem_titleText(TextView view ,int source, int type){
        if (type == 3){
            view.setText("新用户首单全返");
        }else {
            switch (source){
                case 1:
                    view.setText("京东");
                    break;
                case 2:
                    view.setText("淘宝");
                    break;
                case 3:
                    view.setText("天猫");
                    break;
                case 4:
                    view.setText("拼多多");
                    break;
                case 5:
                    view.setText("美团");
                    break;
                    default:
                        view.setText("其他");
                        break;
            }
        }
    }

    @BindingAdapter({"bind:TbState", "bind:TbEarningTime", "bind:tkStatus"})
    public static void setTextitem_explain(TextView view ,int TbState, String TbEarningTime , int tkStatus){
        switch (TbState){
            case 1:
                if (tkStatus == 1){
                    view.setText("收货后8天结算");
                }else if (tkStatus == 2){
                    view.setText("预计：" + TbEarningTime + "到账");
                }else {
                    view.setText("收货后次月结算");
                }
                break;
            case 2:
                view.setText(TbEarningTime + "已到账");
                break;
            case 3:
                view.setText("失效订单无返现");
                break;
        }
    }
}
