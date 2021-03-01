package com.example.androiddevchallenge


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.theme.Green300
import com.example.androiddevchallenge.ui.theme.Green800
import com.example.androiddevchallenge.ui.theme.Orange300
import com.example.androiddevchallenge.ui.theme.Orange800
import com.example.androiddevchallenge.utils.NetworkImage
import kotlin.math.ceil

enum class TabPage {
    AnimalShelter, Missing
}

@Composable
fun Home(modifier: Modifier = Modifier, selectPet: (Long) -> Unit) {
    var tabPage by remember { mutableStateOf(TabPage.Missing) }
    val lazyListState = rememberLazyListState()
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.AnimalShelter) Green300 else Orange300)
    var expandedId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        backgroundColor = backgroundColor,
    ) {

        if (tabPage == TabPage.AnimalShelter) {
            LazyColumn(
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                state = lazyListState,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                items(shelterPets) { shelterAnimal ->
                    ShelterRow(
                        shelterPet = shelterAnimal,
                        expanded = expandedId == shelterAnimal.id,
                        onClick = {
                            expandedId =
                                if (expandedId == shelterAnimal.id) null else shelterAnimal.id
                        }
                    )
//                    HorizontalLayout(
//                        maxRowHeight = 150.dp,
//                        minRowHeight = 80.dp,
//                        modifier = Modifier.padding(4.dp)
//                    ) {
//                        ShelterAnimalComp(shelterAnimal,
//                            expanded = expandedThumbUrl == shelterAnimal.thumbUrl,
//                            onClick = {
//                                expandedThumbUrl = if (expandedThumbUrl == shelterAnimal.thumbUrl) null else shelterAnimal.thumbUrl
//                            }
//                        )
//                    }

                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        } else {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                StaggeredVerticalGrid(
                    maxColumnWidth = 220.dp,
                    modifier = Modifier.padding(4.dp)
                ) {
                    missingPets.forEach { missingAnimal ->
                        MissingAnimalComp(missingAnimal, selectPet)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, tabPage)
        }
    ) {
        HomeTab(
            type = 0,
            icon = Icons.Default.Roofing,
            title = stringResource(R.string.pet_shelter),
            onClick = { onTabSelected(TabPage.AnimalShelter) }
        )
        HomeTab(
            type = 1,
            icon = Icons.Default.PriorityHigh,
            title = stringResource(R.string.missing),
            onClick = { onTabSelected(TabPage.Missing) }
        )
    }
}

@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.AnimalShelter isTransitioningTo TabPage.Missing) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator Left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.AnimalShelter isTransitioningTo TabPage.Missing) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border color"
    ) { page ->
        if (page == TabPage.AnimalShelter) Green800 else Orange800
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

@Composable
private fun HomeTab(
    type: Int,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = if (type == 1) compositionLocalOf { Color.Red }.current.copy(alpha = 0.8f)
            else compositionLocalOf { Color.Black }.current.copy(alpha = 1.0f),
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.subtitle1)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShelterRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun ShelterPreview() {
    ShelterRow(shelterPets[0], false, onClick = {})
}

//@Preview
//@Composable
//fun MissedPetPreview() {
//    StaggeredVerticalGrid(
//        maxColumnWidth = 220.dp,
//        modifier = Modifier.padding(4.dp)
//    ) {
//        missingPets.forEach { missingAnimal ->
//            MissingAnimalComp(missingAnimal, )
//
//        }
//    }
//}


@Composable
fun ShelterAnimalComp(
    shelterPet: ShelterPet,
    expanded: Boolean, onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ShelterRowSpacer(visible = expanded)
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 2.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .semantics {
//                    contentDescription = animalString
                }
        ) {
            if (expanded) {
                val (image, breed, sex) = createRefs()
                NetworkImage(
                    url = shelterPet.thumbUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .aspectRatio(4f / 3f)
                        .padding(end = 4.dp)
                        .constrainAs(image) {
                            centerVerticallyTo(parent)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = shelterPet.breed,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(breed) {
                            top.linkTo(image.top)
                            start.linkTo(image.end)
                        }
                )
                Text(
                    text = shelterPet.sex.toString().toUpperCase(),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .constrainAs(sex) {
                            top.linkTo(breed.bottom)
                            start.linkTo(image.end)
                        }
                )
            } else {
                val (image, breed, sex) = createRefs()
                NetworkImage(
                    url = shelterPet.thumbUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .aspectRatio(4f / 3f)
                        .padding(end = 4.dp)
                        .constrainAs(image) {
                            centerVerticallyTo(parent)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = shelterPet.breed,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(breed) {
                            top.linkTo(image.top)
                            start.linkTo(image.end)
                        }
                )
                Text(
                    text = shelterPet.sex.toString().toUpperCase(),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .constrainAs(sex) {
                            top.linkTo(breed.bottom)
                            start.linkTo(image.end)
                        }
                )
            }
        }
    }
    ShelterRowSpacer(visible = expanded)
}

@Composable
private fun getPetImage(pet: Pet): Painter {
    val petImage: Painter = when (pet) {
        Pet.Dog -> painterResource(id = R.drawable.ic_dog)
        Pet.Cat -> painterResource(id = R.drawable.ic_cat)
        else -> painterResource(id = R.drawable.ic_rabbit)
    }

    return petImage
}

@Composable
private fun ShelterRow(shelterPet: ShelterPet, expanded: Boolean, onClick: () -> Unit) {
    val petImage = getPetImage(shelterPet.type)

    ShelterRowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {

            Row {
                NetworkImage(
                    url = shelterPet.thumbUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(140.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .aspectRatio(4f / 3f)
                        .padding(end = 4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${shelterPet.breed}",
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Row {
                        Icon(
                            tint = if (shelterPet.sex == 'f') compositionLocalOf { Color.Red }.current.copy(
                                alpha = 0.5f
                            )
                            else compositionLocalOf { Color.Blue }.current.copy(alpha = 0.5f),
                            imageVector = if (shelterPet.sex == 'f') Icons.Default.Female else Icons.Default.Male,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = petImage,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rescue Information",
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.body2,
                        color = Green800
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "date: ${shelterPet.regDate}",
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "current: ${shelterPet.location}",
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "rescued: ${shelterPet.rescuedLocation}",
                        textAlign = TextAlign.Justify
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = shelterPet.character,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        }
    }
    ShelterRowSpacer(visible = expanded)
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Composable
fun HorizontalLayout(
    modifier: Modifier = Modifier,
    maxRowHeight: Dp,
    minRowHeight: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
            .defaultMinSize(minHeight = minRowHeight)
    ) { measurables, constraints ->
//        check(constraints.hasBoundedHeight) {
//            "Unbounded height not supported"
//        }

        val itemConstraints = constraints.copy(maxHeight = maxRowHeight.toPx().toInt())
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }
        layout(
            width = constraints.maxWidth,
            height = maxRowHeight.toPx().toInt()
        ) {
            placeables.forEach { placeable ->
                placeable.place(
                    x = 0,
                    y = 0
                )
            }
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

@Composable
fun MissingAnimalComp(
    missedPet: MissedPet,
    selectPet: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = MaterialTheme.colors.surface,
        elevation = 0.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        val petImage = getPetImage(missedPet.type)
        val animalString = stringResource(id = R.string.pet_shelter)
        ConstraintLayout(
            modifier = Modifier
                .clickable(
                    onClick = {
                        selectPet(missedPet.id)
                    }
                )
                .semantics {
                    contentDescription = animalString
                }
        ) {
            val (image, breed, col) = createRefs()
            NetworkImage(
                url = missedPet.thumbUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )
            Text(
                text = missedPet.breed,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(breed) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                    }
            )
            Column(
                modifier = Modifier
                    .constrainAs(col) {
                        top.linkTo(breed.bottom)
                        start.linkTo(image.start)
                    }
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = petImage,
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${missedPet.regDate}",
                        textAlign = TextAlign.Justify
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
            }


//            Text(
//                text = "Rescue Information",
//                textAlign = TextAlign.Justify,
//                style = MaterialTheme.typography.body2,
//                color = Green800,
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//                    .constrainAs(rescueInfo) {
//                        top.linkTo(image.bottom)
//                    }
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "date: ${shelterPet.regDate}",
//                textAlign = TextAlign.Justify
//            )
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(
//                text = "current: ${shelterPet.location}",
//                textAlign = TextAlign.Justify
//            )
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(
//                text = "rescued: ${shelterPet.rescuedLocation}",
//                textAlign = TextAlign.Justify
//            )

        }
    }
}

