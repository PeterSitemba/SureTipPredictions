package faba.app.suretippredictions.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.suretippredictions.database.MatchEvents
import faba.app.suretippredictions.models.events.Events
import faba.app.suretippredictions.repository.PredictionsRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(private val predictionsRepository: PredictionsRepository) :
    ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val matchEventsList = MutableLiveData<List<Events>>()
    val roomMatchEventsList: LiveData<List<MatchEvents>> =
        predictionsRepository.roomMatchEventsList.asLiveData()

    /*  val roomMatchEventsListByDate: LiveData<List<MatchEvents>> =
          predictionsRepository.roomMatchEventsListByDate.asLiveData()*/
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")

    }

    fun insert(matchEvents: MatchEvents) = viewModelScope.launch {
        predictionsRepository.insert(matchEvents)
    }

    fun roomMatchEventsByDate(match_date: String): LiveData<List<MatchEvents>> {
        return predictionsRepository.roomMatchEventsListByDate(match_date).asLiveData()
    }

    fun getAllMatchEvents() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = predictionsRepository.getAllMatchEvents()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    matchEventsList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }


        }
    }


    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}