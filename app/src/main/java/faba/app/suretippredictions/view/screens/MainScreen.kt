package faba.app.suretippredictions.view.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.utils.Constants
import faba.app.suretippredictions.view.uicomponents.*
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.launch


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

    val enterScaleIn = remember {
        scaleIn(
            animationSpec = TweenSpec(
                durationMillis = Constants.SCALE_IN_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        )
    }
    val exitScaleOut = remember {
        scaleOut(
            animationSpec = TweenSpec(
                durationMillis = Constants.SCALE_OUT_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }

    val loading = predictionsViewModel.loading.observeAsState(true).value
    val apiSize = predictionsViewModel.apiSize.observeAsState(0).value

    if (prediction.isEmpty() && !NetworkConnectionInterceptor(LocalContext.current).isNetworkAvailable()) {
        NoInternetConnection()
    } else if (loading || (prediction.isEmpty() && apiSize > 0) || (prediction.isEmpty() && predictionsViewModel.localSize.value!! > 0)) {
        ProgressDialog()
    } else if (prediction.isEmpty() && predictionsViewModel.localSize.value == 0) {
        IsEmpty()
    } else {
        val groupedLeaguesNo = prediction.groupBy { it.league?.id }.values
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val leagues = groupedLeaguesNo.toList()
            .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))

        val collapsedState =
            rememberSaveable {
                leagues.map {
                    mutableStateOf(false)
                }
            }
        Box {

            CollapsableLazyColumn(
                leagues,
                listState,
                collapsedState
            )

            AnimatedVisibility(
                visible = listState.firstVisibleItemIndex > 0,
                enter = enterScaleIn,
                exit = exitScaleOut,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 72.dp, end = 12.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    backgroundColor = colorResource(R.color.colorLightBlue),
                    contentColor = colorResource(R.color.white)

                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, "")
                }


            }

        }


    }
}



