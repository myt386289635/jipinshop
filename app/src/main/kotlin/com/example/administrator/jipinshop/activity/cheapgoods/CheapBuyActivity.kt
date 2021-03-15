package com.example.administrator.jipinshop.activity.cheapgoods

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.BaseActivity

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe 官方百万补贴专区（单开页）
 */
class CheapBuyActivity : BaseActivity() {

    private var startPop: Boolean = true//是否弹出关闭确认弹窗
    // 定义
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init()
        startPop = intent.getBooleanExtra("startPop", true)
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.home_fragment, zCheapBuyFragment.getInstance(startPop,"3")).commit()
    }

}