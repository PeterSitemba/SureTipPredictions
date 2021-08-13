package faba.app.suretippredictions.models.events

data class Away(
    val coach: List<Coach>,
    val missing_players: List<Any>,
    val starting_lineups: List<StartingLineup>,
    val substitutes: List<Substitute>
)