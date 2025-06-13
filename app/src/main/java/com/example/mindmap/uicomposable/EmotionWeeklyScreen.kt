package com.example.mindmap.uicomposable

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mindmap.model.SelfCheckRecord
import com.example.mindmap.util.SelfCheckEvaluator
import com.example.mindmap.util.formatDate
import com.example.mindmap.util.getLevelColor
import com.example.mindmap.viewmodel.SelfCheckViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.Locale
import android.graphics.Color as AndroidColor

@Composable
fun EmotionWeeklyScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val application = context.applicationContext as Application
            return SelfCheckViewModel(application) as T
        }
    }
    val viewModel: SelfCheckViewModel = viewModel(factory = viewModelFactory)

    LaunchedEffect(Unit) {
        viewModel.loadRecords()

    }

    val allRecords by viewModel.allRecords.collectAsState()
    val graphRecords by viewModel.graphRecords.collectAsState()

    EmotionWeeklyScreenContent(
        navController = navController,
        allRecords = allRecords,
        graphRecords = graphRecords
    )
}

@Composable
fun EmotionWeeklyScreenContent(
    navController: NavController,
    allRecords: List<SelfCheckRecord>,
    graphRecords: List<SelfCheckRecord>
) {
    var viewMode by remember { mutableStateOf("graph") }
    var selectedRecord by remember { mutableStateOf<SelfCheckRecord?>(null) }

    val sdf = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val monthAgo = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -30) }.time

    val filteredGraphRecords = graphRecords.filter { sdf.parse(it.date)?.after(monthAgo) == true }
    val filteredAllRecords = allRecords.filter { sdf.parse(it.date)?.after(monthAgo) == true }

    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "뒤로가기")
            }

            Text(
                text = "감정 기록",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, Color.Black, shape = RectangleShape)
        ) {
            val selectedColor = Color(0xFFD8F8D4)
            val unselectedColor = Color.White

            Button(
                onClick = { viewMode = "graph" },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewMode == "graph") selectedColor else unselectedColor,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(vertical = 0.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("그래프")
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Black)
            )

            Button(
                onClick = { viewMode = "list" },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewMode == "list") selectedColor else unselectedColor,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(vertical = 0.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("리스트")
            }
        }

        Spacer(Modifier.height(24.dp))

        if (viewMode == "graph") {
            Text(
                "월간 감정 그래프",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(12.dp))
            LineChartWithSelection(filteredGraphRecords) { selectedRecord = it }
            Spacer(Modifier.height(16.dp))
            selectedRecord?.let {
                EmotionGraphDetailCard(it)
            }
        } else {
            Text(
                "최근 한 달 감정 리스트",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(filteredAllRecords) {
                    EmotionListCard(it)
                }
            }
        }
    }
}

@Composable
fun EmotionGraphDetailCard(record: SelfCheckRecord) {
    val (level, _) = SelfCheckEvaluator().evaluate(record.score)
    val color = getLevelColor(level)

    Column(modifier = Modifier.padding(12.dp)) {
        Text("${formatDate(record.date)}", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(4.dp))
        Text("진단 점수 ${record.score}점", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(4.dp))
        Text("$level", color = color, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))

        if (record.memo.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD8F8D4))
            ) {
                Text(
                    text = "${record.memo}",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun EmotionListCard(record: SelfCheckRecord) {
    val (level, _) = SelfCheckEvaluator().evaluate(record.score)
    val color = getLevelColor(level)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD8F8D4))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${formatDate(record.date)}", fontWeight = FontWeight.Bold)
                Text(
                    text = "${record.score}점",
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            }
            if (record.memo.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("${record.memo}")
            }
        }
    }
}

@Composable
fun LineChartWithSelection(
    records: List<SelfCheckRecord>,
    onPointSelected: (SelfCheckRecord) -> Unit
) {
    val context = LocalContext.current
    val chartRef = remember { mutableStateOf<LineChart?>(null) }

    AndroidView(factory = {
        LineChart(context).apply {
            chartRef.value = this
            setupChart(this, records, onPointSelected)
        }
    }, update = {
        setupChart(it, records, onPointSelected)
    }, modifier = Modifier
        .fillMaxWidth()
        .height(300.dp))
}

private fun setupChart(
    chart: LineChart,
    records: List<SelfCheckRecord>,
    onPointSelected: (SelfCheckRecord) -> Unit
) {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val baseDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -30) }.time

    val entries = records.mapNotNull { record ->
        val date = sdf.parse(record.date)
        if (date != null) {
            val daysFormBase = ((date.time - baseDate.time) / (1000 * 60 * 60 * 24)).toFloat()
            Entry(daysFormBase, record.score.toFloat())
        } else null
    }

    val dataSet = LineDataSet(entries, "감정 점수").apply {
        color = AndroidColor.parseColor("#388E3C")
        lineWidth = 3f
        setDrawValues(false)
        setDrawCircles(true)
        circleRadius = 5f
        setCircleColor(AndroidColor.parseColor("#388E3C"))
        setCircleHoleColor(AndroidColor.parseColor("#388E3C"))
        setDrawCircleHole(true)
    }

    chart.data = LineData(dataSet)

    chart.apply {
        xAxis.apply {
            isEnabled = false
        }

        axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 27f
            granularity = 5f
            setDrawGridLines(true)
        }

        axisRight.isEnabled = false
        description.text = ""
        legend.isEnabled = false

        setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                e?.x?.let { selectedX ->
                    val matched = records.firstOrNull {
                        val date = sdf.parse(it.date)
                        date != null && ((date.time - baseDate.time) / (1000 * 60 * 60 * 24)).toFloat() == selectedX
                    }
                    matched?.let { onPointSelected(it) }
                }
            }

            override fun onNothingSelected() {}
        })

        invalidate()
    }
}