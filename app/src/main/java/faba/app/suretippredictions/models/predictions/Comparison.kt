package faba.app.suretippredictions.models.predictions

//keeping
data class Comparison(
    val att: Att,
    val def: Def,
    val form: Form,
    val goals: Goals,
    val h2h: H2hComparison,
    val poisson_distribution: PoissonDistribution,
    val total: TotalComparison
)