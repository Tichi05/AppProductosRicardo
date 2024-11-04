package com.unison.appproductos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.dialogs.DeleteConfirmationDialog
import com.unison.appproductos.dialogs.ExitoDialog
import com.unison.appproductos.models.Producto
import com.unison.appproductos.ui.theme.backgroundDarkHighContrast
import com.unison.appproductos.ui.theme.inversePrimaryDarkMediumContrast
import com.unison.appproductos.ui.theme.onPrimaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDarkHighContrast
import com.unison.appproductos.ui.theme.primaryContainerDarkMediumContrast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoProductos(
    productos: List<Producto>,
    onAgregarProductoClick: () -> Unit,
    onEditClick: (Producto) -> Unit,
    onDeleteClick: (Producto) -> Unit,
    onRefresh: () -> Unit,
    navController: NavHostController
) {

    var productoParaEliminar by remember { mutableStateOf<Producto?>(null) }
    var mostrarDialogoBorrar by remember { mutableStateOf(false) }
    var mostrarDialogoExito by remember { mutableStateOf(false) }

    if (mostrarDialogoBorrar && productoParaEliminar != null) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDeleteClick(productoParaEliminar!!)
                mostrarDialogoBorrar = false
                mostrarDialogoExito = true
            },
            onDismiss = {
                mostrarDialogoBorrar = false
            }
        )
    }

    if (mostrarDialogoExito) {
        ExitoDialog(
            message = "Producto borrado con éxito",
            onDismiss = {
                mostrarDialogoExito = false
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Listado de Productos",
                        color = onPrimaryContainerDark
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("inicio") },
                        modifier = Modifier.background(
                            color = backgroundDarkHighContrast,
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
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
                onClick = onAgregarProductoClick,
                containerColor = primaryContainerDark
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Agregar Producto",
                    tint = primaryContainerDarkHighContrast
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(backgroundDarkHighContrast),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos disponibles",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = primaryContainerDarkHighContrast,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onEditClick = { onEditClick(producto) },
                            onDeleteClick = {
                                productoParaEliminar = producto
                                mostrarDialogoBorrar = true
                            },
                            backgroundColor = backgroundDarkHighContrast,
                            textColor = primaryContainerDarkHighContrast,
                            iconColor = primaryContainerDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Green,
    textColor: Color = Color.Black,
    iconColor: Color = Color.Red
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(primaryContainerDarkMediumContrast),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp, // Aumento del tamaño de fuente
                        color = inversePrimaryDarkMediumContrast
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Precio: $${String.format("%.2f", producto.precio)}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp), // Aumento del tamaño de fuente
                    color = inversePrimaryDarkMediumContrast
                )
            }

            Row {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.background(
                        color = iconColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = textColor
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.background(
                        color = iconColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = textColor
                    )
                }
            }
        }
    }
}


//            IconButton(
//                onClick = onDeleteClick,
//                modifier = Modifier.background(
//                    color = iconColor,
//                    shape = RoundedCornerShape(8.dp)
//                )
//            ) {
//                Icon(
//                    Icons.Filled.Delete,
//                    contentDescription = "Eliminar",
//                    tint = textColor
//                )
//            }



