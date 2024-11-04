package com.unison.appproductos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.unison.appproductos.navigation.NavManager
import com.unison.appproductos.room.ProductDatabase
import com.unison.appproductos.ui.theme.AppProductosTheme
import com.unison.appproductos.viewmodels.ProductosViewModel
import com.unison.appproductos.viewmodels.ProductosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java, "productos-database"
        ).build()

        val productDao = db.productDao()

        setContent {
            AppProductosTheme {
                val viewModel: ProductosViewModel = viewModel(
                    factory = ProductosViewModelFactory(productDao)
                )
                val navController = rememberNavController()

                Surface {
                    NavManager(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
