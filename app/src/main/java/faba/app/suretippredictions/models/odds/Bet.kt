package faba.app.suretippredictions.models.odds

data class Bet(
    val id: Int,
    val name: String,
    val values: List<Value>
)