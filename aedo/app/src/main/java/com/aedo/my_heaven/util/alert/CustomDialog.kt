package com.aedo.my_heaven.util.alert

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.PopupDefaultBinding
import com.aedo.my_heaven.databinding.ViewPopupButtonBinding
import com.aedo.my_heaven.databinding.ViewPopupButtonDeviderBinding
import com.aedo.my_heaven.databinding.ViewPopupButtonNegativeBinding

class CustomDialog(context: Activity) {
    private var context: Activity? = null
    private var dialog: Dialog?=null
    private var binding: PopupDefaultBinding? = null
    private var negativeBtn: ViewPopupButtonNegativeBinding? = null
    private var positiveBtn: ViewPopupButtonBinding? = null
    private var neturalBtn: ViewPopupButtonNegativeBinding? = null

    fun getDialog(): Dialog? {
        return dialog
    }

    private fun getPositiveButton(
        text: String,
        listener: View.OnClickListener
    ): ViewPopupButtonBinding {
        val buttonBinding: ViewPopupButtonBinding = DataBindingUtil.inflate(
            context!!.layoutInflater,
            R.layout.view_popup_button,
            null,
            false
        )
        buttonBinding.root.layoutParams =
            LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1F)
        buttonBinding.tvButton.text = text
        buttonBinding.clParent.setOnClickListener(listener)
        return buttonBinding
    }

    private fun getNegativeButton(
        text: String,
        listener: View.OnClickListener
    ): ViewPopupButtonNegativeBinding {
        val buttonBinding: ViewPopupButtonNegativeBinding = DataBindingUtil.inflate(
            context!!.layoutInflater,
            R.layout.view_popup_button_negative,
            null,
            false
        )
        buttonBinding.root.layoutParams =
            LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1F)
        buttonBinding.tvButton.text = text
        buttonBinding.clParent.setOnClickListener(listener)
        return buttonBinding
    }

    private fun getDevider(): ViewPopupButtonDeviderBinding {
        val deviderBinding: ViewPopupButtonDeviderBinding = DataBindingUtil.inflate(
            context!!.layoutInflater,
            R.layout.view_popup_button_devider,
            null,
            false
        )
        deviderBinding.root.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        return deviderBinding
    }

    fun text(text: CharSequence?): CustomDialog? {
        if (context != null && binding != null) {
            //view.textView.setText(text);
            binding!!.popTvDefail.text = text
        }
        return this
    }

    private fun createPositiveButton(
        text: String,
        listener: View.OnClickListener
    ): ViewPopupButtonBinding? {
        return if (context != null && binding != null) {
            //view.btnPos.setOnClickListener();
            //binding.setRightBtnUse(true);
            getPositiveButton(text) { v: View? ->
                listener.onClick(v)
                dialog!!.dismiss()
            }
        } else null
    }

    private fun createNegativeButton(
        text: String,
        listener: View.OnClickListener
    ): ViewPopupButtonNegativeBinding? {
        return if (context != null && binding != null) {
            //view.btnPos.setOnClickListener();
            //binding.setRightBtnUse(true);
            getNegativeButton(text) { view: View? ->
                listener.onClick(view)
                dialog!!.dismiss()
            }
        } else null
    }

    fun positive(text: String, listener: View.OnClickListener): CustomDialog? {
        positiveBtn = createPositiveButton(text, listener)
        return this
    }

    fun positive(listener: View.OnClickListener): CustomDialog? {
        positiveBtn = createPositiveButton(context!!.getString(R.string.ok), listener)
        return this
    }

    fun negative(text: String, listener: View.OnClickListener): CustomDialog? {
        negativeBtn = createNegativeButton(text, listener)
        return this
    }

    fun negative(listener: View.OnClickListener): CustomDialog? {
        negativeBtn = createNegativeButton(context!!.getString(R.string.cancel), listener)
        return this
    }

    fun cancelable(cancleable: Boolean): CustomDialog {
        dialog!!.setCancelable(cancleable)
        return this
    }

    private fun buildButton() {
        if (negativeBtn != null) {
            binding!!.llButton.addView(negativeBtn!!.root)
        }
        if (neturalBtn != null) {
            if (negativeBtn != null) {
                binding!!.llButton.addView(getDevider().root)
            }
            binding!!.llButton.addView(neturalBtn!!.root)
        }
        if (positiveBtn != null) {
            if (negativeBtn != null || neturalBtn != null) {
                binding!!.llButton.addView(getDevider().root)
            }
            binding!!.llButton.addView(positiveBtn!!.root)
        }
    }

    fun show() {
        if (context != null) {
            //dialog = builder.create();
            buildButton()
            val window = dialog!!.window
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog!!.show() // TODO: WindowLeaked Exception
        }
    }

    fun dismiss() {
        if (context != null && dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}