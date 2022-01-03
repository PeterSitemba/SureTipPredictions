package faba.app.suretippredictions.screens


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.FirstTimeLoading
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.uicomponents.CollapsableLazyColumn
import faba.app.suretippredictions.uicomponents.NavigationItem


@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun PredictionsScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean
) {

    onSetAppTitle("SureScore Predictions")
    onTopAppBarIconsName(NavigationItem.Main.name)

    if (firstTimeLoading) {
        FirstTimeLoading()
    } else {

        val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values

        CollapsableLazyColumn(
            leagues = groupedLeaguesNo.toList()
                .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country })), false
        )
    }

}


