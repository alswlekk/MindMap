package com.example.mindmap.Post.Service

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.jvm.java

class PostListRepository(private val table: DatabaseReference) {
    suspend fun insertPost(postData: PostData) {
        try {
            var curPost = table.push()
            val curKey = curPost.key ?: throw Exception("")
            var curPostData = postData.copy(key = curKey)
            curPost
                .setValue(curPostData)
                .await()

            Log.i("insert", "성공")

        } catch (e: Exception) {
            Log.i("insert", "실패")
        }
    }

    fun getAllPosts(): Flow<List<PostData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = snapshot.children.mapNotNull {
                    it.getValue(PostData::class.java)
                }.sortedByDescending { it.time }
                trySend(postList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }
}