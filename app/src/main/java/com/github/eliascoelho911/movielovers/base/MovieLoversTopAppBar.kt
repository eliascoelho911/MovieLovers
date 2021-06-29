package com.github.eliascoelho911.movielovers.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun MovieLoversTopAppBar(
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit
) {
    TopAppBar(title = title, navigationIcon = navigationIcon, actions = actions)
}
