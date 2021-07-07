package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import com.github.eliascoelho911.movielovers.base.TmdbImage
import com.github.eliascoelho911.movielovers.base.VoteAverage
import com.github.eliascoelho911.movielovers.model.Movie

@Composable
fun MovieVerticalList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    item: @Composable (position: Int, currentMovie: Movie) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            item(position = position, currentMovie = currentMovie)
        })
    }
}

@Composable
fun MovieVerticalListItem(
    modifier: Modifier,
    pathImage: String,
    title: String,
    genre: String,
    releaseYear: Int? = null,
    voteAverage: Double
) {
    Row(modifier = modifier.itemModifier()) {
        TmdbImage(
            path = pathImage,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        Spacer(modifier = Modifier.width(16.dp))

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                if (releaseYear != null)
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
