package faba.app.suretippredictions.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.IsEmpty
import faba.app.suretippredictions.NoInternetConnection
import faba.app.suretippredictions.ProgressDialog
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.uicomponents.CollapsableLazyColumn
import faba.app.suretippredictions.uicomponents.NavigationItem
import faba.app.suretippredictions.viewmodels.PredictionsViewModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun AllGamesScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    predictionsViewModel: PredictionsViewModel
) {

    onSetAppTitle(NavigationItem.AllGames.name)
    onTopAppBarIconsName(NavigationItem.AllGames.name)

    val loading = predictionsViewModel.loading.observeAsState(true).value
    val apiSize = predictionsViewModel.apiSize.observeAsState(0).value



    if (prediction.isEmpty() && !NetworkConnectionInterceptor(LocalContext.current).isNetworkAvailable()) {
        NoInternetConnection()
    } else if (loading || (prediction.isEmpty() && apiSize > 0)) {
        ProgressDialog()
    } else {
        if (prediction.isEmpty()) {
            IsEmpty()
        } else {
            val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values
            val listState = rememberLazyListState()

            val leagues = groupedLeaguesNo
                .sortedWith(compareBy({ it[0].league?.country }, { it[0].league?.id }))

            val collapsedState =
                rememberSaveable {
                    leagues.map {
                        mutableStateOf(true)
                    }
                }

            CollapsableLazyColumn(
                leagues,
                listState,
                collapsedState
            )
        }

    }

}