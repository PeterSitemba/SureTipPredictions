package faba.app.suretippredictions.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.database.Prediction

@ExperimentalCoilApi
@Composable
fun FavoritesScreen(prediction: List<Prediction>, onSetAppTitle: (String) -> Unit, onTopAppBarIconsName: (String) -> Unit) {

    onSetAppTitle("Favorites")
    onTopAppBarIconsName("Favorites")

    LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
        items(prediction) { prediction ->
            PredictionListItemDark(prediction)
        }
    }

}