package faba.app.suretippredictions.view.uicomponents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun PredictionListItemDark(
    prediction: Prediction,
    predictionOutcome: String,
    odds: String,
    theGameTime: String,
    gameTextColor: Color
) {

    val infiniteTransition = rememberInfiniteTransition()

    val infinitelyAnimatedFloat = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        )
    )

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


                        Row(modifier = Modifier.constrainAs(text) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                            Text(
                                text = theGameTime,
                                textAlign = TextAlign.Center,
                                color = gameTextColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            when (prediction.status?.short) {
                                "1H", "2H" -> {
                                    Text(
                                        text = "'",
                                        textAlign = TextAlign.Center,
                                        color = gameTextColor,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        modifier = Modifier.alpha(
                                            infinitelyAnimatedFloat.value
                                        )

                                    )

                                }
                            }


                        }


                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 10.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.homeLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 38.dp, height = 38.dp)
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
                        val goals: String = if (prediction.goals?.home == null) {
                            "VS"
                        } else {
                            "${prediction.goals.home} - ${prediction.goals.away}"
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

                        if (prediction.predictionString.toString().isEmpty()) {
                            predColor = colorResource(R.color.card_bg)
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


                        var theOdds = ""

                        if (odds.isNotEmpty()) {
                            theOdds = "Odds: $odds"
                        }

                        Text(
                            text = theOdds,
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
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 10.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.awayLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 38.dp, height = 38.dp)
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