package com.aedo.my_heaven.view.main.detail.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.adapter.ViewPagerAdapter
import com.aedo.my_heaven.api.APIService
import com.aedo.my_heaven.api.ApiUtils
import com.aedo.my_heaven.databinding.ActivityShopBinding
import com.aedo.my_heaven.util.base.BaseActivity
import com.aedo.my_heaven.view.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ShopActivity : BaseActivity() {

    private lateinit var mBinding: ActivityShopBinding
    private lateinit var apiServices: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shop)
        mBinding.activity = this@ShopActivity
        apiServices = ApiUtils.apiService
        setupViewPager()
        inStatusBar()
    }

    private fun setupViewPager() {
        val viewPager = mBinding.vpActivityShop
        val tabLayout = mBinding.tlActivityShop
        val titles = listOf(
            R.string.activity_shop_tab_1,
            R.string.activity_shop_tab_2,
            R.string.activity_shop_tab_3
        )
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, i: Int ->
            tab.text = getString(titles[i])
        }.attach()
    }

    fun onBackClick(v: View) {
        moveMain()
    }

    fun onShopTermClick(v: View) {
        moveShopTerm()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}