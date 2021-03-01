package com.example.androiddevchallenge.pet

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.MissedPet
import com.example.androiddevchallenge.PetRepo
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.utils.NetworkImage
import com.example.androiddevchallenge.utils.backHandler
import com.example.androiddevchallenge.utils.scrim
import kotlinx.coroutines.launch

private val FabSize = 56.dp
private const val ExpandedSheetAlpha = 0.96f

@Composable
fun MissedPetDetails(
    id: Long,
    upPress: () -> Unit
) {
    val missedPet = remember(id) { PetRepo.getMissingPet(id) }
    MissedPetDetails(missedPet, upPress)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MissedPetDetails(
    missedPet: MissedPet,
    upPress: () -> Unit
) {
    BoxWithConstraints {
        val sheetState = rememberSwipeableState(SheetState.Closed)
        val scope = rememberCoroutineScope()
        val fabSize = with(LocalDensity.current) { FabSize.toPx() }
        val dragRange = constraints.maxHeight - fabSize
        backHandler(
            enabled = sheetState.currentValue == SheetState.Open,
            onBack = {
                scope.launch {
                    sheetState.animateTo(SheetState.Closed)
                }
            }
        )

        Box(
            // The Lessons sheet is initially closed and appears as a FAB. Make it openable by
            // swiping or clicking the FAB.
            Modifier.swipeable(
                state = sheetState,
                anchors = mapOf(
                    0f to SheetState.Closed,
                    -dragRange to SheetState.Open
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
        ) {
            val openFraction = if (sheetState.offset.value.isNaN()) {
                0f
            } else {
                -sheetState.offset.value / dragRange
            }.coerceIn(0f, 1f)
            MissedPetDescription(missedPet, upPress)
        }
    }
}

private enum class SheetState { Open, Closed }



@Composable
private fun MissedPetDescription(
    missedPet: MissedPet,
    upPress: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item { MissedPetDescriptionHeader(missedPet, upPress) }
            item { MissedPetDescriptionBody(missedPet) }
        }
    }
}

@Composable
private fun MissedPetDescriptionHeader(
    missedPet: MissedPet,
    upPress: () -> Unit
) {
    Box {
        NetworkImage(
            url = missedPet.thumbUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White, // always white as image has dark scrim
            modifier = Modifier
        ) {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.label_back)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MissedPetDescriptionBody(missedPet: MissedPet) {

    Text(
        text = missedPet.breed,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 36.dp,
                end = 8.dp,
                bottom = 8.dp
            )
    )
    Icon(
        tint = if (missedPet.sex == 'f') compositionLocalOf { Color.Red }.current.copy(
            alpha = 0.7f
        )
        else compositionLocalOf { Color.Blue }.current.copy(alpha = 0.7f),
        imageVector = if (missedPet.sex == 'f') Icons.Default.Female else Icons.Default.Male,
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Missed date: ${missedPet.regDate}",
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Text(
        text = "Missed location: ${missedPet.missedLocation}",
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Divider(modifier = Modifier.padding(16.dp))
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = stringResource(id = R.string.missedpet_desc),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}