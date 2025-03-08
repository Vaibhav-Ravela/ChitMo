package com.example.chitmo.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chitmo.data.local.entities.Participant
import com.example.chitmo.presentation.viewModels.DashboardViewModel
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.util.Locale

class NewChitScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NewChitScreen(ViewModelProvider(requireActivity())[DashboardViewModel::class.java])
            }
        }
    }
}

@Composable
fun NewChitScreen(dashboardViewModel: DashboardViewModel) {
    val participantsList by dashboardViewModel.participantListMutableStateFlow.collectAsState()
    NewChitScreenPreview(participantsList)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewChitScreenPreview(
    participantsList: List<Participant> = listOf(
        Participant(
            name = "John", phoneNumber = "8688387949"
        )
    )
) {
    val (chosenAmountButton, setChosenAmountChosen) = remember { mutableIntStateOf(-1) }
    val (customAmount, setCustomAmount) = remember { mutableStateOf("") }
    val (chosenDurationButton, setChosenDurationChosen) = remember { mutableIntStateOf(-1) }
    val (customDuration, setCustomDuration) = remember { mutableStateOf("") }
    val (chooseParticipantDropDownExpanded, setChooseParticipantDropDownExpanded) = remember {
        mutableStateOf(
            false
        )
    }
    val (searchText, setSearchText) = remember { mutableStateOf("") }
    val selectedParticipants = remember { mutableStateListOf<Participant>() }
    val (showAddParticipantDialog, setShowAddParticipantDialog) = remember { mutableStateOf(false) }
    val (showDatePicker, setShowDatePicker) = remember { mutableStateOf(false) }
    val (selectedYearMonth, setSelectedYearMonth) = remember { mutableStateOf(YearMonth.now()) }
    val chosenAmount = when (chosenAmountButton) {
        0 -> 50000
        1 -> 100000
        2 -> 200000
        3 -> customAmount.toIntOrNull() ?: 0
        else -> 0
    }
    val chosenDuration = when (chosenDurationButton) {
        0 -> 10
        1 -> 20
        2 -> 30
        3 -> customDuration.toIntOrNull() ?: 0
        else -> 0
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Choose amount",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline
        )
        AmountFlowRow(
            chosenAmountButton, setChosenAmountChosen, customAmount, setCustomAmount
        )
        Text(
            text = "Choose Duration",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(top = 20.dp)
        )
        DurationFlowRow(
            chosenDurationButton, setChosenDurationChosen, customDuration, setCustomDuration
        )
        Text(
            text = "Choose Start Date",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(top = 20.dp)
        )
        Button(
            onClick = { setShowDatePicker(true) }, colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                text = "${
                    selectedYearMonth.month.toString().lowercase(Locale.ROOT)
                }, ${selectedYearMonth.year}"
            )
            Icon(
                imageVector = Icons.Default.Event,
                contentDescription = "Add",
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        if (showDatePicker) {
            MonthYearDatePicker(setShowDatePicker, setSelectedYearMonth)
        }
        Text(
            text = "Choose Participants",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(top = 20.dp)
        )
        SelectParticipantsDropDown(
            participantsList,
            chooseParticipantDropDownExpanded,
            setChooseParticipantDropDownExpanded,
            searchText,
            setSearchText,
            selectedParticipants,
            setShowAddParticipantDialog,
        )
        SelectedParticipantsLazyColumn(
            selectedParticipants, modifier = Modifier.padding(top = 20.dp)
        )
        if (showAddParticipantDialog) {
            AddParticipantDialog(setShowAddParticipantDialog)
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black,
            ),
            modifier = Modifier.align(CenterHorizontally),
            enabled = chosenAmountButton != -1 && chosenDurationButton != -1 && selectedParticipants.size != chosenDuration
        ) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AmountFlowRow(
    chosenAmountButton: Int,
    setChosenAmountButton: (Int) -> Unit,
    customAmount: String,
    setCustomAmount: (String) -> Unit
) {
    val (isAmountError, setIsAmountError) = remember { mutableStateOf(false) }
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { setChosenAmountButton(0) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenAmountButton == 0) Color.Black else Color.White,
                contentColor = if (chosenAmountButton == 0) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "50,000/-")
        }
        Button(
            onClick = { setChosenAmountButton(1) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenAmountButton == 1) Color.Black else Color.White,
                contentColor = if (chosenAmountButton == 1) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "1,00,000/-")
        }
        Button(
            onClick = { setChosenAmountButton(2) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenAmountButton == 2) Color.Black else Color.White,
                contentColor = if (chosenAmountButton == 2) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "2,00,000/-")
        }
        Button(
            onClick = { setChosenAmountButton(3) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenAmountButton == 3) Color.Black else Color.White,
                contentColor = if (chosenAmountButton == 3) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "Custom")
        }
        if (chosenAmountButton == 3) {
            Column {
                OutlinedTextField(value = customAmount,
                    onValueChange = { text ->
                        setCustomAmount(text)
                        setIsAmountError(!(text.all { it.isDigit() } && text.toInt() >= 50000))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(text = "Enter amount")
                    },
                    modifier = Modifier.width(200.dp),
                    singleLine = true
                )
                if (isAmountError) {
                    Text(
                        text = "Enter a valid amount above 50,000/-",
                        color = Color.Red,
                        modifier = Modifier.width(200.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DurationFlowRow(
    chosenDurationButton: Int,
    setChosenDurationButton: (Int) -> Unit,
    customDuration: String,
    setCustomDuration: (String) -> Unit
) {
    val (isDurationError, setIsDurationError) = remember { mutableStateOf(false) }
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { setChosenDurationButton(0) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenDurationButton == 0) Color.Black else Color.White,
                contentColor = if (chosenDurationButton == 0) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "10 Months")
        }
        Button(
            onClick = { setChosenDurationButton(1) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenDurationButton == 1) Color.Black else Color.White,
                contentColor = if (chosenDurationButton == 1) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "20 Months")
        }
        Button(
            onClick = { setChosenDurationButton(2) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenDurationButton == 2) Color.Black else Color.White,
                contentColor = if (chosenDurationButton == 2) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "30 Months")
        }
        Button(
            onClick = { setChosenDurationButton(3) },
            modifier = Modifier.padding(end = 10.dp),
            shape = RoundedCornerShape(
                CornerSize(15.dp)
            ),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonColors(
                containerColor = if (chosenDurationButton == 3) Color.Black else Color.White,
                contentColor = if (chosenDurationButton == 3) Color.White else Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "Custom")
        }
        if (chosenDurationButton == 3) {
            Column {
                OutlinedTextField(value = customDuration,
                    onValueChange = { text ->
                        setCustomDuration(text)
                        setIsDurationError(!(text.all { it.isDigit() } && text.toInt() >= 10))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(text = "Enter Duration")
                    },
                    modifier = Modifier.width(200.dp),
                    singleLine = true
                )
                if (isDurationError) {
                    Text(
                        text = "Enter valid months above 10",
                        color = Color.Red,
                        modifier = Modifier.width(200.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearDatePicker(
    setShowDatePicker: (Boolean) -> Unit, setSelectedYearMonth: (YearMonth) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(onDismissRequest = { setShowDatePicker(false) }, confirmButton = {
        TextButton(onClick = {
            val selectedMillis = datePickerState.selectedDateMillis ?: return@TextButton
            val selectedDate =
                Instant.ofEpochMilli(selectedMillis).atZone(ZoneId.systemDefault()).toLocalDate()
            setSelectedYearMonth(YearMonth.of(selectedDate.year, selectedDate.month))
            setShowDatePicker(false)
        }) {
            Text("OK", color = Color.Black)
        }
    }, dismissButton = {
        TextButton(onClick = { setShowDatePicker(false) }) {
            Text("Cancel", color = Color.Black)
        }
    }) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectParticipantsDropDown(
    participantsList: List<Participant>,
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    searchText: String,
    setSearchText: (String) -> Unit,
    selectedParticipants: SnapshotStateList<Participant>,
    setShowAddParticipantDialog: (Boolean) -> Unit,
) {
    val filteredParticipants =
        participantsList.filter { it.name.contains(searchText, ignoreCase = true) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = setExpanded) {
        OutlinedTextField(value = searchText,
            onValueChange = {
                setSearchText(it)
                setExpanded(it.isNotEmpty())
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Search Participant") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            singleLine = true
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { setExpanded(false) }) {
            if (filteredParticipants.isEmpty()) {
                DropdownMenuItem(text = {
                    Icon(Icons.Default.Add, "Add")
                    Text(text = "Add New Participant")
                }, onClick = {
                    setShowAddParticipantDialog(true)
                    setExpanded(false)
                })
            }
            filteredParticipants.forEach {
                DropdownMenuItem(text = {
                    Text(text = it.name)
                }, onClick = {
                    selectedParticipants.add(it)
                    setExpanded(false)
                })
            }
        }
    }
}

@Composable
fun SelectedParticipantsLazyColumn(
    selectedParticipants: SnapshotStateList<Participant>, modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        items(selectedParticipants.size) {
            Row(
                modifier = Modifier.background(Color.Black, RoundedCornerShape(20.dp)),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = "${it + 1}. ${selectedParticipants[it].name}",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                IconButton(
                    onClick = { selectedParticipants.removeAt(it) },
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Remove",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AddParticipantDialog(setShowAddParticipantDialog: (Boolean) -> Unit) {
    val (name, setName) = remember { mutableStateOf("") }
    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }
    val (isNameError, setIsNameError) = remember { mutableStateOf(false) }
    val (isPhoneNumberError, setIsPhoneNumberError) = remember { mutableStateOf(false) }
    AlertDialog(onDismissRequest = { setShowAddParticipantDialog(false) },
        containerColor = Color.White,
        confirmButton = {
            Button(
                onClick = {
                    //todo
                    setShowAddParticipantDialog(false)
                }, colors = ButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                ), enabled = isNameError || isPhoneNumberError, modifier = Modifier.width(91.dp)
            ) {
                Text(text = "Add")
            }
        },
        title = { Text(text = "Add New Participant", fontWeight = FontWeight.SemiBold) },
        text = {
            Column {
                OutlinedTextField(value = name,
                    onValueChange = { text ->
                        setName(text)
                        setIsNameError(!text.all { it.isLetterOrDigit() })
                    },
                    modifier = Modifier.padding(bottom = 10.dp),
                    label = { Text(text = "Name") },
                    isError = isNameError,
                    singleLine = true
                )
                OutlinedTextField(value = phoneNumber,
                    onValueChange = { text ->
                        setPhoneNumber(text)
                        setIsPhoneNumberError(!text.all { it.isDigit() })
                    },
                    label = { Text(text = "Phone Number") },
                    isError = isPhoneNumberError,
                    singleLine = true
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { setShowAddParticipantDialog(false) }, colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                ), border = BorderStroke(1.dp, Color.Black), modifier = Modifier.width(91.dp)
            ) {
                Text(text = "Cancel")
            }
        })

}
