package com.example.administrator.jipinshop.fragment.mine.group

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.mine.group.MyGroupActivity
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.MyWalletBean
import com.example.administrator.jipinshop.databinding.FragmentGroupBinding
import com.example.administrator.jipinshop.util.TimeUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.google.gson.Gson

/**
 * @author 莫小婷
 * @create 2020/11/25
 * @Describe
 */
class KTMyGroupFragment : DBBaseFragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentGroupBinding
    private var position = 0
    private lateinit var bean: MyWalletBean.DataBean
    private var timer = 0L
    private lateinit var countDownTimer : CountDownTimer

    companion object{
        //position父类位置
        fun getInstance(date: String, position: Int): KTMyGroupFragment {
            var fragment = KTMyGroupFragment()
            var bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putString("date", date)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_group, container, false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        arguments?.let {
            position = it.getInt("position", 0)
            bean = Gson().fromJson(it.getString("date"),MyWalletBean.DataBean::class.java)
        }
        //数据
        GlideApp.loderRoundImage(context,bean.groupList[position].img,mBinding.groupImage)
        mBinding.groupPeople.text = bean.groupList[position].leftCount
        mBinding.groupPrice.text = "￥" + bean.groupList[position].upFee
        timer = (bean.groupList[position].timeToEndTime * 1000) - System.currentTimeMillis()
        if (timer > 0) {//开始倒计时
            countDownTimer = object : CountDownTimer(timer, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    mBinding.groupTime.text = TimeUtil.getCountTimeByLong3(millisUntilFinished)
                }
                override fun onFinish() {}
            }.start()
        }else{
            mBinding.groupTime.text = "00:00:00"
        }
    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.group_container -> {
               //进入详情
               if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                   startActivity(Intent(context, LoginActivity::class.java))
                   return
               }
               startActivity(Intent(context, MyGroupActivity::class.java)
                       .putExtra("id", bean.groupList[position].id)
               )
           }
           R.id.group_share -> {
               //todo 分享
           }
       }
    }

}