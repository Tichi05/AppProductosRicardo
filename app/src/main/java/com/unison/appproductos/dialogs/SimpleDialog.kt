package com.unison.appproductos.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.unison.appproductos.R
import com.unison.appproductos.models.Producto
import com.unison.appproductos.ui.theme.onPrimaryDark


@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "¿Desea borrar permanentemente?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "No")
            }
        }
    )
}

@Composable
fun CreateConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Producto creado con exito!")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "")
            }
        }
    )
}

@Composable
fun ExitoDialog(
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    color = onPrimaryDark
                )
            }
        }
    }

    // Automáticamente cerrar el diálogo después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        onDismiss()
    }
}

@Composable
fun ExitoCreacionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "¡Éxito!")
        },
        text = {
            Text("Producto creado con éxito.")
        },
        confirmButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Ok")
            }
        }
    )
}



//
//
//@Composable
//fun SimpleDialog(
//    onDismissRequest: () -> Unit,
//    onConfirm: () -> Unit,
//    dialogTitle: String,
//    dialogText: String,
//) {
//    Dialog(onDismissRequest = { onDismissRequest() }) {
//        // Container
//        Card(
//            modifier = Modifier.padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = MaterialTheme.colorScheme.background,
//                contentColor = MaterialTheme.colorScheme.secondary
//        )
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                // Title
//                Text(
//                    text = dialogTitle,
//                    fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(top = 30.dp)
//                )
//
//                // Message
//                Text(
//                    text = dialogText,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(top = 10.dp)
//                )
//
//                // Buttons
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 30.dp, bottom = 10.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    // Dismiss Button
//                    TextButton(
//                        onClick = { onDismissRequest() },
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.Dismiss),
//                            //text = stringResource(id = "Dismiss"),
//                            color = MaterialTheme.colorScheme.secondary
//                        )
//
//                    }
//
//                    // Confirm Button
//                    TextButton(
//                        onClick = { onConfirm() }, // Error?
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    ){
//                        Text(
//                            text = stringResource(id = R.string.Confirm),
//                            //text = stringResource(id = "Confirm"),
//                            color = MaterialTheme.colorScheme.secondary
//                        )
//                    }
//                }
//
//
//            }
//        }
//    }
//
//}


