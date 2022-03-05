package faba.app.suretippredictions.view.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import faba.app.suretippredictions.utils.Constants

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun scaleInFabAnim() = remember {
    scaleIn(
        animationSpec = TweenSpec(
            durationMillis = Constants.SCALE_IN_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    )
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun scaleOutFabAnim() = remember {
    scaleOut(
        animationSpec = TweenSpec(
            durationMillis = Constants.SCALE_OUT_ANIMATION_DURATION,
            easing = FastOutLinearInEasing
        )
    )
}


