package faba.app.suretippredictions.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.MatchEvents
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme


@Composable
fun PredictionsScreen(events: List<MatchEvents>) {

    LazyColumn() {
        items(events) { event ->
            //PredictionListItemDark(event.matchEvents)
        }
    }

}

@Composable
fun PredictionListItem() {

    Surface {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(120.dp), elevation = 6.dp
        ) {

            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (icon, text) = createRefs()
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "fav",
                            modifier = Modifier
                                .size(width = 27.dp, height = 27.dp)
                                .padding(start = 5.dp, top = 5.dp)
                                .constrainAs(icon) {
                                    start.linkTo(parent.start)
                                }
                        )

                        Text(
                            text = "16 Apr 17:20",
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
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
                            painterResource(id = R.drawable.man_u),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = "Manchester United",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text(
                            text = "2 - 0",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            maxLines = 2,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )

                        Surface(
                            color = Color.Gray,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 104.dp, height = 17.dp)

                        ) {
                            Text(
                                text = "Home Win or Draw",
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
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.city),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = "Manchester City",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
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


//To be removed, look at theming on android code labs
/*@ExperimentalCoilApi
@Composable
fun PredictionListItemDark(event: Events) {

    Surface(color = Color.DarkGray) {

        Card(
            backgroundColor = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
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
                            text = "${event.match_date} ${event.match_time}",
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
                            painter = rememberImagePainter(event.team_home_badge),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = event.match_hometeam_name,
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
                        Text(
                            text = "${event.match_hometeam_ft_score} - ${event.match_awayteam_ft_score}",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )

                        Surface(
                            color = Color.Green,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 104.dp, height = 17.dp)

                        ) {
                            Text(
                                text = "Home Win or Draw",
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
                            painter = rememberImagePainter(event.team_away_badge),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = event.match_awayteam_name,
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


}*/

/*fun PredictionListItemTwo() {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp)
        ) {
            Image(Icons.Default.Home, contentDescription = null)
            Text(text = "Premier League", Modifier.padding(start = 5.dp))
        }

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .align(CenterHorizontally)
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (team1, team2, vs) = createRefs()

                Text(text = "Manchester United" , modifier = Modifier.constrainAs(team1){
                    end.linkTo(vs.start)
                })

                Text(text = " vs ", modifier = Modifier.constrainAs(vs){
                    centerHorizontallyTo(parent)
                })

                Text(text = "Leeds United", modifier = Modifier.constrainAs(team2){
                    start.linkTo(vs.end)
                })
            }


        }

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row() {
                Text(text = "Tip : 1", textAlign = TextAlign.Start)
            }
            Row() {
                Text(text = "Odds : 1.2", textAlign = TextAlign.Center)
            }
            Row() {
                Text(text = "Result : 1-1", textAlign = TextAlign.End)
            }


        }

    }


}*/

@Composable
fun PredictionHeaderItem() {

    Text(text = "England", textAlign = TextAlign.Center)

}

@Preview(showBackground = true)
@Composable
fun PredictionListItemPreview() {
    SureTipPredictionsTheme {
        //PredictionListItemDark()
    }
}

