package com.sujoy.contactapp.ui_layer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.sujoy.contactapp.data.database.Contact

data class ContactState (
    val contacts: List<Contact> = emptyList(),
    val name:MutableState<String> = mutableStateOf(""),
    val number:MutableState<String> = mutableStateOf(""),
    val email:MutableState<String> = mutableStateOf(""),
    val dateOfCreation:MutableState<Long> = mutableStateOf(0),
    val id:MutableState<Int> = mutableStateOf(0),
    val image:MutableState<ByteArray> = mutableStateOf(ByteArray(0))
)