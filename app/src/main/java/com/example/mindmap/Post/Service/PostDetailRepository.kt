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

class PostDetailRepository(private val table: DatabaseReference) {
    fun getPostByID(postKey : String): Flow<PostData?> = callbackFlow {
        val postRef = table.child(postKey)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(PostData::class.java)
                trySend(post)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        postRef.addValueEventListener(listener)
        awaitClose {
            postRef.removeEventListener(listener)
        }
    }

    suspend fun insertComment(commentData: CommentData, postKey : String) {
        val commentRef = table.child(postKey).child("comments")
        try {
            var curComment = commentRef.push()
            val curKey = curComment.key ?: throw Exception("")
            var curCommentData = commentData.copy(key = curKey)
            curComment
                .setValue(curCommentData)
                .await()
            Log.i("insert", "성공")

        } catch (e: Exception) {
            Log.i("insert", "실패")
        }
    }

    fun getAllComments(postKey : String): Flow<List<CommentData>> = callbackFlow {
        val commentRef = table.child(postKey).child("comments")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val commentList = snapshot.children.mapNotNull {
                    it.getValue(CommentData::class.java)
                }.sortedBy {
                    if(it.time is Long) it.time as Long
                    else 0L
                }
                trySend(commentList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        commentRef.addValueEventListener(listener)
        awaitClose {
            commentRef.removeEventListener(listener)
        }
    }
}