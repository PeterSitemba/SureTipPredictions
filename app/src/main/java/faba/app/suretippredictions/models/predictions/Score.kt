package faba.app.suretippredictions.models.predictions

//keeping
data class Score(
    val extratime: Extratime,
    val fulltime: Fulltime,
    val halftime: Halftime,
    val penalty: PenaltyFixture
)