package com.example.mindmap.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import com.example.mindmap.Service.PostListRepository
import com.example.mindmap.Service.PostListViewModel
import com.example.mindmap.Service.PostListViewModelFactory
import com.example.mindmap.Service.PostData
import com.example.mindmap.ui.theme.completeColor
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostView(modifier: Modifier = Modifier, onPostNavigate: () -> Unit) {
    val table = Firebase.database.getReference("posts")
    val viewModel: PostListViewModel =
        viewModel(factory = PostListViewModelFactory(PostListRepository(table)))

    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }

    fun buildPost(): PostData {
        return PostData("", title, content, LocalDateTime.now().toString())
    }

    val isActive by remember(title, content) {
        derivedStateOf {
            title.isNotEmpty() && content.isNotEmpty()
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "글 쓰기",
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
            Text(
                text = "완료",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp, top = 10.dp)
                    .size(48.dp)
                    .clickable {
                        if (isActive) {
                            val item = buildPost()
                            viewModel.insertPost(item)
                            title = ""
                            content = ""
                            onPostNavigate()
                        }
                    },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) completeColor else Color.Gray
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            placeholder = {
                Text(
                    text = "제목을 입력해주세요.",
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
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            value = content,
            onValueChange = { content = it },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            placeholder = {
                Text(
                    text = "이용자들과 자유롭게 대화해 보세요.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}