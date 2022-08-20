package com.aedo.my_heaven.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.model.shop.MyOrders
import com.aedo.my_heaven.util.`object`.Constant
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_CREATED
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_ITEM
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PLACE_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PLACE_NUMBER
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_PRICE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_RECEIVER_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_RECEIVER_PHONE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_SENDER_NAME
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_SENDER_PHONE
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_COMPANY
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_WORD
import com.aedo.my_heaven.util.`object`.Constant.MY_ORDER_WORD_WORDSECOND
import com.aedo.my_heaven.util.base.MyApplication
import com.aedo.my_heaven.view.main.detail.shop.MyOrderDetailActivity
import com.aedo.my_heaven.view.side.list.ListDetailActivity

class MyOrderAdapter (private val postList : List<MyOrders>, val context : Context)
    : RecyclerView.Adapter<MyOrderAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position],context)
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it,position)
        }
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    private var itemClickListener: OnItemClickListener?=null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    inner class ViewHolder (itemView: View? ) : RecyclerView.ViewHolder(itemView!!){

        val order_flower = itemView?.findViewById<TextView>(R.id.tv_flower_pay)
        val myorder_receiver_name = itemView?.findViewById<TextView>(R.id.tv_receiver_name_detail)
        val myorder_send_name = itemView?.findViewById<TextView>(R.id.tv_send_name_detail)
        val order_timestamp = itemView?.findViewById<TextView>(R.id.tv_order_timestamp)
        val cl_order = itemView?.findViewById<ConstraintLayout>(R.id.cl_order)

        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(itemPhoto : MyOrders?, context: Context){
            order_flower?.text = "${itemPhoto?.item.toString()}/${itemPhoto?.price.toString()}"
            myorder_receiver_name?.text = itemPhoto?.receiver?.name.toString()
            myorder_send_name?.text = itemPhoto?.sender?.name.toString()

            cl_order?.setOnClickListener {
                val intent = Intent(context, MyOrderDetailActivity::class.java)
                intent.putExtra(MY_ORDER_PLACE_NAME, itemPhoto?.place?.name.toString())
                intent.putExtra(MY_ORDER_PLACE_NUMBER, itemPhoto?.place?.number.toString())
                intent.putExtra(MY_ORDER_ITEM, itemPhoto?.item.toString())
                intent.putExtra(MY_ORDER_PRICE, itemPhoto?.price.toString())
                intent.putExtra(MY_ORDER_RECEIVER_NAME, itemPhoto?.receiver?.name.toString())
                intent.putExtra(MY_ORDER_RECEIVER_PHONE, itemPhoto?.receiver?.phone.toString())
                intent.putExtra(MY_ORDER_SENDER_NAME, itemPhoto?.sender?.name.toString())
                intent.putExtra(MY_ORDER_SENDER_PHONE, itemPhoto?.sender?.phone.toString())
                intent.putExtra(MY_ORDER_WORD_COMPANY, itemPhoto?.word?.company.toString())
                intent.putExtra(MY_ORDER_WORD_WORD, itemPhoto?.word?.word.toString())
                intent.putExtra(MY_ORDER_WORD_WORDSECOND, itemPhoto?.word?.wordsecond.toString())
                intent.putExtra(MY_ORDER_CREATED, itemPhoto?.created.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }

        }
    }
}