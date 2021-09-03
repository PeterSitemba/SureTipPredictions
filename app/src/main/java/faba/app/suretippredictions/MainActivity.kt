package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.database.MatchEvents
import faba.app.suretippredictions.models.events.Events
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.uicomponents.PredictionListItem
import faba.app.suretippredictions.uicomponents.PredictionListItemDark
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import androidx.compose.runtime.livedata.observeAsState
import faba.app.suretippredictions.uicomponents.PredictionsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val predictionsViewModel: PredictionsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            predictionsViewModel.getAllMatchEvents()

            SureTipPredictionsTheme {
                PredictionActivityScreen(predictionsViewModel)
            }

            predictionsViewModel.matchEventsList.observe(this, {
                //Log.e(localClassName, it.toString())

                //val matchEventList = ArrayList<Events>()
                for (matchevent in it) {
                    //Log.e(localClassName, matchevent.match_id + " " + matchevent.match_hometeam_name)
                    //matchEventList.add(matchevent)
                    predictionsViewModel.insert(
                        MatchEvents(
                            matchevent.match_id.toInt(),
                            matchevent.match_date,
                            matchevent
                        )
                    )
                }


            })

/*
            predictionsViewModel.roomMatchEventsByDate("2021-08-20").observe(this, { events ->
                events?.let {

                    for (event in events) {
                        Log.e(
                            localClassName,
                            event.matchEvents.match_id + " " + event.matchEvents.match_hometeam_name
                        )

                    }


                }
            })
*/


            /*   predictionsViewModel.loading.observe(this, Observer {
                   if (it) Log.e(localClassName, "Loading")
               })

               predictionsViewModel.errorMessage.observe(this, Observer {
                   Log.e(localClassName, it)
               })*/

            //predictionsViewModel.getAllMatchEvents()


        }
    }
}

@Composable
fun PredictionActivityScreen(predictionsViewModel: PredictionsViewModel) {

    val predictionItems: List<MatchEvents> by predictionsViewModel.roomMatchEventsByDate("2021-08-29")
        .observeAsState(listOf())

    PredictionsScreen(predictionItems)


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SureTipPredictionsTheme {
        //PredictionList("Android")
    }
}