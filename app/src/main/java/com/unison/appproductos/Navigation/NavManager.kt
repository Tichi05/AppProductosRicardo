package com.unison.appproductos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unison.appproductos.viewmodels.ProductosViewModel
import com.unison.appproductos.views.*

@Composable
fun NavManager(
    navController: NavHostController,
    viewModel: ProductosViewModel
) {
    // Recoge el estado de los productos desde el ViewModel usando collectAsState()
    val productosState = viewModel.productos.collectAsState()

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            Inicio(
                onProductosClick = { navController.navigate("listado") },
                onPresentacionClick = { navController.navigate("presentacion") },
                navController = navController
            )
        }
        composable("listado") {
            ListadoProductos(
                productos = productosState.value,
                onAgregarProductoClick = { navController.navigate("formulario") },
                onEditClick = { producto ->
                    navController.navigate("formularioEditar/${producto.id}") // Asegúrate de usar el ID para identificar el producto
                },
                onDeleteClick = { viewModel.eliminarProducto(it) },
                onRefresh = { viewModel.refrescarProductos() },
                navController = navController
            )
        }

        composable("formularioEditar/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId")
            val producto = viewModel.obtenerProductoPorId(productoId) // Obtén el producto
            if (producto != null) {
                FormularioEditarProducto(
                    producto = producto,
                    viewModel = viewModel,
                    onEditClick = { navController.navigate("listado") },
                    navController = navController
                )
            }
        }


        composable("formulario") {
            FormularioProductos(
                viewModel = viewModel,
                onAgregarClick = {
                    navController.navigate("listado") // Una vez agregado, navega al listado
                },
                navController = navController
            )
        }
        composable("presentacion") {
            CartaPresentacion(navController)
        }
    }
}