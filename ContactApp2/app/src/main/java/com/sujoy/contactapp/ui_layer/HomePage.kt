package com.sujoy.contactapp.ui_layer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sujoy.contactapp.navigation.Routes
import com.sujoy.contactapp.ui.theme.Orange
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: ContactViewModel, state: ContactState, navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.clickable {
                viewModel.changeSorting()
            },
            title = { Icon(Icons.Rounded.Menu, contentDescription = null,
                tint = Color.Cyan,
                modifier = Modifier.padding(start = 10.dp))
        })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.AddNewContact.route)
                },
                containerColor = Color.Cyan){
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null,
                    modifier = Modifier.size(30.dp))
            }}){
        Toast.makeText(context, "Please Give Phone Permission", Toast.LENGTH_SHORT).show()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(state.contacts){
                var bitmap:Bitmap?=null
                if (it.image!=null){
                    bitmap= BitmapFactory.decodeByteArray(it.image,0,it.image!!.size)
                }
                Card(modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(15.dp)
                    .clickable {
                        state.id.value = it.id
                        state.number.value = it.number
                        state.email.value = it.email
                        state.name.value = it.name
                        state.dateOfCreation.value = it.dateOfCreation
                        if (it.image != null) {
                            state.image.value = it.image!!
                        }
                        navController.navigate(Routes.AddNewContact.route)
                    },
                    elevation = CardDefaults.cardElevation(10.dp)) {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(modifier = Modifier.padding(start = 10.dp)) {
                        if (bitmap!=null){
                            Image(bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape))
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        Column {
                            Text(text = it.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold)
                            Text(text = it.number,
                                fontSize = 18.sp)
                            Text(text = it.email,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Light)
                        }
                    }
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Card(modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .padding(10.dp),
                            elevation = CardDefaults.cardElevation(10.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(Color.Red)) {
                            Icon(imageVector = Icons.Rounded.Delete, contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        state.id.value = it.id
                                        state.number.value = it.number
                                        state.email.value = it.email
                                        state.name.value = it.name
                                        state.dateOfCreation.value = it.dateOfCreation
                                        viewModel.DeleteContact()
                                    })
                        }
                        Card(modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .padding(10.dp),
                            elevation = CardDefaults.cardElevation(10.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(Color.Green)){
                            Icon(imageVector = Icons.Rounded.Call, contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${it.number}"))
                                        context.startActivity(intent)
                                    })
                        }
                    }
//                    Button(onClick = {
//                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${it.number}"))
//                        context.startActivity(intent)
//                    }) {
//                        Text(text = "Make Call")
//                    }

                }
            }
        }
    }
}