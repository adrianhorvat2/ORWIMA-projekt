package hr.ferit.adrianhorvat.autosalon.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CarViewModel: ViewModel() {
    private val db = Firebase.firestore

    val carData = mutableStateListOf<Car>()

    init {
        fetchDatabaseData()
    }

    fun fetchDatabaseData(){
        db.collection("cars")
            .get()
            .addOnSuccessListener {result ->
                for(data in result.documents){
                    val car = data.toObject(Car::class.java)
                    if (car != null){
                        car.id = data.id
                        carData.add(car)
                    }
                }
            }
            .addOnFailureListener{
            }
    }
}