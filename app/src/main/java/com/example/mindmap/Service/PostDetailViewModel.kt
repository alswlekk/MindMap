package com.example.mindmap.Service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.java

class PostDetailViewModelFactory(private val repository: PostDetailRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostDetailViewModel::class.java)){
            return PostDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class PostDetailViewModel(private val repository: PostDetailRepository) :ViewModel(){
    private var _commentList = MutableStateFlow<List<CommentData>>(emptyList())
    val commentList = _commentList.asStateFlow()
    private var _post = MutableStateFlow<PostData?>(null);
    val post = _post.asStateFlow()

    fun getPostByID(postKey: String){
        viewModelScope.launch {
            repository.getPostByID(postKey).collect {
                _post.value = it
            }
        }
    }

    fun insertComment(commentData: CommentData, postKey: String){
        viewModelScope.launch {
            repository.insertComment(commentData, postKey)
        }
    }

    fun getAllComments(postKey: String){
        viewModelScope.launch {
            repository.getAllComments(postKey).collect {
                _commentList.value = it
            }
        }
    }
}