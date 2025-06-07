package com.example.mindmap.Post.Service

import com.google.firebase.database.ServerValue
import java.time.LocalDateTime

data class PostData(
    var key: String = "",
    var title: String = "",
    var content: String = "",
    var time: Any = ServerValue.TIMESTAMP
) {
    constructor() : this("", "", "", ServerValue.TIMESTAMP)
}