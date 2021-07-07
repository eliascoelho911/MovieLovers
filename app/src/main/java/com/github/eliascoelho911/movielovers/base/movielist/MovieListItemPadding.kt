package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.listVerticalItemPadding(
    listSize: Int,
    position: Int,
    paddingBetweenItems: Dp = 8.dp,
    paddingSides: Dp = 0.dp
): Modifier =
    composed {
        val paddingValues = when (position) {
            0 -> PaddingValues(
                top = paddingSides,
                bottom = paddingBetweenItems / 2
            )
            listSize - 1 -> PaddingValues(
                top = paddingBetweenItems / 2,
                bottom = paddingSides
            )
            else -> PaddingValues(vertical = paddingBetweenItems / 2)
        }

        this.padding(paddingValues)
    }

fun Modifier.listHorizontalItemPadding(
    listSize: Int,
    position: Int,
    paddingBetweenItems: Dp = 8.dp,
    paddingSides: Dp = 0.dp
): Modifier =
    composed {
        val paddingValues = when (position) {
            0 -> PaddingValues(
                start = paddingSides,
                end = paddingBetweenItems / 2
            )
            listSize - 1 -> PaddingValues(
                start = paddingBetweenItems / 2,
                end = paddingSides
            )
            else -> PaddingValues(horizontal = paddingBetweenItems / 2)
        }

        this.padding(paddingValues)
    }