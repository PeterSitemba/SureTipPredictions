package faba.app.suretippredictions.models.predictions

//keeping
data class Penalty(
    val total: Int,
    val scored: PenaltyScored,
    val missed: PenaltyMissed
)
