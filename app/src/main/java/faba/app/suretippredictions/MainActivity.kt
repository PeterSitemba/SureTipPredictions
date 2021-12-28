package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.screens.PredictionsScreen
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.uicomponents.SureScorePredictionsMain
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val predictionsViewModel: PredictionsViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(
                color = colorResource(R.color.dark_mode)
            )

            SureTipPredictionsTheme(true) {
                MainActivityScreen(predictionsViewModel, "2021-12-04")
            }

            iniObservables("2021-12-04")

            updatePredictions()


        }
    }

    private fun iniObservables(date: String) {

        //get pred number from server and use it to update local db if number is less than db

        predictionsViewModel.getPredictionsRowCount(date)?.observe(this, { pred ->
            if (pred == 0) {
                predictionsViewModel.listPredictions(date)
                predictionsViewModel.updatePredictionOdds(date)
            }
        })


    }

    private fun updatePredictions(): Job {
        return lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //predictionsViewModel.updatePrediction("2021-12-04")
                while (true) {
                    predictionsViewModel.updatePrediction("2021-12-04")
                    delay(30000)
                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun MainActivityScreen(predictionsViewModel: PredictionsViewModel, date: String) {

    val predictionItems: List<Prediction> by predictionsViewModel.roomPredictionsList(date)
        .observeAsState(listOf())

    val error by predictionsViewModel.errorMessage.observeAsState("")

    if (predictionItems.isEmpty()) {
        CircularProgressIndicator()
    } else {


        SureTipPredictionsTheme(true) {

            //if (error.isNotEmpty()) ErrorSnack(error)

            //SureScorePredictionsMain(predictionItems.filter { it.league?.id in Constants.mainLeaguesList }, error)


            SureScorePredictionsMain(predictionItems, error)

            //PredictionsScreen(predictionItems)

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