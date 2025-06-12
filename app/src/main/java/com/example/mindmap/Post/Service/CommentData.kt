package com.example.mindmap.Post.Service

import com.google.firebase.database.ServerValue
import java.time.LocalDateTime

data class CommentData (
    var key:String = "",
    var content:String = "",
    var time: Any = ServerValue.TIMESTAMP
) {
    constructor():this("", "", ServerValue.TIMESTAMP)
}