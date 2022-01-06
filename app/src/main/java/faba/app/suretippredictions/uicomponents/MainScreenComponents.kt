package faba.app.suretippredictions.uicomponents


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import faba.app.suretippredictions.Constants
import faba.app.suretippredictions.Constants.COLLAPSE_ANIMATION_DURATION
import faba.app.suretippredictions.Constants.EXPAND_ANIMATION_DURATION
import faba.app.suretippredictions.Constants.FADE_IN_ANIMATION_DURATION
import faba.app.suretippredictions.Constants.FADE_OUT_ANIMATION_DURATION
import faba.app.suretippredictions.R
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.screens.AllGamesScreen
import faba.app.suretippredictions.screens.FavoritesScreen
import faba.app.suretippredictions.screens.PredictionsScreen
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
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

                when (snackbarResult) {
                    SnackbarResult.ActionPerformed -> {

                    }
                }
            }
        }

    }

}

@ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CollapsableLazyColumn(
    leagues: List<List<Prediction>>,
    listState: LazyListState,
    startCollapsed: Boolean,
    modifier: Modifier = Modifier.padding(bottom = 60.dp)
) {
    val collapsedState = rememberSaveable(leagues) { leagues.map { mutableStateOf(startCollapsed) } }

    LazyColumn(modifier, state = listState) {

        leagues.forEachIndexed { i, dataItem ->

            val collapsed = collapsedState[i]


            item(key = "header_$i") {

                Divider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            collapsedState[i].value = !collapsed.value
                        }
                        .padding(top = 10.dp, bottom = 10.dp)

                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed.value)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.LightGray,
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Image(
                        painter = rememberImagePainter(dataItem[0].league?.flag, builder = {
                            decoder(SvgDecoder(LocalContext.current))
                        }),
                        contentDescription = "league",
                        modifier = Modifier
                            .size(width = 20.dp, height = 20.dp)
                    )

                    Spacer(modifier = Modifier.size(10.dp))


                    Text(
                        "${dataItem[0].league?.country} - ${dataItem[0].league?.name!!}",
                        Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            )
                            .weight(1f),
                        fontWeight = FontWeight.Bold
                    )

                }

                if (collapsed.value) {
                    Divider()
                }


            }

            items(dataItem) { prediction ->
                PredictionContent(prediction = prediction, collapsed = collapsed.value)
            }

        }
    }

}


