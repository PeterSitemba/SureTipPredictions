package faba.app.suretippredictions.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import faba.app.suretippredictions.models.fixtures.Goals
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.predictions.*

@Entity(tableName = "predictions_table")
data class Prediction(
    @PrimaryKey val id: Int,
    val date: String?,
    val gameTime: String?,
    @TypeConverters(Converter::class) val status: Status?,
    @TypeConverters(Converter::class) val score: Score?,
    @TypeConverters(Converter::class) val goals: Goals?,
    @TypeConverters(Converter::class) val predictions: Predictions?,
    @TypeConverters(Converter::class) val league: League?,
    val homeId: Int?,
    val homeName: String?,
    val homeLogo: String?,
    @TypeConverters(Converter::class) val homeLastFive: Last5?,
    val homeForm: String?,
    @TypeConverters(Converter::class) val homeFixtures: Fixtures?,
    @TypeConverters(Converter::class) val homeGoalsForTotal: GoalsTotal?,
    @TypeConverters(Converter::class) val homeGoalsForAverage: GoalsAverage?,
    @TypeConverters(Converter::class) val homeGoalsAgainstTotal: GoalsTotal?,
    @TypeConverters(Converter::class) val homeGoalsAgainstAverage: GoalsAverage?,
    @TypeConverters(Converter::class) val homeBiggest: Biggest?,
    @TypeConverters(Converter::class) val homeCleanSheet: CleanSheet?,
    @TypeConverters(Converter::class) val homeFailedToScore: FailedToScore?,
    @TypeConverters(Converter::class) val homePenalty: Penalty?,
    val awayId: Int?,
    val awayName: String?,
    val awayLogo: String?,
    @TypeConverters(Converter::class) val awayLastFive: Last5?,
    val awayForm: String?,
    @TypeConverters(Converter::class) val awayFixtures: Fixtures?,
    @TypeConverters(Converter::class) val awayGoalsForTotal: GoalsTotal?,
    @TypeConverters(Converter::class) val awayGoalsForAverage: GoalsAverage?,
    @TypeConverters(Converter::class) val awayGoalsAgainstTotal: GoalsTotal?,
    @TypeConverters(Converter::class) val awayGoalsAgainstAverage: GoalsAverage?,
    @TypeConverters(Converter::class) val awayBiggest: Biggest?,
    @TypeConverters(Converter::class) val awayCleanSheet: CleanSheet?,
    @TypeConverters(Converter::class) val awayFailedToScore: FailedToScore?,
    @TypeConverters(Converter::class) val awayPenalty: Penalty?,
    @TypeConverters(Converter::class) val comparison: Comparison?,
    @TypeConverters(Converter::class) val h2h: MutableList<FixturesH2H>?,
    @TypeConverters(Converter::class) val odds: MutableList<Bookmaker>?,
    val predictionString: String?,
)

data class PredictionUpdate(
    val id: Int,
    @TypeConverters(Converter::class) val status: Status?,
    @TypeConverters(Converter::class) val score: Score?,
    @TypeConverters(Converter::class) val goals: Goals?
)

data class PredictionUpdateOdds(
    val id: Int,
    @TypeConverters(Converter::class) val odds: MutableList<Bookmaker>?
)
