package faba.app.suretippredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.database.MatchEvents
import faba.app.suretippredictions.models.events.Events
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.viewmodels.PredictionsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val predictionsViewModel: PredictionsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            predictionsViewModel.matchEventsList.observe(this, {
                //Log.e(localClassName, it.toString())

                val matchEventList = ArrayList<Events>()
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
            predictionsViewModel.roomMatchEventsList.observe(this, { events ->
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

            predictionsViewModel.roomMatchEventsByDate("2021-08-14").observe(this, { events ->
                events?.let {

                    for (event in events) {
                        Log.e(
                            localClassName,
                            event.matchEvents.match_id + " " + event.matchEvents.match_hometeam_name
                        )

                    }


                }
            })


            predictionsViewModel.loading.observe(this, Observer {
                if (it) Log.e(localClassName, "Loading")
            })

            predictionsViewModel.errorMessage.observe(this, Observer {
                Log.e(localClassName, it)
            })

            //predictionsViewModel.getAllMatchEvents()


        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SureTipPredictionsTheme {
        Greeting("Android")
    }
}