package faba.app.suretippredictions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.utils.DateUtil.currentDate
import faba.app.suretippredictions.utils.DateUtil.dateFormatter
import faba.app.suretippredictions.view.uicomponents.SureScorePredictionsMain
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.*

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
                predictionsViewModel.loading.value!!
            }
        }

        updatePredictions(dateFormatter(predictionsViewModel.getLastSelectedDate.value)!!)

        setContent {

            var datePicked: String by remember {
                mutableStateOf(currentDate())
            }

            val updatedDate = { date: Long? ->
                datePicked = dateFormatter(date) ?: currentDate()

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
                SureScorePredictionsMain(predictionsViewModel, updatedDate, datePicked)

            }

            iniObservables(datePicked)

        }
    }

    private fun iniObservables(date: String) {

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

