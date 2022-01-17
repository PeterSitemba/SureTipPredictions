package faba.app.suretippredictions

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.uicomponents.SureScorePredictionsMain
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {
    private val predictionsViewModel: PredictionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                predictionsViewModel.loading.value!!
            }
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val currentDate = sdf.format(Date())
        //val currentDate = "2022-01-11"



        setContent {

            var datePicked : String? by remember {
                mutableStateOf(currentDate)
            }

            val updatedDate = { date : Long? ->
                datePicked = DateFormater(date) ?: currentDate
            }


            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(
                color = colorResource(R.color.dark_mode)
            )

            SureTipPredictionsTheme(true) {
                MainActivityScreen(predictionsViewModel, datePicked!!, updatedDate)

            }

            iniObservables(datePicked!!)

            updatePredictions(datePicked!!)


        }
    }

    private fun iniObservables(date: String) {

        //get pred number from server and use it to update local db if number is less than db

        predictionsViewModel.getPredictionsRowCount(date)?.observe(this, { pred ->
            if (pred == 0) {
                predictionsViewModel.listPredictions(date)
            }
        })


    }

    private fun updatePredictions(date: String): Job {
        return lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //predictionsViewModel.updatePrediction("2021-12-04")
                while (true) {
                    predictionsViewModel.updatePrediction(date)
                    delay(30000)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun DateFormater(milliseconds : Long?) : String?{
        milliseconds?.let{
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(it)
            return formatter.format(calendar.getTime())
        }
        return null
    }

}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MainActivityScreen(
    predictionsViewModel: PredictionsViewModel,
    date: String,
    updatedDate: (Long?) -> Unit
) {

    var isInternet = ""

    val predictionItems: List<Prediction> by predictionsViewModel.roomPredictionsList(date)
        .observeAsState(listOf())

    val error by predictionsViewModel.errorMessage.observeAsState("")

    val predictionList by remember(predictionItems) {
        derivedStateOf {
            predictionItems
        }
    }

    if (predictionItems.isEmpty()) {
        //first time loading
        SureScorePredictionsMain(
            predictionList,
            error,
            firstTimeLoading = true,
            predictionsViewModel,
            updatedDate
        )
        //return
    } else {
        SureScorePredictionsMain(
            predictionList,
            error,
            firstTimeLoading = false,
            predictionsViewModel,
            updatedDate
        )


    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SureTipPredictionsTheme {
        //PredictionList("Android")
    }
}


@Composable
fun FirstTimeLoading() {
    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {


        Column {
            Text(text = "No Predictions Available")
            //CircularProgressIndicator(color = colorResource(R.color.colorLightBlue), modifier = Modifier.align(CenterHorizontally))
        }


    }
}

@Composable
fun NoPredictions() {
    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {


    }
}