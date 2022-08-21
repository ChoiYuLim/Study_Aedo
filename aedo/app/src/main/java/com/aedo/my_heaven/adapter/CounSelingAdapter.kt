package com.aedo.my_heaven.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.model.coun.CounList
import com.aedo.my_heaven.util.`object`.Constant.COUN_CONTENT
import com.aedo.my_heaven.util.`object`.Constant.COUN_CREATED
import com.aedo.my_heaven.util.`object`.Constant.COUN_NAME
import com.aedo.my_heaven.util.`object`.Constant.COUN_TITLE
import com.aedo.my_heaven.view.side.coun.CounSelingDetailActivity

class CounSelingAdapter(private val counseling: List<CounList>, val context: Context) :
    RecyclerView.Adapter<CounSelingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CounSelingAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.view_item_counseling, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: CounSelingAdapter.ViewHolder, position: Int) {
        holder.bind(counseling[position], context)
    }

    override fun getItemCount(): Int {
        return counseling.count()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val coun_title = itemView?.findViewById<TextView>(R.id.tv_counseling_item_title)
        val coun_created = itemView?.findViewById<TextView>(R.id.tv_counseling_item_timestamp)
        val btn_go = itemView?.findViewById<ImageView>(R.id.coun_img_go)

        fun bind(list: CounList, context: Context) {
            coun_title?.text = list.title.toString()
            coun_created?.text = list.created.toString()

            btn_go?.setOnClickListener {
                val intent = Intent(context, CounSelingDetailActivity::class.java)
                intent.putExtra(COUN_NAME, list.name.toString())
                intent.putExtra(COUN_TITLE, list.title.toString())
                intent.putExtra(COUN_CONTENT, list.content.toString())
                intent.putExtra(COUN_CREATED, list.created.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

}