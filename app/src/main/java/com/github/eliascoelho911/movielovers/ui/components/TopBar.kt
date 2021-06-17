package com.github.eliascoelho911.movielovers.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.preferredValue

@ExperimentalAnimationApi
@Composable
fun MovieLoversTopBar(
    title: @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit) = {},
    navigationIcon: @Composable (() -> Unit)? = null
) {
    TopAppBar (title = title, actions = actions, navigationIcon = navigationIcon)
}
