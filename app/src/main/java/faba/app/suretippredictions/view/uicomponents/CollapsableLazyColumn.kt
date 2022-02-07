package faba.app.suretippredictions.view.uicomponents

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import faba.app.suretippredictions.database.Prediction

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CollapsableLazyColumn(
    leagues: List<List<Prediction>>,
    listState: LazyListState,
    collapsedState: List<MutableState<Boolean>>,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier.padding(bottom = 60.dp), state = listState) {

        leagues.forEachIndexed { i, dataItem ->

            val collapsed = collapsedState[i]

            item(key = "header_$i") {

                Divider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            collapsedState[i].value = !collapsed.value
                        }
                        .padding(top = 10.dp, bottom = 10.dp)

                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed.value)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.LightGray,
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Image(
                        painter = rememberImagePainter(dataItem[0].league?.flag, builder = {
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

                if (collapsed.value) {
                    Divider()
                }


            }

            items(dataItem, key = { prediction -> prediction.id }) { prediction ->
                PredictionContent(prediction = prediction, collapsed = collapsed.value)
            }

        }
    }

}
