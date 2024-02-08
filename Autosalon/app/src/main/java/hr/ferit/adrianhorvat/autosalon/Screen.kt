package hr.ferit.adrianhorvat.autosalon

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object BrandDetailScreen : Screen("brand_detail_screen")
}
