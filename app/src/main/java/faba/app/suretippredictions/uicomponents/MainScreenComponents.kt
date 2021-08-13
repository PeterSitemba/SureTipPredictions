package faba.app.suretippredictions.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme


@Composable
fun PredictionListItem() {


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


}

@Composable
fun PredictionHeaderItem() {

    Text(text = "England", textAlign = TextAlign.Center)

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PredictionListItemPreview() {
    SureTipPredictionsTheme {
        PredictionListItem()
    }
}
