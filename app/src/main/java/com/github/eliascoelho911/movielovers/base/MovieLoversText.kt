package com.github.eliascoelho911.movielovers.base

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.ui.theme.Green

@Composable
fun TextRetractable(text: String, maxLinesColapsed: Int) {
    var isColapsed by remember { mutableStateOf(true) }
    val iconDegress by animateFloatAsState(
        if (isColapsed) 0f else 180f
    )
    Column(modifier = Modifier.clickable {
        isColapsed = isColapsed.not()
    }, horizontalAlignment = Alignment.End) { 0
        Text(
            text = text,
            maxLines = maxLinesColapsed.takeIf { isColapsed } ?: Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = stringResource(id = R.string.read_more), color = Green)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .rotate(iconDegress)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.ic_angle_down),
                contentDescription = stringResource(id = R.string.read_more),
                tint = Green
            )
        }
    }
}