package com.aedo.my_heaven.util.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aedo.my_heaven.R
import com.aedo.my_heaven.databinding.FragmentImgDialogBinding
import com.aedo.my_heaven.util.`object`.Constant

class ImgDialog : DialogFragment() {

    private var _binding: FragmentImgDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImgDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.fragOkBtn.setOnClickListener {
            loadIMG()
            dismiss()
        }
        return view
    }

    private fun loadIMG() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}