package faba.app.suretippredictions.models.predictions

data class Prediction(
    val id: Int,
    val awayGoalsForAverage: GoalsAverage,
    val comparison: Comparison,
    val homeForm: String,
    val status: Status,
    val score: Score,
    val homeName: String,
    val awayId: Int,
    val homeFailedToScore: FailedToScore,
    val date: String,
    val awayLastFive: Last5,
    val homeCleanSheet: CleanSheet,
    val awayLogo: String,
    val awayGoalsForTotal: GoalsTotal,
    val homeGoalsForTotal: GoalsTotal,
    val homeGoalsAgainstAverage: GoalsAverage,
    val homeLastFive: Last5,
    val homeGoalsForAverage: GoalsAverage,
    val league: League,
    val awayFailedToScore: FailedToScore,
    val awayGoalsAgainstTotal: GoalsTotal,
    val awayName: String,
    val homeLogo: String,
    val awayBiggest: Biggest,
    val homeBiggest: Biggest,
    val homeGoalsAgainstTotal: GoalsTotal,
    val awayCleanSheet: CleanSheet,
    val awayFixtures: Fixtures,
    val homeId: Int,
    val homeFixtures: Fixtures,
    val predictions: Predictions,
    val awayPenalty: Penalty,
    val homePenalty: Penalty,
    val awayGoalsAgainstAverage: GoalsAverage,
    val awayForm: String,
    val h2h: MutableList<FixturesH2H>

















)
