package faba.app.suretippredictions.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo.exception.ApolloException
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.core.ApiException
import faba.app.core.NoInternetException
import faba.app.suretippredictions.Constants
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.database.PredictionUpdate
import faba.app.suretippredictions.database.PredictionUpdateOdds
import faba.app.suretippredictions.di.IoDispatcher
import faba.app.suretippredictions.di.MainDispatcher
import faba.app.suretippredictions.models.fixtures.Goals
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.predictions.*
import faba.app.suretippredictions.repository.PredictionsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(
    private val repository: PredictionsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val gson = Gson()

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getLastSelectedDate: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val status: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val apiSize: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val localSize: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }

    init {
        loading.value = true
        apiSize.value = 0
        localSize.value = 0
        getLastSelectedDate.value = Calendar.getInstance().timeInMillis
    }


    fun listPredictions(date: String) {
        onError("")
        val predictionList = mutableListOf<Prediction>()
        apiSize.value = 1
        viewModelScope.launch {
            withContext(ioDispatcher + exceptionHandler) {
                try {

                    var response = repository.listPredictions(date, "")

                    while (true) {

                        response.let { data ->
                            data.listPredictions()?.items()?.forEach {

                                val predictions = gson.fromJson(
                                    it.predictions() as String?,
                                    Predictions::class.java
                                )

                                var predictionString = ""


                                val homeId = it.homeId()
                                val awayId = it.awayId()

                                if (predictions.win_or_draw) {
                                    when (predictions.winner.id) {
                                        homeId -> {
                                            predictionString = "Home Win or Draw"

                                        }
                                        awayId -> {
                                            predictionString = "Away Win or Draw"
                                        }
                                    }

                                } else {
                                    when (predictions.winner.id) {
                                        homeId -> {
                                            predictionString = "Home Win"

                                        }
                                        awayId -> {
                                            predictionString = "Away Win"
                                        }
                                    }
                                }

                                val prediction = Prediction(
                                    it!!.id(),
                                    it.predictionDate(),
                                    it.gameTime(),
                                    null,
                                    null,
                                    null,
                                    gson.fromJson(
                                        it.predictions() as String?,
                                        Predictions::class.java
                                    ),
                                    gson.fromJson(
                                        it.league() as String?,
                                        League::class.java
                                    ),
                                    null,
                                    it.homeName(),
                                    it.homeLogo(),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    it.awayName(),
                                    it.awayLogo(),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    predictionString

                                )


                                if (!predictionList.any { prediction -> prediction.id == it.id() }) {
                                    predictionList.add(prediction)
                                }
                                //predCounter++

                            }

                        }

                        if (response.listPredictions()?.nextToken() == null) {
                            break
                        } else {
                            response = repository.listPredictions(
                                date,
                                response.listPredictions()?.nextToken()!!,
                            )

                        }


                    }

                    Log.e("Size ", predictionList.size.toString())

                    predictionList.let {
                        repository.insertPrediction(it)
                    }



                    withContext(mainDispatcher) {
                        apiSize.value = predictionList.size
                        if (predictionList.size == 0) loading.value = false

                    }

                    withContext(ioDispatcher) {
                        updatePredictionOdds(date)
                    }

                } catch (e: ApiException) {
                    onError(Constants.internetError)
                } catch (e: NoInternetException) {
                    onError(Constants.internetError)
                } catch (e: ApolloException) {
                    onError(Constants.internetError)
                }
            }
        }
    }

    fun updatePrediction(date: String) {
        val predictionUpdateList = mutableListOf<PredictionUpdate>()
        viewModelScope.launch {
            withContext(ioDispatcher + exceptionHandler) {
                try {
                    var response = repository.getFixtures(date, "")

                    while (true) {

                        response.let { data ->
                            data.listFixtures()?.items()?.forEach {

                                //Log.e("The goals are", it.goals().toString())

                                val predictionUpdate = PredictionUpdate(
                                    it.id(),
                                    gson.fromJson(
                                        it.status() as String?,
                                        Status::class.java
                                    ),
                                    gson.fromJson(
                                        it.score() as String?,
                                        Score::class.java
                                    ),
                                    gson.fromJson(
                                        it.goals() as String?,
                                        Goals::class.java
                                    )
                                )

                                if (!predictionUpdateList.any { predictionUpdateItem -> predictionUpdateItem.id == it.id() }) {
                                    predictionUpdateList.add(predictionUpdate)
                                }


                            }

                        }

                        if (response.listFixtures()?.nextToken() == null) {
                            break
                        } else {
                            response = repository.getFixtures(
                                date,
                                response.listFixtures()?.nextToken()!!
                            )
                        }

                    }

                    predictionUpdateList.let {
                        repository.updatePrediction(it)
                    }

                } catch (e: NoInternetException) {
                    onError(Constants.internetError)
                } catch (e: ApolloException) {
                    onError(Constants.internetError)
                } catch (e: ApiException) {
                    onError(Constants.internetError)
                }

            }

        }
    }

    fun updatePredictionOdds(date: String) {
        val predictionUpdateOddsList = mutableListOf<PredictionUpdateOdds>()
        viewModelScope.launch {
            withContext(ioDispatcher + exceptionHandler) {
                try {
                    var response = repository.listOdds(date, "")

                    while (true) {

                        response.let { data ->
                            data.listOdds()?.items()?.forEach {

                                val predictionUpdateOdds = PredictionUpdateOdds(
                                    it.id(),
                                    gson.fromJson(
                                        it.bookmaker() as String?,
                                        Array<Bookmaker>::class.java
                                    ).toMutableList()

                                )

                                if (!predictionUpdateOddsList.any { predictionUpdateOddsItem -> predictionUpdateOddsItem.id == it.id() }) {
                                    predictionUpdateOddsList.add(predictionUpdateOdds)
                                }


                            }

                        }

                        if (response.listOdds()?.nextToken() == null) {
                            break
                        } else {
                            response = repository.listOdds(
                                date,
                                response.listOdds()?.nextToken()!!
                            )
                        }

                    }

                    predictionUpdateOddsList.let {
                        repository.updatePredictionOdds(it)
                    }

                    withContext(ioDispatcher) {
                        updatePrediction(date)
                    }


                } catch (e: ApiException) {
                    onError(Constants.internetError)
                } catch (e: NoInternetException) {
                    onError(Constants.internetError)
                } catch (e: ApolloException) {
                    onError(Constants.internetError)
                }

            }

        }
    }


    fun roomPredictionsList(date: String): Flow<List<Prediction>> {
        loading.value = false
        return repository.roomPredictionsList(date)
    }

    fun getPredictionsRowCount(date: String): Flow<Int?>? {
        loading.value = true
        return repository.getPredictionsRowCount(date)
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

}