@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun PredictionContent(prediction: Prediction, collapsed: Boolean) {

    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }

    var predictionOutcome by remember { mutableStateOf("") }
    var odds by remember { mutableStateOf("") }
    val oddsList = prediction.odds


    when (prediction.predictionString) {

        "Home Win or Draw" -> {

            if (prediction.score?.fulltime?.home == null) {
                predictionOutcome = "TBD"
            } else {
                when (prediction.status?.short) {
                    "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                        predictionOutcome =
                            if (prediction.score.fulltime.home >= prediction.score.fulltime.away!!) {
                                "WON"
                            } else {
                                "LOST"
                            }
                    }
                    "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                        predictionOutcome = "TBD"
                    }
                }
            }


            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 12
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Home/Draw"
                    }?.odd ?: ""

                } else {
                    ""
                }
            }else{
                odds = ""
            }

        }
        "Away Win or Draw" -> {

            if (prediction.score?.fulltime?.away == null) {
                predictionOutcome = "TBD"
            } else {
                when (prediction.status?.short) {
                    "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                        predictionOutcome =
                            if (prediction.score.fulltime.away >= prediction.score.fulltime.home!!) {
                                "WON"
                            } else {
                                "LOST"
                            }
                    }
                    "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                        predictionOutcome = "TBD"
                    }
                }
            }

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 12
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Draw/Away"
                    }?.odd ?: ""

                } else {
                    ""
                }
            }else{
                odds = ""
            }
        }
        "Home Win" -> {

            if (prediction.score?.fulltime?.home == null) {
                predictionOutcome = "TBD"
            } else {
                when (prediction.status?.short) {
                    "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                        predictionOutcome =
                            if (prediction.score.fulltime.home > prediction.score.fulltime.away!!) {
                                "WON"
                            } else {
                                "LOST"
                            }
                    }
                    "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                        predictionOutcome = "TBD"
                    }
                }
            }

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 1
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Home"
                    }?.odd ?: ""

                } else {
                    ""
                }
            }else{
                odds = ""
            }


        }
        "Away Win" -> {

            if (prediction.score?.fulltime?.away == null) {
                predictionOutcome = "TBD"
            } else {
                when (prediction.status?.short) {
                    "FT", "ET", "AET", "PEN", "WO", "P", "BT", "AWD" -> {
                        predictionOutcome =
                            if (prediction.score.fulltime.away > prediction.score.fulltime.home!!) {
                                "WON"
                            } else {
                                "LOST"
                            }
                    }
                    "LIVE", "TBD", "NS", "1H", "HT", "2H", "SUSP", "INT", "PST", "CANC", "ABD" -> {
                        predictionOutcome = "TBD"
                    }
                }
            }

            if (oddsList != null) {
                odds = if (oddsList.isNotEmpty()) {
                    val betDoubleChance = oddsList[0].bets.firstOrNull {
                        it.id == 1
                    }

                    betDoubleChance?.values?.firstOrNull {
                        it.value == "Away"
                    }?.odd ?: ""

                } else {
                    ""
                }
            }else{
                odds = ""
            }

        }
        "" -> {

            predictionOutcome = "TBD"
            odds = ""
        }

    }

    Log.e("Game is ", predictionOutcome)


    AnimatedVisibility(
        visible = !collapsed,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {

        if (!collapsed) {
            PredictionListItemDark(prediction, predictionOutcome, odds)

        }

    }

}

@ExperimentalCoilApi
@Composable
fun PredictionListItemDark(prediction: Prediction, predictionOutcome: String, odds: String) {

    Surface(color = colorResource(R.color.dark_mode)) {

        Card(
            backgroundColor = colorResource(R.color.card_bg),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .height(120.dp), elevation = 6.dp
        ) {

            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (icon, text) = createRefs()
                        Icon(
                            painter = painterResource(id = R.drawable.outline_star_white_24),
                            contentDescription = "fav",
                            modifier = Modifier
                                .size(width = 27.dp, height = 27.dp)
                                .padding(start = 5.dp, top = 5.dp)
                                .constrainAs(icon) {
                                    start.linkTo(parent.start)
                                }
                        )

                        Text(
                            text = prediction.date.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
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
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 10.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.homeLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 38.dp, height = 38.dp)
                                .offset(y = (-10).dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = prediction.homeName.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(
                        modifier = Modifier.width(100.dp)
                    ) {
                        var goals: String = if (prediction.goals?.home == null) {
                            "VS"
                        } else {
                            "${prediction.goals?.home} - ${prediction.goals?.away}"
                        }

                        Text(
                            text = goals,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )

                        var predColor = colorResource(R.color.colorLightGrey)


                        when (predictionOutcome) {
                            "TBD" -> {
                                predColor = colorResource(R.color.colorLightGrey)
                            }
                            "WON" -> {
                                predColor = colorResource(R.color.dark_green)
                            }
                            "LOST" -> {
                                predColor = colorResource(R.color.colorLightRed)
                            }
                        }

                        Surface(
                            color = predColor,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .size(width = 104.dp, height = 17.dp)

                        ) {
                            Text(
                                text = prediction.predictionString.toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                        }


                        var theOdds = ""

                        if (odds.isNotEmpty()) {
                            theOdds = "Odds: $odds"
                        }

                        Text(
                            text = theOdds,
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            maxLines = 2,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }

                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.CenterVertically)
                            .padding(bottom = 10.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(prediction.awayLogo),
                            contentDescription = "team_one",
                            modifier = Modifier
                                .size(width = 38.dp, height = 38.dp)
                                .offset(y = (-10).dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = prediction.awayName.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 2,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                }


            }


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
