package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.database.PredictionAndOdds
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.uicomponents.PredictionsScreen
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val predictionsViewModel: PredictionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(
                color = colorResource(R.color.dark_mode)
            )

            SureTipPredictionsTheme(true) {
                PredictionActivityScreen(predictionsViewModel, "2021-12-25")
            }

            initPredictionData("2021-12-25")

            updatePredictions()


        }
    }

    private fun initPredictionData(date : String){

        //get pred number from server and use it to update local db if number is less than db

        predictionsViewModel.getPredictionsRowCount(date)?.observe(this, { pred ->
            if (pred == 0) {
                predictionsViewModel.listPredictions(
                    date
                )
            }
            predictionsViewModel.getOddsRowCount(date)?.observe(this, { odd ->

              /*  if (pred == odd && pred != 0 && odd != 0) {
                    predictionsViewModel.roomPredictionsAndOddsList(date)
                        .observe(this, {
                            it.forEach { predOdds ->

                            }
                        })
                }*/
/*
                predictionsViewModel.predCounterResponse.observe(this, { predApi ->
                    predictionsViewModel.oddCounterResponse.observe(this, { oddApi ->

                    })

                })
*/

            })
        })

    }

    private fun updatePredictions(): Job {
        return lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                predictionsViewModel.updatePrediction("2021-12-25")
               /* while (true) {
                    predictionsViewModel.updatePrediction("2021-12-25")
                    delay(30000)
                }*/
            }
        }
    }

}

@Composable
fun PredictionActivityScreen(predictionsViewModel: PredictionsViewModel, date: String) {

    val predictionItems: List<PredictionAndOdds> by predictionsViewModel.roomPredictionsAndOddsList(date)
        .observeAsState(listOf())

    if (predictionItems.isEmpty()) {
        CircularProgressIndicator()
    } else {
        SureTipPredictionsTheme(true) {
            PredictionsScreen(predictionItems)
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SureTipPredictionsTheme {
        //PredictionList("Android")
    }
}