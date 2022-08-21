package com.aedo.my_heaven.view.main.detail.shop.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentShopFirstBinding
import com.aedo.my_heaven.util.`object`.Constant.SHOP
import com.aedo.my_heaven.util.`object`.Constant.SHOP_PAY
import com.aedo.my_heaven.view.main.detail.shop.fragment.order.OrderActivity
import com.iamport.sdk.domain.core.Iamport

class ShopFirstFragment : Fragment() {

    private lateinit var mBinding: FragmentShopFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Iamport.init(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_first, container, false)
        setClick()
        return mBinding.root
    }

    @SuppressLint("ShowToast")
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

        mBinding.btnThird.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            intent.putExtra(SHOP, mBinding.tvNameThird.text.toString())
            intent.putExtra(SHOP_PAY, mBinding.tvMoneyThrid.text.toString())
            startActivity(intent)
        }

        mBinding.btnFour.setOnClickListener {
            val intent = Intent(activity, OrderActivity::class.java)
            intent.putExtra(SHOP, mBinding.tvNameFour.text.toString())
            intent.putExtra(SHOP_PAY, mBinding.tvMoneyFour.text.toString())
            startActivity(intent)
        }

    }
}