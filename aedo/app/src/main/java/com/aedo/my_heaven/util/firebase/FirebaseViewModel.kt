package com.aedo.my_heaven.util.firebase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aedo.my_heaven.model.firebase.ItemDTO


class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FirebaseRepository = FirebaseRepository()

    // 아이템 업로드하기
    fun uploadItem(itemDTO: ItemDTO) {
        repository.uploadItem(itemDTO)
    }

    // 아이템 삭제하기
    fun deleteItem(itemDTO: ItemDTO) {
        repository.deleteItem(itemDTO)
    }


}