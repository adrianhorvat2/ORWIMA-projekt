package hr.ferit.adrianhorvat.autosalon.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.adrianhorvat.autosalon.R
import hr.ferit.adrianhorvat.autosalon.Routes
import hr.ferit.adrianhorvat.autosalon.data.Car
import hr.ferit.adrianhorvat.autosalon.ui.theme.LighterGray
import coil.compose.rememberAsyncImagePainter


//@Preview(showBackground=true)
@Composable
fun CarDetailScreen(
    navigation: NavController,
    car: Car,
    onToggleFavorite: (Car)->Unit
){
    val scrollState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            TopIcons(navigation, car, onToggleFavorite)
            CarTitleAndImage(car.brand, car.image)
            CarAttributes(car.horsePower, car.mileage, car.gearbox, car.engine)
            CarDescription(car.description, car.price)
        }
    }
}

@Composable
fun TopIcons(
    navigation:NavController,
    car:Car,
    onToggleFavorite:(Car)->Unit
){

    var isFavorite by remember { mutableStateOf(car.isFavorite) }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {

            CircularButton(
                R.drawable.ic_arrow_back,
                onClick = {
                    navigation.navigate(Routes.SCREEN_ALL_CARS)
                },
                color = Color.Black
            )

            val favoriteColor = if (isFavorite) Color.Red else Color.Black

            CircularButton(
                R.drawable.ic_favorite,
                color = favoriteColor,
                onClick = {
                    onToggleFavorite(car)
                    isFavorite = !isFavorite
                }
            )
        }
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int, color: Color = Gray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        elevation = elevation,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = LighterGray,
            contentColor = color
        ),
        shape = RoundedCornerShape(8.dp), modifier = Modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
        )
    }
}

@Composable
fun CarTitleAndImage(
    title: String,
    image: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text(
                text = "Car model:",
                style = TextStyle(color = Gray, fontSize = 16.sp, fontStyle = FontStyle.Italic),
            )
            Text(
                text = title,
                style = TextStyle(color = Color.Black, fontSize = 28.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                        )
                    }
        }
}

@Composable
fun CarAttributes(
    horsePower:Int,
    mileage:Int,
    gearbox:String,
    engine:String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LighterGray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.horsepower),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "$horsePower HP", color = Color.Black)
                }
            }
        }

        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LighterGray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.engine),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = engine, color = Color.Black)
                }
            }
        }


        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LighterGray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gearbox),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = gearbox, color = Color.Black)
                }
            }
        }


        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LighterGray),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.road),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "$mileage km", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun CarDescription(
    description: String,
    price: Int
){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Car Details:",
            style = TextStyle(color = Color.Black, fontSize = 16.sp, fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = description,
            style = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Start,
        )

        Text(
            text = "Price: $price EUR",
            style = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}
