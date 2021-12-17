package faba.app.suretippredictions.models.odds

data class Odds(
    val bookmakers: List<Bookmaker>,
    val date: String,
    val fixture: Fixture,
    val id: Int,
    val update: String
)