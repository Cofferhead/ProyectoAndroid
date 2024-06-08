package com.example.examen2pruebaotravez

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen2pruebaotravez.ui.theme.Examen2PruebaOtraVezTheme
import com.example.examen2pruebaotravez.ui.theme.ShowViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.ui.theme.MainScreen
import com.example.examen2pruebaotravez.ui.theme.ShowCard
import com.example.examen2pruebaotravez.ui.theme.ShowDetail
import com.example.examen2pruebaotravez.ui.theme.ShowList

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen2PruebaOtraVezTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val goDetail = {show:Show ->
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("SHOW", show)
                        startActivity(intent)
                    }
                    MainScreen(context = applicationContext, goDetail = goDetail)
                    //Greeting(context = LocalContext.current)
                }
            }
        }
    }
}
/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting( modifier: Modifier = Modifier,
              viewModel : ShowViewModel = viewModel(factory = ShowViewModel.factory),
              context:Context
              ) {

    val state by viewModel.uiState

    LaunchedEffect(Unit){
        viewModel.getShows()
    }
    var shows = state.shows
    var len = shows.size
    LaunchedEffect(Unit){
        viewModel.getShowDetail(50)
    }

    var show = state.showDetail
    /*LazyColumn {
        item {
            /*for (serie in shows)
            {
                Text(text = serie.toString())
                Text("POR AQUI AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            }*/
            Text(text = show.toString())
        }
    }*/
    /*Text(
        text = "Hello ${state.shows.toString()}!",
        modifier = modifier
    )*/
    //ShowCard(show = show)
    //ShowList(showList = shows)
    //ShowDetail(show = show)
    //var query by remember { mutableStateOf(" ") }
    //var active by remember { mutableStateOf(false)}

    var query by remember { mutableStateOf(" ") }
    var active by remember { mutableStateOf(false)}
    var filteredShow = shows
    /*
    ShowList(showList = filteredShow)
    SearchBar(
        query = query,
        onQueryChange = {query = it},
        onSearch = {
                   Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
            active = false
        },
        active = true,
        onActiveChange = {active = it}) {
        filteredShow = shows.filter { it.name.contains(query, true) }
        //ShowList(showList = filteredShow)
    } */
    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChange = {query = it},
                onSearch = {
                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                    active = false
                },
                active = active,
                onActiveChange = {active = it}
            ) {
                filteredShow = shows.filter { it.name.contains(query, true) }

                ShowList(showList = filteredShow)
            }
        },
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

        }
    ) {
        ShowList(showList = shows)
    }
}*/