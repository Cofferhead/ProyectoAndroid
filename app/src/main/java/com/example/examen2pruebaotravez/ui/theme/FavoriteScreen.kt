package com.example.examen2pruebaotravez.ui.theme

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Data.Database.FavoriteShow
import com.example.examen2pruebaotravez.Data.Database.toShow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    state: List<FavoriteShow>,
    onCardClick: (show: Show) -> Unit
) {

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp)
        ) {

            items(state)
            {
                    favorite ->
                ShowCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .animateItemPlacement(
                            tween(300),
                        ),
                    show = favorite.toShow(),
                    goDetail = onCardClick
                )
            }
            /*this.items(
                items = state,
                key = { show -> show.id },
            ) { favorite ->
                ShowCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .animateItemPlacement(
                            tween(300),
                        ),
                    show = favorite.toShow(),
                    onClick = { onCardClick(favorite.id) }
                )
            }*/
        }
    }

