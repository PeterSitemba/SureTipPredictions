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
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.uicomponents.PredictionsScreen
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*


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
                PredictionActivityScreen(predictionsViewModel, "2021-12-04")
            }

            initPredictionData("2021-12-04")

            updatePredictions()


        }
    }

    private fun initPredictionData(date: String) {

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

@Composable
fun PredictionActivityScreen(predictionsViewModel: PredictionsViewModel, date: String) {

    val predictionItems: List<Prediction> by predictionsViewModel.roomPredictionsList(date)
        .observeAsState(listOf())

    if (predictionItems.isEmpty()) {
        CircularProgressIndicator()
    } else {

        val leagues: List<Int> = arrayListOf(39,71, 173,421,847,432,59,441, 149,430)

        SureTipPredictionsTheme(true) {
            PredictionsScreen(predictionItems.filter { it.league?.id in leagues })
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