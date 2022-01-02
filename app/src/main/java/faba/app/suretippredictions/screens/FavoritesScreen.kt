package faba.app.suretippredictions.screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.FirstTimeLoading
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.uicomponents.CollapsableLazyColumn
import faba.app.suretippredictions.uicomponents.NavigationItem

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun FavoritesScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean
) {

    onSetAppTitle(NavigationItem.Favorites.name)
    onTopAppBarIconsName(NavigationItem.Favorites.name)

    if (firstTimeLoading) {
        FirstTimeLoading()
    } else {
        val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values

        CollapsableLazyColumn(
            leagues = groupedLeaguesNo.toList()
                .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country })), true
        )
    }


}