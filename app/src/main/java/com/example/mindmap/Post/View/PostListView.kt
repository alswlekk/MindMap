package com.example.mindmap.Post.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindmap.R
import com.example.mindmap.Post.Service.PostListRepository
import com.example.mindmap.Post.Service.PostListViewModel
import com.example.mindmap.Post.Service.PostListViewModelFactory
import com.example.mindmap.ui.theme.MainColor
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostView(
    modifier: Modifier = Modifier,
    onNewPostNavigate: () -> Unit,
    onPostDetailNavigate: (String) -> Unit,
    onHomeNavigate:() -> Unit
) {
    val table = Firebase.database.getReference("posts")
    val viewModel: PostListViewModel =
        viewModel(factory = PostListViewModelFactory(PostListRepository(table)))
    val postListState by viewModel.postList.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(top = 24.dp)) {
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
                        onHomeNavigate()
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

        // Post List
        LazyColumn {
            items(postListState) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onPostDetailNavigate(item.key)
                        }
                        .padding(start = 12.dp, end = 12.dp)
                ) {
                    val restored = LocalDateTime.parse(item.time)
                    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    var time: String = restored.format(formatter)

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        item.title, fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        item.content, fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        time, fontSize = 15.sp,
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
    }

    // Floating Button for New Post
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            modifier = Modifier
                .padding(24.dp)
                .size(60.dp)
                .align(Alignment.BottomEnd)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                ),
            shape = CircleShape,
            onClick = {
                onNewPostNavigate()
            },
            containerColor = MainColor
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(id = R.drawable.outline_new_window_24),
                contentDescription = "",
            )
        }
    }
}