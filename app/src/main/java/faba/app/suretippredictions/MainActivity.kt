package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.viewmodels.PredictionsViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val predictionsViewModel: PredictionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            predictionsViewModel.listPredictions("2021-12-16")

            predictionsViewModel.predictionListResponse.observe(this , {
                it.forEach{ prediction ->
                    Log.e("The pred date is!! " , prediction.predictions?.winner?.comment.toString())
                }
            })

            predictionsViewModel.oddsListResponse.observe(this , {
                it.forEach{ odds ->
                    Log.e("The odd date is!! " , odds.date)
                }
            })


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