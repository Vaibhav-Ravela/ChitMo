package com.example.chitmo.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.presentation.viewModels.DashboardViewModel
import com.example.chitmo.utils.TimeUtils

class ChitScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ChitScreen(ViewModelProvider(requireActivity())[DashboardViewModel::class.java])
            }
        }
    }
}

@Composable
fun ChitScreen(dashboardViewModel: DashboardViewModel) {
    val chitList by dashboardViewModel.chitListMutableStateFlow.collectAsState()
    LaunchedEffect(key1 = Unit) {
        dashboardViewModel.getAllChits()
    }
    ChitScreenPreview(chitList = chitList)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChitScreenPreview(
    chitList: List<Chit> = listOf(
        Chit(
            startMonthYear = "01-2025", durationInMonths = 20, amount = 100000
        ), Chit(
            startMonthYear = "01-2025", durationInMonths = 20, amount = 100000
        )
    )
) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { /*TODO*/ },
            containerColor = Color.Black,
            contentColor = Color.White,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                )
            },
            text = {
                Text(text = "Add Chit", fontSize = 15.sp)
            })
    }) { paddingValues ->
        ChitListLazyColumn(chitList = chitList, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun ChitListLazyColumn(
    chitList: List<Chit>, modifier: Modifier
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(chitList.size) {
            ChitListItem(chit = chitList[it], modifier = modifier)
        }
    }
}

@Composable
fun ChitListItem(chit: Chit, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .border(
                width = 1.dp,
                color = Color.Gray.copy(0.5f),
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
    ) {
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CurrencyRupee,
                        contentDescription = "Rupee",
                        modifier = Modifier.padding(
                                start = 10.dp, top = 10.dp, bottom = 10.dp, end = 0.dp
                            )
                    )
                    Text(text = "${chit.amount}/-", fontSize = 20.sp)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Timelapse,
                        contentDescription = "Rupee",
                        modifier = Modifier.padding(
                                start = 10.dp, top = 10.dp, bottom = 10.dp, end = 0.dp
                            )
                    )
                    Text(text = "${chit.durationInMonths} Months", fontSize = 20.sp)
                }
            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Today,
                        contentDescription = "Event",
                        modifier = Modifier.padding(
                                start = 10.dp, top = 10.dp, bottom = 10.dp, end = 5.dp
                            )
                    )
                    Text(
                        text = TimeUtils.convertMonthYearStringToReadableMonthYear(chit.startMonthYear),
                        fontSize = 20.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = "Event",
                        modifier = Modifier.padding(
                                start = 10.dp, top = 10.dp, bottom = 10.dp, end = 5.dp
                            )
                    )
                    Text(
                        text = TimeUtils.addMonthsToMonthYearAndReturnReadableMonthYear(
                            monthsToAdd = chit.durationInMonths, monthYear = chit.startMonthYear
                        ), fontSize = 20.sp
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Event",
            modifier = Modifier.padding(10.dp)
        )
    }
}