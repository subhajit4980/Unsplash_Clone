package com.example.unplashclone.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.unplashclone.navigation.Screen
import com.example.unplashclone.screens.common.ListContent
import com.example.unsplash.screen.Home.HomeTopBar
import com.example.unsplash.screen.Home.HomeViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,context:Context,
    viewModel: HomeViewModel = hiltViewModel()) {
    val getAllImages = viewModel.getAllImages.collectAsLazyPagingItems()
    Log.d("IMAGE____COUNT____DATA", getAllImages.itemCount.toString())
    Scaffold() {
        Column() {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
            ListContent(items = getAllImages,context)
        }
    }
}