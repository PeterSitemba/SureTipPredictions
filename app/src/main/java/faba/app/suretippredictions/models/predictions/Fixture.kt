package faba.app.suretippredictions.models.predictions

import faba.app.suretippredictions.models.fixtures.Periods
import faba.app.suretippredictions.models.fixtures.Status
import faba.app.suretippredictions.models.fixtures.Venue

data class Fixture(
    val id: Int,
    val date: String?,
    val periods: Periods?,
    val referee: String?,
    val status: Status?,
    val timestamp: Int?,
    val timezone: String?,
    val venue: Venue?,
)
