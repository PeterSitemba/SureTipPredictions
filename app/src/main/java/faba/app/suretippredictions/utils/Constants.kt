package faba.app.suretippredictions.utils


object Constants {
    const val URL: String =
        "https://fqq4pp5xhjbijggrrxnsl62szq.appsync-api.me-south-1.amazonaws.com/graphql/"

    const val internetError: String =
        "Something went wrong.\nCheck your internet connection"

    val mainLeaguesList: List<Int> = arrayListOf(39, 61, 78, 88, 135, 140, 173, 421, 847, 432, 441, 149, 430, 383)

    const val EXPAND_ANIMATION_DURATION = 200
    const val COLLAPSE_ANIMATION_DURATION = 200
    const val FADE_IN_ANIMATION_DURATION = 350
    const val FADE_OUT_ANIMATION_DURATION = 200
    const val SCALE_IN_ANIMATION_DURATION = 200
    const val SCALE_OUT_ANIMATION_DURATION = 100
}