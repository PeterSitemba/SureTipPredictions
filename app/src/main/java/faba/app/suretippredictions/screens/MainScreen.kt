package faba.app.suretippredictions.screens


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.FirstTimeLoading
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.uicomponents.CollapsableLazyColumn
import faba.app.suretippredictions.uicomponents.NavigationItem
import faba.app.suretippredictions.viewmodels.PredictionsViewModel


@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun PredictionsScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean,
    predictionsViewModel: PredictionsViewModel,
) {

    onSetAppTitle("SureScore Predictions")
    onTopAppBarIconsName(NavigationItem.Main.name)


    if (firstTimeLoading) {
        FirstTimeLoading()
    } else if (predictionsViewModel.loading.value == true) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = colorResource(R.color.colorLightBlue)
            )

        }
    } else {
        val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values
        val listState = rememberLazyListState()

        val leagues = groupedLeaguesNo.toList()
            .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))

        val collapsedState =
            rememberSaveable {
                leagues.map {
                    mutableStateOf(false)
                }
            }

        CollapsableLazyColumn(
            leagues,
            listState,
            collapsedState
        )
    }

/*
    if (firstTimeLoading) {
        FirstTimeLoading()
    } else {

        val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values
        val listState = rememberLazyListState()

        val leagues = groupedLeaguesNo.toList()
            .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))

        */
/*  val leagues = remember(groupedLeaguesNo.toList()) {
              derivedStateOf {  }
              groupedLeaguesNo.toList()
                  .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))
          }*//*


        val leaguesRemembered = remember {
            groupedLeaguesNo.toList()
        }

        val collapsedState =
            rememberSaveable(leaguesRemembered) { leaguesRemembered.map { mutableStateOf(false) } }

        CollapsableLazyColumn(
            leagues,
            listState,
            collapsedState
        )
    }
*/

}


