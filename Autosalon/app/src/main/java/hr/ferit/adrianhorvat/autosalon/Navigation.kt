package hr.ferit.adrianhorvat.autosalon

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.ferit.adrianhorvat.autosalon.data.CarViewModel
import hr.ferit.adrianhorvat.autosalon.ui.CarDetailScreen
import hr.ferit.adrianhorvat.autosalon.ui.FavoritesScreen
import hr.ferit.adrianhorvat.autosalon.ui.MainScreen

object Routes {
    const val SCREEN_FAVORITES = "favorites"
    const val SCREEN_ALL_CARS = "carList"
    const val SCREEN_CARS_DETAILS = "carDetails/{carId}"
    fun getCarDetailsPath(carId: String?): String {
        if (carId != null) {
            //Log.d("aa","$carId")
            return "carDetails/$carId"
        }
        return "carDetails/0"
    }
}

@Composable
fun NavigationController(
    viewModel: CarViewModel,
) {
    val navController = rememberNavController()
    val favorites = remember { mutableStateOf(viewModel.carData.filter { car -> car.isFavorite }) }
    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_ALL_CARS,

    ) {
        composable(Routes.SCREEN_FAVORITES) {
            FavoritesScreen(navigation = navController, favorites = favorites.value)
        }

        composable(Routes.SCREEN_ALL_CARS) {
            MainScreen(navigation = navController,viewModel)
        }
        composable(
            Routes.SCREEN_CARS_DETAILS,
            arguments = listOf(
                navArgument("carId") {
                    type = NavType.StringType
                }
            )
        )

        { backStackEntry ->
            backStackEntry.arguments?.getString("carId")?.let {
                carId->
                CarDetailScreen(
                    navigation = navController,
                    car = viewModel.carData.first{it.id==carId},
                    onToggleFavorite = { car ->
                        car.isFavorite = !car.isFavorite
                        favorites.value = viewModel.carData.filter { it.isFavorite }
                    }
                )
            }
        }
    }
}