package faba.app.suretippredictions.models.odds

data class Bookmaker(
    val bets: List<Bet>,
    val id: Int,
    val name: String
)