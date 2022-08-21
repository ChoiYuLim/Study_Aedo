package com.aedo.my_heaven.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.model.list.CondoleList


class MessageRecyclerAdapter(private val messageList: List<CondoleList>, val context: Context) :
    RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.view_message_item, parent, false)
        return MessageViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageList[position], context)
    }

    override fun getItemCount(): Int {
        return messageList.count()
    }

    inner class MessageViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        val message_title = view?.findViewById<TextView>(R.id.tv_message_title)
        val message_name = view?.findViewById<TextView>(R.id.tv_message_name)
        val message_time = view?.findViewById<TextView>(R.id.tv_message_time)

        fun bind(item: CondoleList, context: Context) {
            message_title?.text = item.title
            message_name?.text = item.content
            message_time?.text = item.created
        }
    }
}