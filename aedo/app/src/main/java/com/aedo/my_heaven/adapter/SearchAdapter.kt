package com.aedo.my_heaven.adapter

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
import com.aedo.my_heaven.model.restapi.base.CreateSearch
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_BURIED
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_COFFIN_DATA
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_DECEASED_NAME
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_DOFP_DATA
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_EOD_DATA
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_PLACE
import com.aedo.my_heaven.util.`object`.Constant.SEARCH_RELATION_NAME
import com.aedo.my_heaven.view.main.detail.search.SearchDetailActivity


class SearchAdapter(private val searchlist: List<CreateSearch>, private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.view_item_search, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(searchlist[position], context)

    }

    override fun getItemCount(): Int {
        return searchlist.count()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val search_place = itemView?.findViewById<TextView>(R.id.tv_search_place)
        val search_re_name = itemView?.findViewById<TextView>(R.id.tv_search_resident_name)
        val search_de_name = itemView?.findViewById<TextView>(R.id.tv_search_de_name)
        val cl_search = itemView?.findViewById<ConstraintLayout>(R.id.cl_search)

        fun bind(list: CreateSearch?, context: Context) {
            search_place?.text = list?.place.toString()
            search_re_name?.text = list?.resident?.name.toString()
            search_de_name?.text = list?.deceased?.name.toString()

            cl_search?.setOnClickListener {
                val intent = Intent(context, SearchDetailActivity::class.java)
                intent.putExtra(SEARCH_RELATION_NAME, list?.resident?.name)
                intent.putExtra(SEARCH_EOD_DATA, list?.eod)
                intent.putExtra(SEARCH_DECEASED_NAME, list?.deceased?.name)
                intent.putExtra(SEARCH_COFFIN_DATA, list?.coffin)
                intent.putExtra(SEARCH_DOFP_DATA, list?.dofp)
                intent.putExtra(SEARCH_BURIED, list?.buried)
                intent.putExtra(SEARCH_PLACE, list?.place)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }
}