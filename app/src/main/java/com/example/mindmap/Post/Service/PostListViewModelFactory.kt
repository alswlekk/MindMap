package com.example.mindmap.Post.Service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.java

class PostListViewModelFactory(private val repository: PostListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostListViewModel::class.java)){
            return PostListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class PostListViewModel(private val repository: PostListRepository) :ViewModel(){
    private var _postList = MutableStateFlow<List<PostData>>(emptyList())
    val postList = _postList.asStateFlow()

    init {
        getAllPosts()
    }

    fun insertPost(postData: PostData){
        viewModelScope.launch {
            repository.insertPost(postData)
        }
    }

    fun getAllPosts(){
        viewModelScope.launch {
            repository.getAllPosts().collect {
                _postList.value = it
            }
        }
    }
}