package com.aedo.my_heaven.util.firebase

import com.aedo.my_heaven.model.firebase.ItemDTO
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    val TAG = "FirebaseRepository"

    // Firestore 초기화
    private val fireStore = FirebaseFirestore.getInstance()

    // 아이템 업로드하기
    fun uploadItem(itemDTO: ItemDTO) {
        fireStore.collection("list").document(itemDTO.timestamp.toString()).set(itemDTO)
    }

    // 아이템 삭제하기
    fun deleteItem(itemDTO: ItemDTO) {
        fireStore.collection("list").document(itemDTO.timestamp.toString()).delete()
    }


}