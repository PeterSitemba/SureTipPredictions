package faba.app.suretippredictions.models.predictions

import faba.app.suretippredictions.models.fixtures.Goals
import faba.app.suretippredictions.models.fixtures.League
import faba.app.suretippredictions.models.fixtures.Score
import faba.app.suretippredictions.models.fixtures.Teams

data class FixturesH2H(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)
