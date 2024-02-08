package hr.ferit.adrianhorvat.autosalon.data

import androidx.annotation.DrawableRes

data class Car(
    var id: String="",
    val brand: String="",
    val price: Int=0,
    val image: String="",
    val horsePower: Int=0,
    val engine: String="",
    val gearbox: String="",
    val mileage: Int=0,
    var isFavorite: Boolean=false,
    val description: String="",
){}
