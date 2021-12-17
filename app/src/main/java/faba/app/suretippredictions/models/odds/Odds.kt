package faba.app.suretippredictions.models.odds

data class Odds(
    val id: Int,
    val date: String,
    val fixture: Fixture?,
    val bookmakers: List<Bookmaker>
)