package hr.ferit.adrianhorvat.autosalon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.adrianhorvat.autosalon.R
import hr.ferit.adrianhorvat.autosalon.Routes
import hr.ferit.adrianhorvat.autosalon.data.Car

//@Preview(showBackground = true)
@Composable
fun FavoritesScreen(
    navigation: NavController,
    favorites: List<Car>
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        BackButton(navigation)

        FavoritesTitle(title = "Favorites")

        if (favorites.isEmpty()) {
            Text(
                text = "No favorite cars yet.",
                style = TextStyle(color = Black, fontSize = 16.sp),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(favorites) { car ->
                    CarCard(car, navigation = navigation)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun FavoritesTitle(
    title: String
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = TextStyle(color = Black, fontSize = 28.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}

@Composable
fun BackButton(
    navigation:NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularButton(
            R.drawable.ic_arrow_back,
            onClick = {
                navigation.navigate(Routes.SCREEN_ALL_CARS)
            },
            color = Black
        )
    }
}
