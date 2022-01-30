package faba.app.suretippredictions.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import faba.app.suretippredictions.models.fixtures.Goals
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.predictions.*

@Entity(tableName = "predictions_table")
data class Prediction(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("date")
    val date: String?,
    @SerializedName("gameTime")
    val gameTime: String?,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    @TypeConverters(Converter::class) val status: Status?,
    @SerializedName("score")
    @ColumnInfo(name = "score")
    @TypeConverters(Converter::class) val score: Score?,
    @SerializedName("goals")
    @ColumnInfo(name = "goals")
    @TypeConverters(Converter::class) val goals: Goals?,
    @SerializedName("predictions")
    @TypeConverters(Converter::class) val predictions: Predictions?,
    @SerializedName("league")
    @TypeConverters(Converter::class) val league: League?,
    @SerializedName("homeId")
    val homeId: Int?,
    @SerializedName("homeName")
    val homeName: String?,
    @SerializedName("homeLogo")
    val homeLogo: String?,
    @SerializedName("homeLastFive")
    @TypeConverters(Converter::class) val homeLastFive: Last5?,
    @SerializedName("homeForm")
    val homeForm: String?,
    @SerializedName("homeFixtures")
    @TypeConverters(Converter::class) val homeFixtures: Fixtures?,
    @SerializedName("homeGoalsForTotal")
    @TypeConverters(Converter::class) val homeGoalsForTotal: GoalsTotal?,
    @SerializedName("homeGoalsForAverage")
    @TypeConverters(Converter::class) val homeGoalsForAverage: GoalsAverage?,
    @SerializedName("homeGoalsAgainstTotal")
    @TypeConverters(Converter::class) val homeGoalsAgainstTotal: GoalsTotal?,
    @SerializedName("homeGoalsAgainstAverage")
    @TypeConverters(Converter::class) val homeGoalsAgainstAverage: GoalsAverage?,
    @SerializedName("homeBiggest")
    @TypeConverters(Converter::class) val homeBiggest: Biggest?,
    @SerializedName("homeCleanSheet")
    @TypeConverters(Converter::class) val homeCleanSheet: CleanSheet?,
    @SerializedName("homeFailedToScore")
    @TypeConverters(Converter::class) val homeFailedToScore: FailedToScore?,
    @SerializedName("homePenalty")
    @TypeConverters(Converter::class) val homePenalty: Penalty?,
    @SerializedName("awayId")
    val awayId: Int?,
    @SerializedName("awayName")
    val awayName: String?,
    @SerializedName("awayLogo")
    val awayLogo: String?,
    @SerializedName("awayLastFive")
    @TypeConverters(Converter::class) val awayLastFive: Last5?,
    @SerializedName("awayForm")
    val awayForm: String?,
    @SerializedName("awayFixtures")
    @TypeConverters(Converter::class) val awayFixtures: Fixtures?,
    @SerializedName("awayGoalsForTotal")
    @TypeConverters(Converter::class) val awayGoalsForTotal: GoalsTotal?,
    @SerializedName("awayGoalsForAverage")
    @TypeConverters(Converter::class) val awayGoalsForAverage: GoalsAverage?,
    @SerializedName("awayGoalsAgainstTotal")
    @TypeConverters(Converter::class) val awayGoalsAgainstTotal: GoalsTotal?,
    @SerializedName("awayGoalsAgainstAverage")
    @TypeConverters(Converter::class) val awayGoalsAgainstAverage: GoalsAverage?,
    @SerializedName("awayBiggest")
    @TypeConverters(Converter::class) val awayBiggest: Biggest?,
    @SerializedName("awayCleanSheet")
    @TypeConverters(Converter::class) val awayCleanSheet: CleanSheet?,
    @SerializedName("awayFailedToScore")
    @TypeConverters(Converter::class) val awayFailedToScore: FailedToScore?,
    @SerializedName("awayPenalty")
    @TypeConverters(Converter::class) val awayPenalty: Penalty?,
    @SerializedName("comparison")
    @TypeConverters(Converter::class) val comparison: Comparison?,
    @SerializedName("h2h")
    @TypeConverters(Converter::class) val h2h: MutableList<FixturesH2H>?,
    @SerializedName("odds")
    @TypeConverters(Converter::class) val odds: MutableList<Bookmaker>?,
    @SerializedName("predictionString")
    val predictionString: String?,
)

data class PredictionUpdate(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    @ColumnInfo(name = "status")
    @TypeConverters(Converter::class) val status: Status?,
    @SerializedName("score")
    @ColumnInfo(name = "score")
    @TypeConverters(Converter::class) val score: Score?,
    @SerializedName("goals")
    @ColumnInfo(name = "goals")
    @TypeConverters(Converter::class) val goals: Goals?
)

data class PredictionUpdateOdds(
    @SerializedName("id")
    val id: Int,
    @SerializedName("odds")
    @TypeConverters(Converter::class) val odds: MutableList<Bookmaker>?
)
