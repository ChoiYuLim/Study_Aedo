package com.aedo.my_heaven.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.R
import com.aedo.my_heaven.model.list.Obituaray
import com.aedo.my_heaven.util.`object`.Constant.BURIED
import com.aedo.my_heaven.util.`object`.Constant.COFFIN_DATE
import com.aedo.my_heaven.util.`object`.Constant.DECEASED_NAME
import com.aedo.my_heaven.util.`object`.Constant.DOFP_DATE
import com.aedo.my_heaven.util.`object`.Constant.EOD_DATE
import com.aedo.my_heaven.util.`object`.Constant.LIST_IMG
import com.aedo.my_heaven.util.`object`.Constant.LLIST_ID
import com.aedo.my_heaven.util.`object`.Constant.PLACE_NAME
import com.aedo.my_heaven.util.`object`.Constant.RESIDENT_NAME
import com.aedo.my_heaven.util.`object`.Constant.TAG
import com.aedo.my_heaven.util.base.MyApplication.Companion.prefs
import com.aedo.my_heaven.util.log.LLog
import com.aedo.my_heaven.view.side.list.ListDetailActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import kotlinx.android.synthetic.main.two_button_dialog.view.*


class RecyclerAdapter(private val postList: List<Obituaray>, val context: Context)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position],context)
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it,position)
        }
    }

    interface OnItemClickListener{
        fun onClick(v:View,position: Int)
    }

    private var itemClickListener: OnItemClickListener?=null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun getItemCount(): Int {
        return postList.count()
    }


    inner class ViewHolder (itemView: View? ) : RecyclerView.ViewHolder(itemView!!){

        val tx_date = itemView?.findViewById<TextView>(R.id.list_tx_date)
        val tx_top_name = itemView?.findViewById<TextView>(R.id.tx_top_name)
        val tx_body_name  = itemView?.findViewById<TextView>(R.id.tx_body_name)
        val tx_body_info =   itemView?.findViewById<TextView>(R.id.tx_body_info)
        val btn_show = itemView?.findViewById<Button>(R.id.btn_list_show)
        val btn_share = itemView?.findViewById<Button>(R.id.btn_list_send)
        val imgname = itemView?.findViewById<ImageView>(R.id.list_img)

        @SuppressLint("ResourceType")
        fun bind(itemPhoto : Obituaray?, context: Context){
            tx_date?.text = itemPhoto?.eod.toString()
            tx_top_name?.text = itemPhoto?.deceased?.name.toString()
            tx_body_name?.text = itemPhoto?.resident?.name.toString()
            tx_body_info?.text=itemPhoto?.place.toString()
            prefs.myListId = itemPhoto?.id
            Log.d(TAG,"Recycler Img -> ${itemPhoto?.imgName.toString()}")

            btn_show?.setOnClickListener {
                val intent = Intent(context, ListDetailActivity::class.java)
                intent.putExtra(LLIST_ID,itemPhoto?.id.toString())
                intent.putExtra(DECEASED_NAME,itemPhoto?.deceased?.name.toString())
                intent.putExtra(LIST_IMG,itemPhoto?.imgName.toString())
                intent.putExtra(EOD_DATE,itemPhoto?.eod.toString())
                intent.putExtra(RESIDENT_NAME,itemPhoto?.resident?.name.toString())
                intent.putExtra(PLACE_NAME,itemPhoto?.place.toString())
                intent.putExtra(COFFIN_DATE,itemPhoto?.coffin.toString())
                intent.putExtra(DOFP_DATE,itemPhoto?.dofp.toString())
                intent.putExtra(BURIED,itemPhoto?.buried.toString())
                startActivity(itemView.context, intent, null)
            }

            btn_share?.setOnClickListener {
                KakaoSdk.init(context, "fb6d89598fd7acc40272f792dfa0dc1e")
                val myLayout = LayoutInflater.from(context).inflate(R.layout.two_button_dialog, null)
                val build = AlertDialog.Builder(context).apply {
                    setView(myLayout)
                }
                val textView : TextView = myLayout.findViewById(R.id.popTv_second)
                textView.text = "부고 공유"
                myLayout.finish_btn.text = "문자 메세지"
                myLayout.update_btn.text = "카카오톡 공유"
                val dialog = build.create()
                dialog.show()

                myLayout.finish_btn.setOnClickListener {
                    dialog.dismiss()
                }
                myLayout.update_btn.setOnClickListener {
                    kakaoShare(itemPhoto)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun kakaoShare(itemPhoto: Obituaray?) {
        val imgs = itemPhoto?.imgName?.byteInputStream()
        val bit = BitmapFactory.decodeStream(imgs)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "故 ${itemPhoto?.deceased?.name.toString()}님 별세(${itemPhoto?.place.toString()})",
                description = "임종 : ${itemPhoto?.coffin.toString()}",
                imageUrl = "http://k.kakaocdn.net/dn/m44LM/btrzKtNDLsa/ixqw0WJsnYl5r0Ax07tgvK/kakaolink40_original.png",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.aedo.my_heaven"
                )
            )
        )
        LinkClient.instance.defaultTemplate(context, defaultFeed) { linkResult, error ->
            if (error != null) {
                LLog.e("카카오링크 보내기 실패")
            }
            else if (linkResult != null) {
                context.startActivity(linkResult.intent)
                LLog.w("Warning Msg: ${linkResult.warningMsg}")
                LLog.w("Argument Msg: ${linkResult.argumentMsg}")
            }
        }
    }
}