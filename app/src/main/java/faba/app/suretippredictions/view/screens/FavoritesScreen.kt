package faba.app.suretippredictions.view.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.view.uicomponents.*
import faba.app.suretippredictions.viewmodels.PredictionsViewModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun FavoritesScreen(
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    predictionsViewModel: PredictionsViewModel
) {

    onSetAppTitle(NavigationItem.Favorites.name)
    onTopAppBarIconsName(NavigationItem.Favorites.name)

    val predictionItems: List<Prediction> by predictionsViewModel.roomFavorites()
        .observeAsState(emptyList())

    if (predictionItems.isEmpty()) {
        NoFavorites()
    } else {

        val groupedByDates = predictionItems.groupBy{it.date}.values.toList()
        val listState = rememberLazyListState()

        FavoritesLazyColumn(
            groupedByDates,
            listState
        )
    }
}