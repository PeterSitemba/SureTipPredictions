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


            predictionsViewModel.getPredictionsRowCount("2021-12-18")?.observe(this, { pred ->
                if (pred == 0) {
                    predictionsViewModel.listPredictions(
                        "2021-12-18"
                    )
                }
                predictionsViewModel.getOddsRowCount("2021-12-18")?.observe(this, { odd ->

                    Log.e(
                        "The pred no is!! ",
                        pred.toString()
                    )

                    Log.e(
                        "The odd no is!! ",
                        odd.toString()
                    )

                    if (pred == odd && pred != 0 && odd != 0) {
                        predictionsViewModel.roomPredictionsAndOddsList("2021-12-18")
                            .observe(this, {
                                it.forEach { predOdds ->
                                    Log.e(
                                        "The pred id is!! ",
                                        predOdds.prediction.predictions?.winner?.comment.toString()
                                    )
                                    Log.e("The odd id is!! ", predOdds.odds.id.toString())
                                }
                            })
                    }
                    predictionsViewModel.predCounterResponse.observe(this, { predApi ->
                        predictionsViewModel.oddCounterResponse.observe(this, { oddApi ->

                            if (pred != 0 && pred == odd && predApi == pred && oddApi == odd) {
                                predictionsViewModel.roomPredictionsAndOddsList("2021-12-18")
                                    .observe(this, {
                                        it.forEach { predOdds ->
                                            Log.e(
                                                "The pred id is!! ",
                                                predOdds.prediction.predictions?.winner?.comment.toString()
                                            )
                                            Log.e("The odd id is!! ", predOdds.odds.id.toString())

                                        }
                                    })
                            }
                        })

                    })

                })
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