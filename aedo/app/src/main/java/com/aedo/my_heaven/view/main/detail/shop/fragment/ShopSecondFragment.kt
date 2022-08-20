package com.aedo.my_heaven.view.main.detail.shop.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentShopSecondBinding
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FIVE
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FIVE_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SEVEN
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SEVEN_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SIX
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SIX_PAY
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.*

class ShopSecondFragment : Fragment() {

    private lateinit var mBinding: FragmentShopSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_second,container,false)
        setClick()
        return mBinding.root
    }

    private fun setClick() {
        mBinding.btnActivityShopPurchase.setOnClickListener {
            val intent = Intent(activity, FiveOrderActivity::class.java)
            intent.putExtra(SHOP_FIVE,mBinding.tvActivityShopTitle.text.toString())
            intent.putExtra(SHOP_FIVE_PAY, mBinding.tvActivityShopSalesPrice.text.toString())
            startActivity(intent)
        }

        mBinding.btnSecond.setOnClickListener {
            val intent = Intent(activity, SixOrderActivity::class.java)
            intent.putExtra(SHOP_SIX,mBinding.tvNameSecond.text.toString())
            intent.putExtra(SHOP_SIX_PAY, mBinding.tvMoneySecond.text.toString())
            startActivity(intent)

        }

        mBinding.btnThird.setOnClickListener {
            val intent = Intent(activity, SevenOrderActivity::class.java)
            intent.putExtra(SHOP_SEVEN,mBinding.tvNameThird.text.toString())
            intent.putExtra(SHOP_SEVEN_PAY, mBinding.tvMoneyThrid.text.toString())
            startActivity(intent)

        }
    }

}