package faba.app.suretippredictions.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import faba.app.suretippredictions.models.predictions.*
import faba.app.suretippredictions.models.odds.*
import faba.app.suretippredictions.models.odds.Fixture

class Converter {

    @TypeConverter
    fun toStatus(json: String): Status {
        val type = object : TypeToken<Status>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonStatus(status: Status): String {
        val type = object : TypeToken<Status>() {}.type
        return Gson().toJson(status, type)
    }

    @TypeConverter
    fun toScore(json: String): Score {
        val type = object : TypeToken<Score>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonScore(score: Score): String {
        val type = object : TypeToken<Score>() {}.type
        return Gson().toJson(score, type)
    }

    @TypeConverter
    fun toPredictions(json: String): Predictions {
        val type = object : TypeToken<Predictions>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonPredictions(predictions: Predictions): String {
        val type = object : TypeToken<Predictions>() {}.type
        return Gson().toJson(predictions, type)
    }

    @TypeConverter
    fun toLeague(json: String): League {
        val type = object : TypeToken<League>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonLeague(league: League): String {
        val type = object : TypeToken<League>() {}.type
        return Gson().toJson(league, type)
    }

    @TypeConverter
    fun toLast5(json: String): Last5 {
        val type = object : TypeToken<Last5>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonLast5(last5: Last5): String {
        val type = object : TypeToken<Last5>() {}.type
        return Gson().toJson(last5, type)
    }

    @TypeConverter
    fun toFixtures(json: String): Fixtures {
        val type = object : TypeToken<Fixtures>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonFixtures(fixtures: Fixtures): String {
        val type = object : TypeToken<Fixtures>() {}.type
        return Gson().toJson(fixtures, type)
    }

    @TypeConverter
    fun toGoalsTotal(json: String): GoalsTotal {
        val type = object : TypeToken<GoalsTotal>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonGoalsTotal(goalsTotal: GoalsTotal): String {
        val type = object : TypeToken<GoalsTotal>() {}.type
        return Gson().toJson(goalsTotal, type)
    }

    @TypeConverter
    fun toGoalsAverage(json: String): GoalsAverage {
        val type = object : TypeToken<GoalsAverage>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonGoalsAverage(goalsAverage: GoalsAverage): String {
        val type = object : TypeToken<GoalsAverage>() {}.type
        return Gson().toJson(goalsAverage, type)
    }

    @TypeConverter
    fun toBiggest(json: String): Biggest {
        val type = object : TypeToken<Biggest>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonBiggest(biggest: Biggest): String {
        val type = object : TypeToken<Biggest>() {}.type
        return Gson().toJson(biggest, type)
    }

    @TypeConverter
    fun toCleanSheet(json: String): CleanSheet {
        val type = object : TypeToken<CleanSheet>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonCleanSheet(cleanSheet: CleanSheet): String {
        val type = object : TypeToken<CleanSheet>() {}.type
        return Gson().toJson(cleanSheet, type)
    }

    @TypeConverter
    fun toFailedToScore(json: String): FailedToScore {
        val type = object : TypeToken<FailedToScore>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonFailedToScore(failedToScore: FailedToScore): String {
        val type = object : TypeToken<FailedToScore>() {}.type
        return Gson().toJson(failedToScore, type)
    }

    @TypeConverter
    fun toPenalty(json: String): Penalty {
        val type = object : TypeToken<Penalty>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonPenalty(penalty: Penalty): String {
        val type = object : TypeToken<Penalty>() {}.type
        return Gson().toJson(penalty, type)
    }

    @TypeConverter
    fun toComparison(json: String): Comparison {
        val type = object : TypeToken<Comparison>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonComparison(comparison: Comparison): String {
        val type = object : TypeToken<Comparison>() {}.type
        return Gson().toJson(comparison, type)
    }

    @TypeConverter
    fun toFixturesH2H(json: String): MutableList<FixturesH2H> {
        val type = object : TypeToken<MutableList<FixturesH2H>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonFixturesH2H(fixturesH2H: MutableList<FixturesH2H>): String {
        val type = object : TypeToken<MutableList<FixturesH2H>>() {}.type
        return Gson().toJson(fixturesH2H, type)
    }

    @TypeConverter
    fun toFixture(json: String): Fixture {
        val type = object : TypeToken<Fixture>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonFixture(fixture: Fixture): String {
        val type = object : TypeToken<Fixture>() {}.type
        return Gson().toJson(fixture, type)
    }

    @TypeConverter
    fun toBookmaker(json: String): List<Bookmaker> {
        val type = object : TypeToken<List<Bookmaker>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonBookmaker(bookmaker: List<Bookmaker>): String {
        val type = object : TypeToken<List<Bookmaker>>() {}.type
        return Gson().toJson(bookmaker, type)
    }

}