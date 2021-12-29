package faba.app.suretippredictions.uicomponents


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.Constants
import faba.app.suretippredictions.ui.theme.SureTipPredictionsTheme
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.screens.AllGamesScreen
import faba.app.suretippredictions.screens.FavoritesScreen
import faba.app.suretippredictions.screens.PredictionsScreen
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.launch


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun SureScorePredictionsMain(
    predictionList: List<Prediction>,
    error: String,
    firstTimeLoading: Boolean
) {

    var appTitle by remember { mutableStateOf("") }
    var topAppBarIconsName by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem.AllGames,
        NavigationItem.Main,
        NavigationItem.Favorites
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appTitle) },
                actions = {



                    when (topAppBarIconsName) {
                        NavigationItem.AllGames.name, NavigationItem.Main.name -> {

                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Localized description"
                                )
                            }

                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.outline_calendar_today_white_24),
                                    contentDescription = "Calendar",
                                    modifier = Modifier.size(20.dp)

                                )
                            }


                        }
                        NavigationItem.Favorites.name -> {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    }


                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = items,
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Divider(color = Color.DarkGray, thickness = 1.dp)
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = colorResource(R.color.colorLightBlue),
                    backgroundColor = colorResource(R.color.dark_mode),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        }

    ) {


        Navigation(
            navController = navController,
            predictionList,
            onSetAppTitle = { appTitle = it },
            topAppBarIconsName = { topAppBarIconsName = it },
            firstTimeLoading
            )

        if (error.isNotEmpty()) {
            coroutineScope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "Refresh"
                )

                when(snackbarResult){
                    SnackbarResult.ActionPerformed -> {

                    }
                }
            }
        }

    }

}

@ExperimentalCoilApi
@Composable
fun Navigation(
    navController: NavHostController,
    predictionList: List<Prediction>,
    onSetAppTitle: (String) -> Unit,
    topAppBarIconsName: (String) -> Unit,
    firstTimeLoading: Boolean
) {
    NavHost(navController = navController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.AllGames.route) {
            AllGamesScreen(
                predictionList,
                onSetAppTitle,
                topAppBarIconsName,
                firstTimeLoading
            )
        }
        composable(NavigationItem.Main.route) {
            PredictionsScreen(
                predictionList.filter { it.league?.id in Constants.mainLeaguesList },
                onSetAppTitle,
                topAppBarIconsName,
                firstTimeLoading
            )
        }
        composable(NavigationItem.Favorites.route) {
            FavoritesScreen(
                predictionList,
                onSetAppTitle,
                topAppBarIconsName,
                firstTimeLoading
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (NavigationItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(R.color.dark_mode),
        elevation = 20.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = colorResource(R.color.colorLightBlue),
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.name
                        )

                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )

/*
                        if(selected) {

                        }
*/
                    }
                }
            )
        }
    }
}


/*@Composable
fun PredictionListItem() {

    Surface {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(120.dp), elevation = 6.dp
        ) {

            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (icon, text) = createRefs()
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "fav",
                            modifier = Modifier
                                .size(width = 27.dp, height = 27.dp)
                                .padding(start = 5.dp, top = 5.dp)
                                .constrainAs(icon) {
                                    start.linkTo(parent.start)
                                }
                        )

                        Text(
                            text = "16 Apr 17:20",
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.constrainAs(text) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }

                        )


                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.man_u),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = "Manchester United",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text(
                            text = "2 - 0",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            maxLines = 2,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )

                        Surface(
                            color = Color.Gray,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 104.dp, height = 17.dp)

                        ) {
                            Text(
                                text = "Home Win or Draw",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                        }

                        Text(
                            text = "Odds : 1.32",
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            maxLines = 2,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.city),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 47.dp, height = 47.dp)
                                .offset(y = (-10).dp)
                                .align(CenterHorizontally)
                        )

                        Text(
                            text = "Manchester City",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }


            }


        }


    }


}*/


/*fun PredictionListItemTwo() {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 10.dp)
        ) {
            Image(Icons.Default.Home, contentDescription = null)
            Text(text = "Premier League", Modifier.padding(start = 5.dp))
        }

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .align(CenterHorizontally)
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (team1, team2, vs) = createRefs()

                Text(text = "Manchester United" , modifier = Modifier.constrainAs(team1){
                    end.linkTo(vs.start)
                })

                Text(text = " vs ", modifier = Modifier.constrainAs(vs){
                    centerHorizontallyTo(parent)
                })

                Text(text = "Leeds United", modifier = Modifier.constrainAs(team2){
                    start.linkTo(vs.end)
                })
            }


        }

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row() {
                Text(text = "Tip : 1", textAlign = TextAlign.Start)
            }
            Row() {
                Text(text = "Odds : 1.2", textAlign = TextAlign.Center)
            }
            Row() {
                Text(text = "Result : 1-1", textAlign = TextAlign.End)
            }


        }

    }


}*/

@Composable
fun PredictionHeaderItem() {

    Text(text = "England", textAlign = TextAlign.Center)

}

@Preview(showBackground = true)
@Composable
fun PredictionListItemPreview() {
    SureTipPredictionsTheme {
        //PredictionListItemDark()
    }
}

