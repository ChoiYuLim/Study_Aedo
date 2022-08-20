package com.aedo.my_heaven.view.main.detail.shop.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentShopFirstBinding
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FIRST
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FIRST_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FOUR
import com.aedo.my_heaven.util.`object`.Constant.SHOP_FOUR_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SECOND
import com.aedo.my_heaven.util.`object`.Constant.SHOP_SECOND_PAY
import com.aedo.my_heaven.util.`object`.Constant.SHOP_THIRD
import com.aedo.my_heaven.util.`object`.Constant.SHOP_THIRD_PAY
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.FourOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.OneOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.ThreeOrderActivity
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.TwoOrderActivity
import com.iamport.sdk.domain.core.Iamport

class ShopFirstFragment : Fragment() {

    private lateinit var mBinding : FragmentShopFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Iamport.init(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_first,container,false)
        setClick()
        return mBinding.root
    }

    @SuppressLint("ShowToast")
    private fun setClick() {

        mBinding.btnActivityShopPurchase.setOnClickListener {
            val intent = Intent(activity, OneOrderActivity::class.java)
            intent.putExtra(SHOP_FIRST,mBinding.tvActivityShopTitle.text.toString())
            intent.putExtra(SHOP_FIRST_PAY, mBinding.tvActivityShopSalesPrice.text.toString())
            startActivity(intent)
        }

        mBinding.btnSecond.setOnClickListener {
            val intent = Intent(activity, TwoOrderActivity::class.java)
            intent.putExtra(SHOP_SECOND,mBinding.tvNameSecond.text.toString())
            intent.putExtra(SHOP_SECOND_PAY, mBinding.tvMoneySecond.text.toString())
            startActivity(intent)
        }

        mBinding.btnThird.setOnClickListener {
            val intent = Intent(activity, ThreeOrderActivity::class.java)
            intent.putExtra(SHOP_THIRD,mBinding.tvNameThird.text.toString())
            intent.putExtra(SHOP_THIRD_PAY, mBinding.tvMoneyThrid.text.toString())
            startActivity(intent)
        }

        mBinding.btnFour.setOnClickListener {
            val intent = Intent(activity, FourOrderActivity::class.java)
            intent.putExtra(SHOP_FOUR,mBinding.tvNameFour.text.toString())
            intent.putExtra(SHOP_FOUR_PAY, mBinding.tvMoneyFour.text.toString())
            startActivity(intent)
        }

    }
}