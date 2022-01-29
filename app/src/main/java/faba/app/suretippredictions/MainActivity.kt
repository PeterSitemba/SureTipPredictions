package faba.app.suretippredictions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.view.uicomponents.MainActivityScreen
import faba.app.suretippredictions.utils.DateUtil.DateFormater
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
class MainActivity(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    AppCompatActivity() {
    private val predictionsViewModel: PredictionsViewModel by viewModels()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                //false
                predictionsViewModel.loading.value!!
            }
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val currentDate = sdf.format(Date())
        updatePredictions(DateFormater(predictionsViewModel.getLastSelectedDate.value)!!)

        //val currentDate = "2022-01-11"


        setContent {

            var datePicked: String by remember {
                mutableStateOf(currentDate)
            }

            val updatedDate = { date: Long? ->
                datePicked = DateFormater(date) ?: currentDate

                if (updatePredictions(datePicked).isActive) {
                    updatePredictions(datePicked).cancel()
                    updatePredictions(datePicked)
                }

            }


            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(
                color = colorResource(R.color.dark_mode)
            )

            SureTipPredictionsTheme(true) {
                MainActivityScreen(predictionsViewModel, datePicked, updatedDate)

            }

            iniObservables(datePicked)


        }
    }

    private fun iniObservables(date: String) {

        //get pred number from server and use it to update local db if number is less than db
        lifecycleScope.launch {
            predictionsViewModel.getPredictionsRowCount(date)?.collect {
                predictionsViewModel.localSize.value = it
                Log.e("Number is", it.toString())
                if (it == 0) {
                    predictionsViewModel.listPredictions(date)
                } else {
                    predictionsViewModel.apiSize.value = 0
                }
            }
        }

    }

    private fun updatePredictions(date: String): Job {
        return lifecycleScope.launch {
            withContext(ioDispatcher) {
                while (true) {
                    if (NetworkConnectionInterceptor(this@MainActivity).isNetworkAvailable()) {
                        predictionsViewModel.updatePrediction(date)
                    }
                    delay(30000)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SureTipPredictionsTheme {
        //PredictionList("Android")
    }
}


