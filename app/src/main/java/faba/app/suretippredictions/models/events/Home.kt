package faba.app.suretippredictions.models.events

data class Home(
    val coach: List<CoachX>,
    val missing_players: List<Any>,
    val starting_lineups: List<StartingLineupX>,
    val substitutes: List<SubstituteX>
)