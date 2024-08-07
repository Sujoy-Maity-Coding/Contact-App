package com.sujoy.contactapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sujoy.contactapp.ui_layer.AddEditScreen
import com.sujoy.contactapp.ui_layer.ContactViewModel
import com.sujoy.contactapp.ui_layer.HomePage

@Composable
fun NavController(modifier: Modifier = Modifier, viewModel: ContactViewModel, navController: NavHostController){
    val state by viewModel.state.collectAsState()
    NavHost(navController = navController,
        startDestination =Routes.HomeScreen.route){
        composable(Routes.HomeScreen.route){
            HomePage(viewModel=viewModel, state = state, navController=navController)
        }
        composable(Routes.AddNewContact.route){
            AddEditScreen(state = state, navController = navController){
                viewModel.saveContact()
            }
        }

    }
}