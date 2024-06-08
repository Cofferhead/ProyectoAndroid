package com.example.examen2pruebaotravez

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.ui.theme.Examen2PruebaOtraVezTheme
import com.example.examen2pruebaotravez.ui.theme.FavoriteViewModel
import com.example.examen2pruebaotravez.ui.theme.ShowDetail

class DetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val show = intent.getSerializableExtra("SHOW") as Show
        setContent{
            Examen2PruebaOtraVezTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val favoriteVM: FavoriteViewModel = viewModel(factory = FavoriteViewModel.factory)

                    var stateFav by favoriteVM.uiState

                    //favoriteVM.addFavorite(shows[0])
                    LaunchedEffect(Unit){
                        favoriteVM.getFavoriteShows()
                    }
                    var favoritos = stateFav.shows
                    val goBack = {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    //MainScreen(context = applicationContext)
                    ShowDetail(show = show, goBack = goBack, onFavorite = {favoriteVM.addFavorite(show)})
                    //Greeting(context = LocalContext.current)
                }
            }
        }

    }
}