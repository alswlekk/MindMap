package com.example.mindmap.Post.Service

import java.time.LocalDateTime

data class PostData(
    var key: String = "",
    var title: String = "",
    var content: String = "",
    var time: String = ""
) {
    constructor() : this("", "", "", LocalDateTime.now().toString())
}