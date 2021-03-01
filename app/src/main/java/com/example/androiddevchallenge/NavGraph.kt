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
package com.example.androiddevchallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.MainDestinations.PET_DETAIL_ID_KEY
import com.example.androiddevchallenge.pet.MissedPetDetails

object MainDestinations {
    const val PET_HOME_ROUTE = "pet_home"
    const val PET_DETAIL_ROUTE = "pet"
    const val PET_DETAIL_ID_KEY = "petId"
}

@Composable
fun NavGraph(startDestination: String = MainDestinations.PET_HOME_ROUTE) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.PET_HOME_ROUTE) {
            Home(selectPet = actions.selectMissingPet)
        }
        composable(
            "${MainDestinations.PET_DETAIL_ROUTE}/{$PET_DETAIL_ID_KEY}",
            arguments = listOf(navArgument(PET_DETAIL_ID_KEY) { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            MissedPetDetails(
                id = arguments.getLong(PET_DETAIL_ID_KEY),
                upPress = actions.upPress
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val selectMissingPet: (Long) -> Unit = { id: Long ->
        navController.navigate("${MainDestinations.PET_DETAIL_ROUTE}/$id")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
