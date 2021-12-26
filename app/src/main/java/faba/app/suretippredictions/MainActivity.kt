package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val predictionsViewModel: PredictionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            predictionsViewModel.getPredictionsRowCount("2021-12-25")?.observe(this, { pred ->
                if (pred == 0) {
                    predictionsViewModel.listPredictions(
                        "2021-12-25"
                    )
                }
                predictionsViewModel.getOddsRowCount("2021-12-25")?.observe(this, { odd ->

                    if (pred == odd && pred != 0 && odd != 0) {
                        predictionsViewModel.roomPredictionsAndOddsList("2021-12-25")
                            .observe(this, {
                                it.forEach { predOdds ->

                                }
                            })
                    } else {
                        predictionsViewModel.listPredictions(
                            "2021-12-25"
                        )
                    }
                    predictionsViewModel.predCounterResponse.observe(this, { predApi ->
                        predictionsViewModel.oddCounterResponse.observe(this, { oddApi ->


                        })

                    })

                })
            })

            //updatePredictions()


        }
    }

    private fun updatePredictions(): Job {
        return lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    predictionsViewModel.updatePrediction("2021-12-25")
                    delay(30000)
                }
            }
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