package faba.app.suretippredictions.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
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
        val listState = rememberLazyListState()
        val leagues = groupedLeaguesNo.toList()
            .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))

        val leaguesRemembered = remember {
            groupedLeaguesNo.toList()
        }

        val collapsedState =
            rememberSaveable(leaguesRemembered) { leaguesRemembered.map { mutableStateOf(true) } }

        CollapsableLazyColumn(
            leagues,
            listState,
            collapsedState
        )
    }


}