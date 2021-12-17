package faba.app.suretippredictions.models.fixtures

data class Fixture(
    val id: Int,
    val date: String?,
    val periods: Periods?,
    val referee: String?,
    val status: Status?,
    val timestamp: Int?,
    val timezone: String?,
    val venue: Venue?,
    val goals: Goals?,
    val league: League?,
    val score: Score?,
    val events: List<Event>?,
    val teams: Teams?
)