package com.aedo.my_heaven.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aedo.my_heaven.view.main.detail.shop.fragment.ShopFirstFragment
import com.aedo.my_heaven.view.main.detail.shop.fragment.ShopSecondFragment
import com.aedo.my_heaven.view.main.detail.shop.fragment.ShopThridFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ShopFirstFragment()
            1 -> return ShopSecondFragment()
            2 -> return ShopThridFragment()
        }
        return ShopThridFragment()
    }
}