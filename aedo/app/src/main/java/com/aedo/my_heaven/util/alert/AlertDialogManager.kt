package com.aedo.my_heaven.util.alert

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.PopupCustomListBinding
import com.aedo.my_heaven.model.list.ListPopup
import com.aedo.my_heaven.util.base.BaseActivity

class AlertDialogManager(baseActivity: BaseActivity) {

    private lateinit var context: Activity
    private lateinit var currentDialog: CustomDialog
    private lateinit var rentGuideDialog: Dialog

    fun AlertDialogManager(context: Activity?) {
        this.context = context!!
    }

    fun showDialog(text: CharSequence?, positive: View.OnClickListener?): CustomDialog? {
        if (context.isFinishing) {
            return null
        }
        if (currentDialog.getDialog()!!.isShowing) {
            return null
        }
        currentDialog = CustomDialog(context)
        currentDialog.text(text)?.positive(positive!!)?.cancelable(false)?.show()
        return currentDialog
    }

    fun showDialog(
        text: CharSequence?,
        positive: View.OnClickListener?,
        negative: View.OnClickListener?
    ): CustomDialog? {
        if (context.isFinishing) {
            return null
        }
        if (currentDialog.getDialog()!!.isShowing) {
            return null
        }
        currentDialog = CustomDialog(context)
        currentDialog.text(text)?.positive(positive!!)?.negative(negative!!)?.cancelable(false)
            ?.show()
        return currentDialog
    }

    fun showDialog(
        text: CharSequence?,
        positiveText: String?,
        positive: View.OnClickListener?,
        negativeText: String?,
        negative: View.OnClickListener?
    ): CustomDialog? {
        if (context.isFinishing) {
            return null
        }
        if (currentDialog.getDialog()!!.isShowing) {
            return null
        }
        currentDialog = CustomDialog(context)
        currentDialog.text(text)?.positive(positiveText!!, positive!!)
            ?.negative(negativeText!!, negative!!)?.cancelable(false)?.show()
        return currentDialog
    }

    fun dismiss() {
        currentDialog.dismiss()
    }

    fun showListPopup(listPopup: ListPopup?, listPopupListener: OnListPopupListener) {
        val binding: PopupCustomListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.popup_custom_list,
            null,
            false
        )
        val dialog = Dialog(context)
        dialog.setContentView(binding.root)

        val adapter = ListPopAdapter(listPopup, object : OnListPopupListener {
            override fun onMoveSelected(value: String?) {
                dialog.dismiss()
                listPopupListener.onMoveSelected(value)
            }

            override fun onReturnSelected(value: String?) {
                dialog.dismiss()
                listPopupListener.onReturnSelected(value)
            }

            override fun onFunctionSelected(value: String?) {
                dialog.dismiss()
                listPopupListener.onFunctionSelected(value)
            }
        })

        binding.rvList.adapter = adapter
        binding.btnCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.setCancelable(false)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        }
        dialog.show()
    }
}