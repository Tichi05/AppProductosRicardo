package com.unison.appproductos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unison.appproductos.models.Producto
import com.unison.appproductos.room.ProductDatabaseDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val productDatabaseDao: ProductDatabaseDao // DAO inyectado para acceder a la base de datos
) : ViewModel() {

    // Usamos StateFlow para manejar la lista de productos
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        // Al iniciar el ViewModel, cargamos los productos de la base de datos
        recuperarProductos()
    }

    // Función para recuperar los productos desde la base de datos al iniciar la aplicación
    private fun recuperarProductos() {
        viewModelScope.launch {
            productDatabaseDao.getAllProducts().collect { productosList ->
                _productos.value = productosList
            }
        }
    }

    // Función para refrescar los productos manualmente
    fun refrescarProductos() {
        recuperarProductos() // Vuelve a cargar los productos desde la base de datos
    }

    fun obtenerProductoPorId(id: String?): Producto? {
        return _productos.value.find { it.id == id }
    }

    // Función para agregar un nuevo producto
    fun agregarProducto(nombre: String, descripcion: String, precio: Double, fechaRegistro: String): Boolean {
        if (nombre.isEmpty() || descripcion.isEmpty() || precio <= 0 || fechaRegistro.isEmpty()) {
            return false
        }
        val nuevoProducto = Producto(
            id = (_productos.value.size + 1).toString(), // Generar un ID único para el nuevo producto
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            fechaRegistro = fechaRegistro
        )
        viewModelScope.launch {
            // Insertar el nuevo producto en la base de datos
            productDatabaseDao.insertProduct(nuevoProducto)

            // Actualizar el flujo de productos local con el nuevo producto
            val nuevaLista = _productos.value.toMutableList().apply {
                add(nuevoProducto)
            }
            _productos.value = nuevaLista
        }
        return true
    }



    // Función para eliminar un producto
    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            // Eliminar el producto de la base de datos
            productDatabaseDao.deleteProduct(producto)

            // Actualizar el flujo de productos local eliminando el producto
            val nuevaLista = _productos.value.toMutableList().apply {
                remove(producto)
            }
            _productos.value = nuevaLista
        }
    }



    // Función para actualizar un producto
    fun actualizarProducto(id: String, nombre: String, descripcion: String, precio: Double, fechaRegistro: String) {
        viewModelScope.launch {
            val productoActualizado = Producto(id, nombre, descripcion, fechaRegistro, precio)
            productDatabaseDao.updateProduct(productoActualizado)

            // Actualizar el flujo de productos local con el producto actualizado
            val nuevaLista = _productos.value.toMutableList().apply {
                val index = indexOfFirst { it.id == id }
                if (index != -1) {
                    set(index, productoActualizado)
                }
            }
            _productos.value = nuevaLista
        }
    }
}
