package com.github.eliascoelho911.movielovers.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.ui.theme.Nunito
import com.github.eliascoelho911.movielovers.ui.theme.Red
import java.util.*

@Composable
@Preview
fun MovieLoversLogo(
    color: Color = Red,
    fontSize: TextUnit = 23.sp
) {
    Text(
        text = stringResource(id = R.string.app_name).lowercase(Locale.getDefault()),
        fontFamily = Nunito,
        fontWeight = ExtraBold,
        fontSize = fontSize,
        color = color,
        maxLines = 1
    )
}