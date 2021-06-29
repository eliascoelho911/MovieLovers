package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import com.github.eliascoelho911.movielovers.base.MovieImage

@Composable
fun MovieVerticalListItem(
    pathImage: String,
    title: String,
    genre: String,
    releaseYear: Int,
    voteAverage: Double
) {
    Row {
        MovieImage(
            path = pathImage,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        Spacer(modifier = Modifier.width(16.dp))

        ConstraintLayout(modifier = Modifier.itemModifier()) {
            val (titleRef, voteAverageRef, genreAndReleaseYearRef) = createRefs()

            TitleText(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(genreAndReleaseYearRef.top, 4.dp)
                    end.linkTo(voteAverageRef.start, 8.dp)
                    width = fillToConstraints
                    height = fillToConstraints
                }, text = title
            )
            VoteAverage(
                modifier = Modifier.constrainAs(voteAverageRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                voteAverage = voteAverage
            )
            Column(modifier = Modifier.constrainAs(genreAndReleaseYearRef) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                top.linkTo(titleRef.bottom, 4.dp)
            }) {
                BodyText(text = genre, maxLines = 1)
                BodyText(text = releaseYear.toString())
            }
        }
    }
}

private fun Modifier.imageModifier(): Modifier = composed {
    this
        .width(imageWidth)
        .height(itemHeight)
        .clip(MaterialTheme.shapes.medium)
}

private fun Modifier.itemModifier(): Modifier = composed {
    this
        .height(itemHeight)
        .fillMaxWidth()
}

private val imageWidth = 65.dp
private val itemHeight = (imageWidth / 0.7.dp).dp
