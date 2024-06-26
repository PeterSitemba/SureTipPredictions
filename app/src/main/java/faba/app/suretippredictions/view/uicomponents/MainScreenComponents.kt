package faba.app.suretippredictions.view.uicomponents


import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.service.NetworkConnectionInterceptor
import faba.app.suretippredictions.utils.DateUtil.dateFormatter
import faba.app.suretippredictions.utils.DateUtil.dateFormatterDayOnly
import faba.app.suretippredictions.viewmodels.PredictionsViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun SureScorePredictionsMain(
    predictionsViewModel: PredictionsViewModel,
    updatedDate: (Long?) -> Unit,
    date: String
) {

    val predictionItems: List<Prediction> by predictionsViewModel.roomPredictionsList(date)
        .observeAsState(emptyList())

    val predictionList by remember(predictionItems) {
        derivedStateOf {
            predictionItems
        }
    }

    //Log.e("SurescoreMain", predictionList.toString())

    var appTitle by remember { mutableStateOf("") }
    var topAppBarIconsName by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val saveableStateHolder = rememberSaveableStateHolder()
    val error by predictionsViewModel.errorMessage.observeAsState("")


    val items = listOf(
        NavigationItem.AllGames,
        NavigationItem.Main,
        NavigationItem.Favorites
    )

    val activity = LocalContext.current as AppCompatActivity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appTitle) },
                actions = {

                    when (topAppBarIconsName) {
                        NavigationItem.AllGames.name, NavigationItem.Main.name -> {
                            IconButton(onClick = {
                                showDatePicker(
                                    activity,
                                    updatedDate,
                                    predictionsViewModel,
                                    saveableStateHolder
                                )
                            }) {

                                Box() {
                                    Icon(
                                        painterResource(id = R.drawable.outline_calendar_today_white_24),
                                        contentDescription = "Calendar",
                                        modifier = Modifier.size(25.dp)

                                    )
                                    Text(
                                        text = dateFormatterDayOnly(predictionsViewModel.getLastSelectedDate.value)!!,
                                        fontSize = 11.sp,
                                        modifier = Modifier
                                            .align(
                                                Alignment.BottomCenter
                                            )
                                            .padding(bottom = 2.dp),
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                            }


                        }
                        NavigationItem.Favorites.name -> {
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
            predictionsViewModel,
            saveableStateHolder
        )

        if (error.isNotEmpty()) {
            coroutineScope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "Refresh",
                    duration = SnackbarDuration.Long
                )

                when (snackbarResult) {
                    SnackbarResult.ActionPerformed -> {
                        if (NetworkConnectionInterceptor(activity).isNetworkAvailable()) {
                            predictionsViewModel.listPredictions(
                                dateFormatter(
                                    predictionsViewModel.getLastSelectedDate.value
                                )!!
                            )
                        }
                    }
                    else -> { //empty
                    }
                }
            }
        }

    }

}


@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (NavigationItem) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(R.color.dark_mode),
        elevation = 20.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry?.destination?.route
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


@Composable
fun IsEmpty() {

    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column {
            Text(text = "Predictions Unavailable")
        }
    }
}

@Composable
fun NoFavorites() {

    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column {
            Text(text = "No Favorites")
        }
    }
}

@Composable
fun NoInternetConnection() {

    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column {
            Text(text = "No Internet Connection")
        }
    }
}

@Composable
fun ProgressDialog() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.colorLightBlue)
        )

    }

}


