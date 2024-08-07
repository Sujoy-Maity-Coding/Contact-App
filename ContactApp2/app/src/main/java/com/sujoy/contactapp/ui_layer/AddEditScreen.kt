package com.sujoy.contactapp.ui_layer

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    state: ContactState,
    navController: NavController,
    onEvent: () -> Unit
) {
    val context = LocalContext.current
    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri?->
        if (uri != null){
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream != null){
                val byte = inputStream.readBytes()
                state.image.value = byte
            }
        }
    }
    Scaffold {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(30.dp))
//            Button(onClick = { launcher.launch("image/*") }) {
//                Text(text = "Pick Image")
//            }
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clickable {
                        launcher.launch("image/*")
                    },
                shape = CircleShape
            ) {
                if (state.image.value.isNotEmpty()) {
                    val bitmap = BitmapFactory.decodeByteArray(state.image.value, 0, state.image.value.size)
                    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
                }else{
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null,
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(50.dp),
                        tint = Color.Cyan)
                }
            }
            Spacer(modifier = Modifier.padding(30.dp))
            Card(
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                OutlinedTextField(value = state.name.value, onValueChange = { state.name.value=it },
                    placeholder = { Text(text = "Enter name",
                        color = Color.Cyan) },
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = Color.Cyan
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Card(
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                OutlinedTextField(value = state.number.value, onValueChange = { state.number.value=it },
                    placeholder = { Text(text = "Enter number",
                        color = Color.Cyan) },
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = null,
                            tint = Color.Cyan
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Card(
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                OutlinedTextField(value = state.email.value, onValueChange = { state.email.value=it },
                    placeholder = { Text(text = "Enter email",
                        color = Color.Cyan) },
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null,
                            tint = Color.Cyan
                        )
                    }
                )
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(40.dp)
                .clickable {
                    onEvent.invoke()
                    navController.navigateUp()
                },
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(Color.Cyan),
            ) {
                Text(text = "Save & Continue",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp))
            }
        }
    }
}