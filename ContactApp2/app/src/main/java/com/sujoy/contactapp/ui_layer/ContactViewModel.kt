package com.sujoy.contactapp.ui_layer

import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujoy.contactapp.data.database.Contact
import com.sujoy.contactapp.data.database.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(val database: ContactDatabase):ViewModel() {
    private var isSortedBYName=MutableStateFlow(true)
    private var contact=isSortedBYName.flatMapLatest {
        if (it){
            database.dao.getAllContactsSortName()
        }else{
            database.dao.getAllContactsSortDate()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val _state= MutableStateFlow(ContactState())
    val state= combine(_state,contact,isSortedBYName){state,contact,isSortedBYName->
        state.copy(contacts = contact)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun changeSorting(){
        isSortedBYName.value=!isSortedBYName.value
    }

    fun saveContact(){
        val compressedImage = state.value.image.value.let {
            if (it.isNotEmpty()) compressImage(it) else null
        }
        val contact=Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = System.currentTimeMillis().toLong(),
            isActive = true,
            image = compressedImage
        )

        viewModelScope.launch {
            database.dao.upsertContact(contact)
        }

        state.value.id.value=0
        state.value.name.value=""
        state.value.number.value=""
        state.value.email.value=""
        state.value.dateOfCreation.value=0
        state.value.image.value=ByteArray(0)
    }

    fun DeleteContact(){
        val contact=Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            isActive = true
        )
        viewModelScope.launch {
            database.dao.deleteContact(contact)
        }
        state.value.id.value=0
        state.value.name.value=""
        state.value.number.value=""
        state.value.email.value=""
        state.value.dateOfCreation.value=0

    }
}
