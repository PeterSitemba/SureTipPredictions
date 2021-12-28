package faba.app.suretippredictions.uicomponents

import faba.app.suretippredictions.R

sealed class NavigationItem(var name: String, var route: String, var icon: Int){
    object Main : NavigationItem("Main matches",  "main", R.drawable.pitch)
    object AllGames : NavigationItem("All Games",  "all_games", R.drawable.outline_sports_soccer_white_24)
    object Favorites : NavigationItem("Favorites",  "favorites", R.drawable.outline_star_outline_white)
}
