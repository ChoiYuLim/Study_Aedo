package com.aedo.my_heaven.util.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aedo.my_heaven.databinding.ViewListItemBinding
import com.aedo.my_heaven.model.list.ListPopup
import com.aedo.my_heaven.model.list.ListPopupItem
import com.google.gson.Gson

internal class ListPopAdapter(listPopup: ListPopup?, param: OnListPopupListener) : RecyclerView.Adapter<ListPopAdapter.ListPopViewHolder?>() {

    private var listPopup: ListPopup? = null
    private var listener: OnListPopupListener? = null
    private var gson: Gson? = null

    fun ListPopAdapter(listPopup: ListPopup?, listener: OnListPopupListener?) {
        this.listPopup = listPopup
        this.listener = listener
        gson = Gson()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPopViewHolder {
        return ListPopViewHolder(
            ViewListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ListPopViewHolder,
        position: Int
    ) {
        val action: String? = listPopup?.action
        val item: ListPopupItem = listPopup?.item!![position]
        val name: String? = item.text
        val value =
            if (item.value is String) item.value else gson!!.toJson(item.value)
        holder.binding.text = name
        holder.binding.root.setOnClickListener {
            when {
                action.equals("move", ignoreCase = true) -> {
                    listener?.onMoveSelected(value)
                }
                action.equals("return", ignoreCase = true) -> {
                    listener?.onReturnSelected(value)
                }
                action.equals("function", ignoreCase = true) -> {
                    listener?.onFunctionSelected(value)
                }
                else -> {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listPopup?.item!!.size
    }

    internal class ListPopViewHolder(var binding: ViewListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}
