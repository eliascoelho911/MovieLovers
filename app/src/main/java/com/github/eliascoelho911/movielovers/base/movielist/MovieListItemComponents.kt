package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.ui.theme.Yellow
import java.math.RoundingMode

@Composable
fun BodyText(modifier: Modifier = Modifier, text: String, maxLines: Int = Int.MAX_VALUE) {
    ProvideTextStyle(value = MaterialTheme.typography.body2) {
        Text(
            modifier = modifier,
            text = text,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines
        )
    }
}

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier, text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Composable
fun VoteAverage(modifier: Modifier = Modifier, voteAverage: Double) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Filled.Star,
            contentDescription = stringResource(id = R.string.popularity),
            tint = Yellow
        )
        BodyText(
            modifier = Modifier.padding(start = 4.dp),
            text = voteAverage.toBigDecimal()
                .setScale(1, RoundingMode.UP).toString()
        )
    }
}