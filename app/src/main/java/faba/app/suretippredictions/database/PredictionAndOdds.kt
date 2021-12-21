package faba.app.suretippredictions.database

import androidx.room.Embedded
import androidx.room.Relation

data class PredictionAndOdds(
    @Embedded val prediction: Prediction,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val odds: Odds
)
