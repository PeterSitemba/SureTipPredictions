package faba.app.suretippredictions.models.events

data class StartingLineupX(
    val lineup_number: String,
    val lineup_player: String,
    val lineup_position: String,
    val player_key: String
)