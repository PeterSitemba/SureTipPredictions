package faba.app.suretippredictions.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.FirstTimeLoading
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.uicomponents.NavigationItem

@ExperimentalCoilApi
@Composable
fun FavoritesScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean
) {

    onSetAppTitle(NavigationItem.Favorites.name)
    onTopAppBarIconsName(NavigationItem.Favorites.name)

    if (firstTimeLoading) {
        FirstTimeLoading()
    } else {
        LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
            items(prediction) { prediction ->

                var predictionOutcome by remember { mutableStateOf("") }
                when (prediction.predictionString) {
                    "Home Win or Draw" -> {

                        if (prediction.score?.fulltime?.home == null) {
                            predictionOutcome = "TBD"
                        } else {
                            when (prediction.status?.short) {
                                "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                                    predictionOutcome =
                                        if (prediction.score.fulltime.home >= prediction.score.fulltime.away!!) {
                                            "WON"
                                        } else {
                                            "LOST"
                                        }
                                }
                                "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                                    predictionOutcome = "TBD"
                                }
                            }
                        }
                    }
                    "Away Win or Draw" -> {

                        if (prediction.score?.fulltime?.away == null) {
                            predictionOutcome = "TBD"
                        } else {
                            when (prediction.status?.short) {
                                "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                                    predictionOutcome =
                                        if (prediction.score.fulltime.away >= prediction.score.fulltime.home!!) {
                                            "WON"
                                        } else {
                                            "LOST"
                                        }
                                }
                                "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                                    predictionOutcome = "TBD"
                                }
                            }
                        }
                    }
                    "Home Win" -> {

                        if (prediction.score?.fulltime?.home == null) {
                            predictionOutcome = "TBD"
                        } else {
                            when (prediction.status?.short) {
                                "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                                    predictionOutcome =
                                        if (prediction.score.fulltime.home > prediction.score.fulltime.away!!) {
                                            "WON"
                                        } else {
                                            "LOST"
                                        }
                                }
                                "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                                    predictionOutcome = "TBD"
                                }
                            }
                        }

                    }
                    "Away Win" -> {

                        if (prediction.score?.fulltime?.away == null) {
                            predictionOutcome = "TBD"
                        } else {
                            when (prediction.status?.short) {
                                "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                                    predictionOutcome =
                                        if (prediction.score.fulltime.away > prediction.score.fulltime.home!!) {
                                            "WON"
                                        } else {
                                            "LOST"
                                        }
                                }
                                "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                                    predictionOutcome = "TBD"
                                }
                            }
                        }

                    }
                    "" -> {

                        predictionOutcome = "TBD"
                    }

                }

                Log.e("Game is ", predictionOutcome)

                PredictionListItemDark(prediction, predictionOutcome)
            }
        }
    }


}