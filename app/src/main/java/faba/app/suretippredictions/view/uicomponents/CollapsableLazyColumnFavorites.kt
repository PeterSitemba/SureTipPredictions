package faba.app.suretippredictions.view.uicomponents

import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.utils.DateUtil

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CollapsableLazyColumnFavorites(
    groupedDates: List<List<Prediction>>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {


    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {

        groupedDates.forEach { predictionList ->

            item {
                Text(
                    text = "${DateUtil.getDay(predictionList[0].date.toString())} ${
                        DateUtil.formatToDateAndMonth(
                            predictionList[0].date.toString()
                        )
                    } ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 10.dp)
                )


            }

            val groupedLeaguesNo = predictionList.groupBy { it.league?.id }.values
            val leagues = groupedLeaguesNo.toList()
                .sortedWith(compareBy({ it[0].league?.id }, { it[0].league?.country }))

            leagues.forEach { dataItem ->
                val heightSize = 380 * dataItem.size + 100

                item {
                    LazyColumn(
                        modifier
                            .fillMaxWidth()
                            .height(heightSize.toDp().dp)
                            .padding(bottom = 20.dp),
                        state = listState,
                        userScrollEnabled = false

                    ) {

                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        dataItem[0].league?.flag,
                                        builder = {
                                            decoder(SvgDecoder(LocalContext.current))
                                        }),
                                    contentDescription = "league",
                                    modifier = Modifier
                                        .size(width = 20.dp, height = 20.dp)
                                )

                                Spacer(modifier = Modifier.size(10.dp))


                                Text(
                                    "${dataItem[0].league?.country!!} - ${dataItem[0].league?.name!!}",
                                    Modifier
                                        .align(
                                            alignment = Alignment.CenterVertically
                                        )
                                        .weight(1f),
                                    fontWeight = FontWeight.Bold
                                )

                            }

                        }


                        items(dataItem, key = { prediction -> prediction.id }) { prediction ->
                            PredictionContentFavorites(prediction = prediction)
                        }
                    }

                }


            }


        }

    }


}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()