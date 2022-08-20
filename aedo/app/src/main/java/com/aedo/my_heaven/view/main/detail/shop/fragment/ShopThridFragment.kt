package com.aedo.my_heaven.view.main.detail.shop.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentShopThridBinding
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.`object`.Constant.SHOP_EIGHT
import com.aedo.my_heaven.util.`object`.Constant.SHOP_EIGHT_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_NINE
import com.aedo.my_heaven.util.`object`.Constant.SHOP_NINE_PAY
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.EightOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.NineOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.OneOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.TwoOrderActivity

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
            val intent = Intent(activity, EightOrderActivity::class.java)
            intent.putExtra(SHOP_EIGHT,mBinding.tvActivityShopTitle.text.toString())
            intent.putExtra(SHOP_EIGHT_PAY, mBinding.tvActivityShopSalesPrice.text.toString())
            startActivity(intent)
        }

        mBinding.btnSecond.setOnClickListener {
            val intent = Intent(activity, NineOrderActivity::class.java)
            intent.putExtra(SHOP_NINE,mBinding.tvNameSecond.text.toString())
            intent.putExtra(SHOP_NINE_PAY, mBinding.tvMoneySecond.text.toString())
            startActivity(intent)

        }
    }


}