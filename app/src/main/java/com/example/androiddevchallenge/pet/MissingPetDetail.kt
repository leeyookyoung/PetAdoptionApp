/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.pet

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.MissingPet
import com.example.androiddevchallenge.PetRepo
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.utils.NetworkImage
import com.example.androiddevchallenge.utils.backHandler
import com.example.androiddevchallenge.utils.scrim
import kotlinx.coroutines.launch

private val FabSize = 56.dp

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
    missingPet: MissingPet,
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
            MissedPetDescription(missingPet, upPress)
        }
    }
}

private enum class SheetState { Open, Closed }

@Composable
private fun MissedPetDescription(
    missingPet: MissingPet,
    upPress: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colors.secondary)
        ) {
            item { MissedPetDescriptionHeader(missingPet, upPress) }
            item { MissedPetDescriptionBody(missingPet) }
        }
    }
}

@Composable
private fun MissedPetDescriptionHeader(
    missingPet: MissingPet,
    upPress: () -> Unit
) {
    Box {
        NetworkImage(
            url = missingPet.thumbUrl,
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
private fun MissedPetDescriptionBody(missingPet: MissingPet) {
    Text(
        text = missingPet.breed,
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.body1,
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
        tint = if (missingPet.sex == 'f') compositionLocalOf { Color.Red }.current.copy(
            alpha = 0.7f
        )
        else compositionLocalOf { Color.Blue }.current.copy(alpha = 0.7f),
        imageVector = if (missingPet.sex == 'f') Icons.Default.Female else Icons.Default.Male,
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Missed date: ${missingPet.regDate}",
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Text(
        text = "Missed location: ${missingPet.missedLocation}",
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
