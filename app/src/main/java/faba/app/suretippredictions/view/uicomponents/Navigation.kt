package faba.app.suretippredictions.view.uicomponents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.utils.Constants
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.view.screens.AllGamesScreen
import faba.app.suretippredictions.view.screens.FavoritesScreen
import faba.app.suretippredictions.view.screens.PredictionsScreen
import faba.app.suretippredictions.viewmodels.PredictionsViewModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun Navigation(
    navController: NavHostController,
    predictionList: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    topAppBarIconsName: (String) -> Unit,
    predictionsViewModel: PredictionsViewModel,
    saveableStateHolder: SaveableStateHolder
) {

    NavHost(navController = navController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.AllGames.route) {

            saveableStateHolder.SaveableStateProvider(key = NavigationItem.AllGames.route) {
                AllGamesScreen(
                    predictionList,
                    onSetAppTitle,
                    topAppBarIconsName,
                    predictionsViewModel
                )
            }

        }
        composable(NavigationItem.Main.route) {

            val filteredList by remember(predictionList) {
                derivedStateOf {
                    predictionList.filter { it.league?.id in Constants.mainLeaguesList }
                }
            }

            saveableStateHolder.SaveableStateProvider(key = NavigationItem.Main.route) {
                PredictionsScreen(
                    filteredList,
                    onSetAppTitle,
                    topAppBarIconsName,
                    predictionsViewModel
                )
            }
        }
        composable(NavigationItem.Favorites.route) {
            saveableStateHolder.SaveableStateProvider(key = NavigationItem.Favorites.route) {
                FavoritesScreen(
                    predictionList,
                    onSetAppTitle,
                    topAppBarIconsName,
                    predictionsViewModel
                )
            }
        }
    }
}