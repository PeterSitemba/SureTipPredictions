package faba.app.suretippredictions.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import faba.app.suretippredictions.FirstTimeLoading
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.uicomponents.NavigationItem

@ExperimentalCoilApi
@Composable
fun PredictionsScreen(
    prediction: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    onTopAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean
) {

    onSetAppTitle("SureScore Predictions")
    onTopAppBarIconsName(NavigationItem.Main.name)

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

@ExperimentalCoilApi
@Composable
fun PredictionListItemDark(prediction: Prediction, predictionOutcome: String) {

    Surface(color = colorResource(R.color.dark_mode)) {

        Card(
            backgroundColor = colorResource(R.color.card_bg),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .height(120.dp), elevation = 6.dp
        ) {

            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (icon, text) = createRefs()
                        Icon(
                            painter = painterResource(id = R.drawable.outline_star_white_24),
                            contentDescription = "fav",
                            modifier = Modifier
                                .size(width = 27.dp, height = 27.dp)
                                .padding(start = 5.dp, top = 5.dp)
                                .constrainAs(icon) {
                                    start.linkTo(parent.start)
                                }
                        )

                        Text(
                            text = prediction.date.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.constrainAs(text) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }

                        )


                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.homeLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = prediction.homeName.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        var goals: String = if (prediction.goals?.home == null) {
                            "VS"
                        } else {
                            "${prediction.goals?.home} - ${prediction.goals?.away}"
                        }

                        Text(
                            text = goals,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )

                        var predColor = colorResource(R.color.colorLightGrey)


                        when (predictionOutcome) {
                            "TBD" -> {
                                predColor = colorResource(R.color.colorLightGrey)
                            }
                            "WON" -> {
                                predColor = colorResource(R.color.dark_green)
                            }
                            "LOST" -> {
                                predColor = colorResource(R.color.colorLightRed)
                            }
                        }

                        Surface(
                            color = predColor,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 104.dp, height = 17.dp)

                        ) {
                            Text(
                                text = prediction.predictionString.toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                        }

                        Text(
                            text = "Odds : 1.32",
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.awayLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = prediction.awayName.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }


            }


        }


    }


}