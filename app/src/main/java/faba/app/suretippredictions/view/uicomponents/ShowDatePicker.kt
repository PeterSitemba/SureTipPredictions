package faba.app.suretippredictions.view.uicomponents

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.saveable.SaveableStateHolder
import coil.annotation.ExperimentalCoilApi
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import java.util.*

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalCoilApi::class
)
fun showDatePicker(
    activity: AppCompatActivity,
    updatedDate: (Long?) -> Unit,
    predictionsViewModel: PredictionsViewModel,
    saveableStateHolder: SaveableStateHolder
) {

    val today = MaterialDatePicker.todayInUtcMilliseconds()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    calendar.timeInMillis = today
    calendar[Calendar.MONTH] = Calendar.MONTH - 2
    val previous = calendar.timeInMillis

    calendar.timeInMillis = today
    calendar[Calendar.MONTH] = Calendar.MONTH - 1
    val current = calendar.timeInMillis


    val constraintsBuilder =
        CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

    constraintsBuilder.setStart(previous)
    constraintsBuilder.setEnd(current)
    val builder = MaterialDatePicker.Builder.datePicker().apply {
        setSelection(predictionsViewModel.getLastSelectedDate.value)
        setCalendarConstraints(constraintsBuilder.build())
    }


    val picker = builder.build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        saveableStateHolder.removeState(NavigationItem.Main.route)
        saveableStateHolder.removeState(NavigationItem.AllGames.route)
        saveableStateHolder.removeState(NavigationItem.Favorites.route)
        predictionsViewModel.getLastSelectedDate.value = it
        updatedDate(it)
    }
}