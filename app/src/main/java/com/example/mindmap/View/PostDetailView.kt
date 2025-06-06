package com.example.mindmap.View

import android.R.attr.fontWeight
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindmap.R
import com.example.mindmap.Service.CommentData
import com.example.mindmap.Service.PostDetailRepository
import com.example.mindmap.Service.PostDetailViewModel
import com.example.mindmap.Service.PostDetailViewModelFactory
import com.example.mindmap.ui.theme.MainColor
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailView(
    modifier: Modifier = Modifier,
    postKey: String?,
    onPostNavigate: () -> Unit
) {
    if (postKey == null) {
        onPostNavigate()
        return
    }

    val table = Firebase.database.getReference("posts")
    val viewModel: PostDetailViewModel =
        viewModel(factory = PostDetailViewModelFactory(PostDetailRepository(table)))
    val commentListState by viewModel.commentList.collectAsState(initial = emptyList())
    val postState by viewModel.post.collectAsState()
    val post = postState

    viewModel.getPostByID(postKey)
    viewModel.getAllComments(postKey)

    var content by remember {
        mutableStateOf("")
    }

    fun buildComment(): CommentData {
        return CommentData("", content, LocalDateTime.now().toString())
    }

    Column(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 4.dp)
            .fillMaxWidth()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "소통게시판",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp, top = 4.dp)
                    .size(48.dp)
                    .clickable {
                        onPostNavigate()
                    },
                painter = painterResource(id = R.drawable.outline_arrow_back_24),
                contentDescription = "",
            )
        }

        Spacer(modifier = Modifier.size(16.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.size(8.dp))

        if (post == null) {
            Text(
                text = "게시글이 삭제되었거나, 오류가 발생했습니다.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            return
        }

        // Lazy Column
        LazyColumn(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            // Post
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                ) {
                    Text(
                        text = post.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = post.content, fontSize = 16.sp)
                    Spacer(modifier = Modifier.size(12.dp))

                    Spacer(modifier = Modifier.size(4.dp))
                    val restored = LocalDateTime.parse(post.time)
                    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    var time: String = restored.format(formatter)

                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = time,
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }

            // Comment Info
            item {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainColor)
                ) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(start = 12.dp),
                        painter = painterResource(id = R.drawable.outline_comment_24),
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "댓글 " + commentListState.size.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }

            // Comment List
            items(commentListState) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                ) {
                    val restored = LocalDateTime.parse(item.time)
                    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    var time: String = restored.format(formatter)

                    Spacer(modifier = Modifier.size(8.dp))
                    Text(item.content, fontSize = 15.sp)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = time,
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
        }

        // Comment Input Field
        Surface(
            modifier = Modifier.border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
            color = MainColor,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = content,
                    onValueChange = { content = it },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = "댓글을 입력해주세요.",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Image(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(36.dp)
                        .clickable {
                            val item = buildComment()
                            viewModel.insertComment(item, postKey)
                            content = ""
                        },
                    painter = painterResource(id = R.drawable.outline_send_24),
                    contentDescription = "",
                )
            }
        }
    }
}