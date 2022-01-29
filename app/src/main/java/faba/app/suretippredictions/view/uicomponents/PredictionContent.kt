package faba.app.suretippredictions.view.uicomponents

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.utils.Constants
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.utils.DateUtil

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun PredictionContent(prediction: Prediction, collapsed: Boolean) {

    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = Constants.FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(Constants.EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = Constants.FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(Constants.COLLAPSE_ANIMATION_DURATION))
    }

    var predictionOutcome by remember { mutableStateOf("") }
    var theGameTime by remember { mutableStateOf("") }

    var odds by remember { mutableStateOf("") }

    var gameTextColor by remember {
        mutableStateOf(Color.White)
    }
    val oddsList = prediction.odds


    when (prediction.status?.short) {
        "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
            theGameTime = "FT"
            gameTextColor = colorResource(R.color.colorActualLightGrey)
        }
        "1H", "2H" -> {
            theGameTime = "${prediction.status.elapsed}"
            gameTextColor = colorResource(R.color.colorOrange)

        }
        "NS" -> {
            val gameTime = prediction.gameTime.toString().subSequence(11, 16).toString()
            theGameTime = DateUtil.formatGameTime(gameTime)
        }
        "TBD", "HT" -> {
            theGameTime = prediction.status.short
        }
        "SUSP", "INT", "PST", "CANC", "ABD" -> {
            theGameTime = prediction.status.long
        }
        else -> {
            theGameTime = prediction.status?.short ?: ""
        }
    }


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


            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 12
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Home/Draw"
                    }?.odd ?: ""

                } else {
                    ""
                }
            } else {
                odds = ""
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

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 12
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Draw/Away"
                    }?.odd ?: ""

                } else {
                    ""
                }
            } else {
                odds = ""
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

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 1
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Home"
                    }?.odd ?: ""

                } else {
                    ""
                }
            } else {
                odds = ""
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

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 1
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Away"
                    }?.odd ?: ""

                } else {
                    ""
                }
            } else {
                odds = ""
            }

        }
        "" -> {

            predictionOutcome = "TBD"
            odds = ""
        }

    }

    AnimatedVisibility(
        visible = !collapsed,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {

        if (!collapsed) {
            PredictionListItemDark(prediction, predictionOutcome, odds, theGameTime, gameTextColor)

        }

    }

}