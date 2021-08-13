package faba.app.suretippredictions.models.events

data class Card(
    val away_fault: String,
    val away_player_id: String,
    val card: String,
    val home_fault: String,
    val home_player_id: String,
    val info: String,
    val time: String
)