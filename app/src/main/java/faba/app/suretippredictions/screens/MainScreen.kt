package faba.app.suretippredictions.screens


import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.ProgressDialog
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.isEmpty
import faba.app.suretippredictions.uicomponents.CollapsableLazyColumn
import faba.app.suretippredictions.uicomponents.NavigationItem
import faba.app.suretippredictions.viewmodels.PredictionsViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun PredictionsScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    predictionsViewModel: PredictionsViewModel,
) {

    onSetAppTitle("SureScore Predictions")
    onTopAppBarIconsName(NavigationItem.Main.name)

    //Log.e("loading viewmodel", predictionsViewModel.loading.value.toString())

    val loading = predictionsViewModel.loading.observeAsState(true).value
    val apiSize = predictionsViewModel.apiSize.observeAsState(0).value

    //Log.e("api size", apiSize.toString())


    if (loading || apiSize > 0 || (prediction.isEmpty() && apiSize > 0)) {
        ProgressDialog()
    } else {
        if (prediction.isEmpty()) {
            isEmpty()
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


