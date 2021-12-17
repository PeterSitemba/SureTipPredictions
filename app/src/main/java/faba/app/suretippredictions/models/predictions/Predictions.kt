package faba.app.suretippredictions.models.predictions

//keeping
data class Predictions(
    val winner: Winner,
    val win_or_draw: Boolean,
    val under_over: Any?,
    val goals: Goals,
    val advice: String,
    val percent: Percent
)
