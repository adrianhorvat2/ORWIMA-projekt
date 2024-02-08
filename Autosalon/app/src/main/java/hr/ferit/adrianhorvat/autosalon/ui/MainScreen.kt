package hr.ferit.adrianhorvat.autosalon.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.adrianhorvat.autosalon.R
import hr.ferit.adrianhorvat.autosalon.Routes
import hr.ferit.adrianhorvat.autosalon.data.Car
import hr.ferit.adrianhorvat.autosalon.data.CarViewModel
import hr.ferit.adrianhorvat.autosalon.ui.theme.LighterGray

//@Preview(showBackground = true)
@Composable
fun MainScreen(
navigation: NavController,
viewModel: CarViewModel
) {
    //var filteredCars by remember { mutableStateOf(viewModel.carData.toList()) }
    var filteredCars by remember { mutableStateOf<List<Car>>(viewModel.carData) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenTitle(title = "CarCenter", subtitle = "Dealership", imageResource = R.drawable.user, navigation = navigation)
        SearchBar(
            labelText = "Search our cars",
            viewModel = viewModel,
            onCarSearchResult = { filteredCars = it }
        )
        CarBrandList()
        CarContainer(filteredCars,navigation)
        //CarContainer(viewModel.carData,navigation)
    }
}

@Composable
fun ScreenTitle(
    title: String,
    subtitle: String,
    imageResource: Int,
    navigation: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = subtitle,
                style = TextStyle(color = Gray, fontSize = 14.sp)
            )
            Text(
                text = title,
                style = TextStyle(color = Black, fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }


        CircularButton(
            R.drawable.ic_favorite,
            color = Black,
            onClick = {
                navigation.navigate(Routes.SCREEN_FAVORITES)
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun CarBrand(
    imageResId: Int,
    brandName: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(25.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = brandName,
            style = TextStyle(color = Black, fontSize = 12.sp)
        )
    }
}


@Composable
fun CarBrandList() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CarBrand(R.drawable.nissanlogo, "Nissan")
        CarBrand(R.drawable.audilogo, "Audi")
        CarBrand(R.drawable.toyotalogo, "Toyota")
        CarBrand(R.drawable.vwlogo, "VW")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarCard(
    car:Car,
    navigation: NavController
) {
    val carBrand: String = car.brand
    val carPrice: Int = car.price

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LighterGray
        ),
        onClick = {
            navigation.navigate(
                route = Routes.getCarDetailsPath(car.id)
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = carBrand,
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$carPrice EUR",
                style = TextStyle(
                    color = Gray,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun CarContainer(
    filteredCars: List<Car>,
    navigation: NavController
) {
    var currentSortOrder by remember { mutableStateOf<SortOrder>(SortOrder.Lowest) }

    LazyColumn {
        item {
            SortComponent(
                sortOrder = currentSortOrder,
                onNext = { currentSortOrder = SortOrder.Highest },
                onPrevious = { currentSortOrder = SortOrder.Lowest }
            )
        }

        filteredCars.sortedBy {
            when (currentSortOrder) {
                SortOrder.Lowest -> it.price
                SortOrder.Highest -> -it.price
            }
        }.forEach { car ->
            item {
                CarCard(car, navigation)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

sealed class SortOrder(val displayName: String) {
    object Lowest : SortOrder("Price by lowest")
    object Highest : SortOrder("Price by highest")
}

@Composable
fun SortComponent(
    sortOrder: SortOrder,
    onNext: () -> Unit,
    onPrevious:() -> Unit
){
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clickable { onPrevious() }
                .background(
                    color = LighterGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ){
            Icon(
                imageVector= Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color= LighterGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = sortOrder.displayName,
                color = Black,
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 12.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clickable { onNext() }
                .background(
                    color = LighterGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ){
            Icon(
                imageVector= Icons.Rounded.ArrowForward,
                contentDescription = "Forward",
                tint = Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    labelText: String,
    viewModel: CarViewModel,
    onCarSearchResult: (List<Car>) -> Unit
) {
    val searchInput = remember { mutableStateOf("") }
    TextField(
        value = searchInput.value,
        onValueChange = { newValue ->
            searchInput.value = newValue
            val filteredCars = viewModel.carData.filter { car ->
                car.brand.contains(newValue, ignoreCase = true)

            }
            onCarSearchResult(filteredCars)
        },
        label = {
            Text(labelText)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = LighterGray,
            placeholderColor = Gray,
            textColor = Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}