package com.example.examen2pruebaotravez.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (
    modifier: Modifier = Modifier,
    viewModel : ShowViewModel = viewModel(factory = ShowViewModel.factory),
    favoriteVM: FavoriteViewModel = viewModel(factory = FavoriteViewModel.factory),
    context: Context,
    goDetail: (show:Show) -> Unit
)
{
    val state by viewModel.uiState
    var estado by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(Unit){
        viewModel.getShows()
    }
    var shows = state.shows

    var stateFav by favoriteVM.uiState

    //favoriteVM.addFavorite(shows[0])
    LaunchedEffect(Unit){
        favoriteVM.getFavoriteShows()
    }
    var favoritos = stateFav.shows

    var show = state.showDetail
    var query by remember { mutableStateOf(" ") }
    var active by remember { mutableStateOf(false) }
    var filteredShow = shows
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

                ShowList(showList = filteredShow, goDetail = goDetail)
            }
        },
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

        }
    ) {
        Column(
            modifier = Modifier
                .background(Color(0, 0, 0))
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier
                .height(65.dp))
            TabRow(selectedTabIndex = estado,
                modifier = Modifier.background(Color(0,0,0))
            )
            {
                Tab(selected = (0==estado), modifier = Modifier.background(Color(0, 0, 0)),
                    onClick = { estado = 0 },
                    //text = { Text(text = "", maxLines = 1, color = Color(0,0,0))},
                    icon = { Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = Color(0,100,0)
                    ) }
                )
                Tab(selected = (1==estado), onClick = { estado = 1 }, modifier = Modifier.background(Color(0, 0, 0)),
                    //text = {Text(text = "Plants", color = Color(0,100,0))},
                    icon = { Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        tint = Color(0,100,0)
                    ) }
                )
            }
            if (estado == 0) {
                ShowList(showList = shows, goDetail = goDetail)
            }
            else {
                FavoriteScreen(state = favoritos, onCardClick = goDetail)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowList(
    modifier: Modifier = Modifier,
    showList : List<Show>,
    goDetail: (show:Show) -> Unit
) {


    LazyVerticalGrid(columns = GridCells.Fixed(2) )
    {
        this.items(showList)
        { showActual ->
            ShowCard(show = showActual, goDetail = goDetail)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCard(
    modifier: Modifier = Modifier,
    show: Show,
    goDetail: (show:Show) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = { goDetail(show) },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowThumbnail(
                show = show
            )

            Text(
                text = show.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 14.dp, bottom = 4.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.sp
                ),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 14.dp),
                text = show.genres.ifEmpty { listOf("No genre") }.joinToString(separator = ", "),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowThumbnail(
    modifier: Modifier = Modifier,
    show: Show
) {
    Box(
        contentAlignment = Alignment.BottomStart,
    ){
        AsyncImage(
            modifier = modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(show.image?.medium)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img)
        )
        Badge(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 10.dp, start = 10.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Text(
                text = show.rating.average.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 3.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDetail(
    modifier: Modifier = Modifier,
    show: Show,
    goBack: () -> Unit,
    onFavorite: (show:Show) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = { /*TODO*/ },
                modifier = modifier,
                actions = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                    IconButton(onClick = { onFavorite(show) }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }

                }

                )
        }
    ) {

        innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical,
                )
        ) {
            ShowDetailHeader(show = show)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            SummaryContent(show = show)

        }
    }
}

@Composable
fun SummaryContent(show: Show) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = show.toFormattedDescription(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDetailHeader(
    modifier: Modifier = Modifier,
    show: Show
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowThumbnail(
            modifier = Modifier
                .fillMaxWidth(0.42f)
                .clip(RoundedCornerShape(10.dp)),
            show = show
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = show.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            LabeledText(
                title = "Genres",
                value = show.toGenresString(),
            )
            LabeledText(
                title = "Premiered",
                value = show.toPremieredString(),
            )
            LabeledText(
                title = "Country",
                value = show.network?.country?.toString() ?: "No country",
            )
            LabeledText(
                title = "Language",
                value = show.language,
            )
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 14.dp),
//                text = show.toPremieredString(),
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Start,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 14.dp),
//                text = show.language,
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Start,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
        }
    }
}


@Composable
fun LabeledText(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    delimiter: String = ": ",
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 2,
    fontSize : TextUnit = 13.sp,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("$title$delimiter")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                )
            ) {
                append(value)
            }
        },
        modifier = modifier.padding(vertical = 6.dp) ,
        maxLines = maxLines,
        overflow = overflow,
        lineHeight = 20.sp,
    )
}
