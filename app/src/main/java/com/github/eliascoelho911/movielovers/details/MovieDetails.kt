package com.github.eliascoelho911.movielovers.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.base.TmdbImage
import com.github.eliascoelho911.movielovers.base.TextRetractable
import com.github.eliascoelho911.movielovers.base.VoteAverage
import com.github.eliascoelho911.movielovers.base.movielist.listHorizontalItemPadding
import com.github.eliascoelho911.movielovers.model.Department
import com.github.eliascoelho911.movielovers.model.Genre
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import com.github.eliascoelho911.movielovers.ui.theme.Green
import com.github.eliascoelho911.movielovers.ui.theme.LightGray
import com.google.accompanist.insets.statusBarsPadding
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScopeInstance.pin
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val SectionsVerticalPadding = 16.dp
private val ScreenPadding = 16.dp

@Composable
fun MovieDetailsScreen(movieDetailsViewModel: MovieDetailsViewModel, onBackPressed: () -> Unit) {
    val state = rememberCollapsingToolbarScaffoldState()
    val toolbarCollapsed = state.toolbarState.progress == 0f

    val backgroundColor by animateColorAsState(
        if (toolbarCollapsed) MaterialTheme.colors.primary else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val contentColor by animateColorAsState(
        if (toolbarCollapsed) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    val movie = movieDetailsViewModel.movie

    CollapsingToolbarScaffold(
        modifier = Modifier.statusBarsPadding(),
        state = state,
        toolbar = {
            MovieDetailsAppBar(
                onBackPressed = onBackPressed,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                backdropPath = movie.backdropPath ?: "",
            )
        },
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed
    ) {
        Spacer(modifier = Modifier.height(1500.dp))
        var genres: List<Genre> by remember { mutableStateOf(emptyList()) }
        if (movie.genreIds != null) {
            val resultGenres by movieDetailsViewModel.findGenres(genreIds = movie.genreIds)
                .observeAsState()
            resultGenres?.onSuccess { freshGenres ->
                genres = freshGenres
            }
        }
        val credits by movieDetailsViewModel.credits.observeAsState()
        MovieDetailsContent(
            posterPath = movie.posterPath ?: "",
            title = movie.title,
            voteAverage = movie.voteAverage,
            genres = genres,
            releaseDate = movie.releaseDate,
            directors = credits?.filter { it.department == Department.DIRECTING }
                ?.joinToString { it.name } ?: "",
            producers = credits?.filter { it.department == Department.PRODUCTION }
                ?.joinToString { it.name } ?: "",
            storyline = movie.overview ?: "",
            actors = credits?.filter { it.department == Department.ACTING }
                ?.map { Actor(name = it.name, profileImagePath = it.profileImagePath ?: "") }
                ?: emptyList()
        )
    }
}

@Composable
private fun MovieDetailsContent(
    posterPath: String,
    title: String,
    voteAverage: Double,
    genres: List<Genre>,
    releaseDate: LocalDate?,
    directors: String,
    producers: String,
    storyline: String,
    actors: List<Actor>
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = ScreenPadding, start = ScreenPadding, end = ScreenPadding)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                TmdbImage(
                    path = posterPath,
                    contentDescription = stringResource(id = R.string.backdrop),
                    modifier = Modifier
                        .width(90.dp)
                        .height(128.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Column(modifier = Modifier.padding(horizontal = ScreenPadding)) {
                    Title(text = title)
                    VoteAverage(
                        modifier = Modifier.padding(vertical = 8.dp),
                        voteAverage = voteAverage
                    )
                    GenreBlocks(genres)
                }
            }
            SpaceDefault()
            Description(
                title = stringResource(id = R.string.release_date),
                text = releaseDate?.format(
                    DateTimeFormatter.ofPattern(
                        "dd 'de' MMMM, yyyy",
                        Locale("pt", "BR")
                    )
                ) ?: ""
            )
            Description(
                title = stringResource(id = R.string.director),
                text = directors,
                color = Green
            )
            Description(
                title = stringResource(id = R.string.producer),
                text = producers,
                color = Green
            )
            SpaceDefault()
            Title(text = stringResource(id = R.string.storyline))
            SpaceDefault()
            TextRetractable(text = storyline, maxLinesColapsed = 4)
            SpaceDefault()
            Title(text = stringResource(id = R.string.cast))
            SpaceDefault()
        }
        ActorsHorizontalList(actors)
    }
}

@Composable
private fun ActorsHorizontalList(actors: List<Actor>) {
    LazyRow {
        itemsIndexed(actors, itemContent = { position, actor ->
            val size = 75.dp
            Column(
                modifier = Modifier
                    .listHorizontalItemPadding(
                        listSize = actors.size,
                        position = position,
                        paddingSides = ScreenPadding
                    )
                    .width(size),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TmdbImage(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape),
                    path = actor.profileImagePath,
                    contentDescription = actor.name
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = actor.name)
            }
        })
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable
private fun SpaceDefault() {
    Spacer(modifier = Modifier.height(SectionsVerticalPadding))
}

@Composable
private fun GenreBlocks(genres: List<Genre>) {
    Row(modifier = Modifier.horizontalScroll(state = rememberScrollState())) {
        val maxNumberOfGenres = 3
        val numberOfGenres =
            genres.size.takeIf { it <= maxNumberOfGenres } ?: maxNumberOfGenres

        for (i in 0 until numberOfGenres) {
            val currentGenre = genres[i]
            Surface(
                modifier = Modifier.listHorizontalItemPadding(
                    listSize = numberOfGenres,
                    position = i
                ),
                shape = MaterialTheme.shapes.small,
                color = LightGray
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                    text = currentGenre.name,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
private fun Description(title: String, text: String, color: Color = DarkGray) {
    Row {
        Text(
            text = "$title: ",
            fontWeight = FontWeight.Bold
        )
        Text(text = text, color = color, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun MovieDetailsAppBar(
    onBackPressed: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    backdropPath: String,
) {
    TmdbImage(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            )
            .fillMaxWidth()
            .height(200.dp)
            .pin(),
        path = backdropPath,
        contentDescription = stringResource(id = R.string.backdrop)
    )
    TopAppBar(
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = stringResource(id = R.string.like),
                    tint = contentColor
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.back),
                    tint = contentColor
                )
            }
        },
        title = {})
}

private data class Actor(val name: String, val profileImagePath: String)
