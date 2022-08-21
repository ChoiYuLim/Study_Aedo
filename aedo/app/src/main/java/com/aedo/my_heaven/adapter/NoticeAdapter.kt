package com.aedo.my_heaven.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.model.notice.Announcement
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_CONTENT
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_CREATED
import com.aedo.my_heaven.util.`object`.Constant.NOTICE_TITLE
import com.aedo.my_heaven.view.notice.NoticeDetailActivity


class NoticeAdapter(private val noticeList: List<Announcement>, private val context: Context) :
    RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.view_item_notice, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: NoticeAdapter.ViewHolder, position: Int) {
        holder.bind(noticeList[position], context)

    }

    override fun getItemCount(): Int {
        return noticeList.count()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val notice_title = itemView?.findViewById<TextView>(R.id.tv_notice_item_title)
        val notice_timestamp = itemView?.findViewById<TextView>(R.id.tv_notice_item_timestamp)
        val btn_go = itemView?.findViewById<ImageView>(R.id.notice_img_go)
        val cl_body = itemView?.findViewById<ConstraintLayout>(R.id.cl_notice)

        fun bind(list: Announcement?, context: Context) {
            notice_title?.text = list?.title
            notice_timestamp?.text = list?.created

            cl_body?.setOnClickListener {
                val intent = Intent(context, NoticeDetailActivity::class.java)
                intent.putExtra(NOTICE_TITLE, list?.title.toString())
                intent.putExtra(NOTICE_CONTENT, list?.content.toString())
                intent.putExtra(NOTICE_CREATED, list?.created.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }
}