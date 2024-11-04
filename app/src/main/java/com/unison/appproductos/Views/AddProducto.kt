package com.unison.appproductos.views

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.ui.theme.backgroundDarkHighContrast
import com.unison.appproductos.ui.theme.onErrorDark
import com.unison.appproductos.ui.theme.onPrimaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDarkHighContrast
import com.unison.appproductos.viewmodels.ProductosViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductos(
    viewModel: ProductosViewModel,
    onAgregarClick: () -> Unit,
    navController: NavHostController
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var fechaRegistro by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // Variable para el diálogo de éxito

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaRegistro = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun validarCampos(): Boolean {
        return when {
            nombre.isEmpty() -> {
                errorMessage = "El nombre es obligatorio."
                false
            }
            descripcion.isEmpty() -> {
                errorMessage = "La descripción es obligatoria."
                false
            }
            precio.isEmpty() || precio.toDoubleOrNull() == null -> {
                errorMessage = "El precio es obligatorio y debe ser un número válido."
                false
            }
            fechaRegistro.isEmpty() -> {
                errorMessage = "La fecha de registro es obligatoria."
                false
            }
            else -> {
                errorMessage = ""
                true
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Formulario de Productos",
                        color = onPrimaryContainerDark
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("listado") },
                        modifier = Modifier.background(
                            color = backgroundDarkHighContrast,
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Flecha hacia atrás",
                            tint = onPrimaryContainerDark
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundDarkHighContrast
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    if (validarCampos()) {
                        viewModel.agregarProducto(nombre, descripcion, precio.toDouble(), fechaRegistro)
                        showDialog = true // Muestra el diálogo de éxito
                    }
                },
                containerColor = primaryContainerDark
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Producto", tint = primaryContainerDarkHighContrast)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundDarkHighContrast)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = primaryContainerDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = backgroundDarkHighContrast
                    ),
                    isError = nombre.isEmpty()
                )
                if (nombre.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = primaryContainerDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = descripcion.isEmpty()
                )
                if (descripcion.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = primaryContainerDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = precio.isEmpty() || precio.toDoubleOrNull() == null
                )
                when {
                    precio.isEmpty() -> Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                    precio.toDoubleOrNull() == null -> Text(text = "Debe ser un número válido", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = fechaRegistro,
                    onValueChange = { fechaRegistro = it },
                    label = { Text("Fecha de Registro", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = primaryContainerDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = fechaRegistro.isEmpty()
                )
                if (fechaRegistro.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = onErrorDark)
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                            navController.navigate("listado") // Navegar a la lista de productos
                        }) {
                            Text("Ok")
                        }
                    },
                    title = { Text("Producto creado con éxito") },
                    text = { Text("El producto se ha agregado correctamente.", fontSize = 18.sp) }
                )
            }
        }
    }
}
