package com.example.androiddevchallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.MainDestinations.PET_DETAIL_ID_KEY
import com.example.androiddevchallenge.pet.MissedPetDetails
import androidx.navigation.compose.navigate

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