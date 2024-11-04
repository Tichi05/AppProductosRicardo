package com.unison.appproductos.views

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.dialogs.ExitoDialog
import com.unison.appproductos.models.Producto
import com.unison.appproductos.ui.theme.backgroundDarkHighContrast
import com.unison.appproductos.ui.theme.onErrorDark
import com.unison.appproductos.ui.theme.onPrimaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDarkHighContrast
import com.unison.appproductos.ui.theme.secondaryDark
import com.unison.appproductos.viewmodels.ProductosViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEditarProducto(
    producto: Producto,
    viewModel: ProductosViewModel,
    onEditClick: () -> Unit,
    navController: NavHostController
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var descripcion by remember { mutableStateOf(producto.descripcion) }
    var precio by remember { mutableStateOf(producto.precio.toString()) }
    var fechaRegistro by remember { mutableStateOf(producto.fechaRegistro) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

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

    if (showSuccessDialog) {
        ExitoDialog(
            message = "Producto editado con éxito",
            onDismiss = {
                showSuccessDialog = false
                navController.navigate("listado") // Volver al listado después de la edición
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Editar Producto",
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
                // Campo para el nombre
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = secondaryDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = nombre.isEmpty()
                )
                if (nombre.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para la descripción
                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = secondaryDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = descripcion.isEmpty()
                )
                if (descripcion.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para el precio
                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = secondaryDark,
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

                // Campo para la fecha de registro
                TextField(
                    value = fechaRegistro,
                    onValueChange = { fechaRegistro = it },
                    label = { Text("Fecha de Registro", color = primaryContainerDark, fontSize = 18.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = secondaryDark,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    isError = fechaRegistro.isEmpty()
                )
                if (fechaRegistro.isEmpty()) {
                    Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de error
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = onErrorDark)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar cambios
                Button(
                    onClick = {
                        if (validarCampos()) {
                            viewModel.actualizarProducto(
                                producto.id,
                                nombre,
                                descripcion,
                                precio.toDouble(),
                                fechaRegistro
                            )
                            showSuccessDialog = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryContainerDark)
                ) {
                    Text("Guardar Cambios", color = primaryContainerDarkHighContrast)
                }
            }
        }
    }
}
