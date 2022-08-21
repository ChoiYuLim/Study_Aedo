package com.aedo.my_heaven.view.main.detail.shop.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentShopThridBinding
import com.aedo.my_heaven.util.`object`.Constant.SHOP
import com.aedo.my_heaven.util.`object`.Constant.SHOP_PAY
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.OrderActivity

class ShopThridFragment : Fragment() {

    private lateinit var mBinding : FragmentShopThridBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_thrid,container,false)
        setClick()
        return mBinding.root
    }

    private fun setClick() {
        mBinding.btnActivityShopPurchase.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            intent.putExtra(SHOP, mBinding.tvActivityShopTitle.text.toString())
            intent.putExtra(SHOP_PAY, mBinding.tvActivityShopSalesPrice.text.toString())
            startActivity(intent)
        }

        mBinding.btnSecond.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            intent.putExtra(SHOP, mBinding.tvNameSecond.text.toString())
            intent.putExtra(SHOP_PAY, mBinding.tvMoneySecond.text.toString())
            startActivity(intent)

        }
    }


}