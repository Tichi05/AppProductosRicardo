package com.unison.appproductos.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.R
import com.unison.appproductos.ui.theme.backgroundDarkHighContrast
import com.unison.appproductos.ui.theme.primaryContainerDark
import com.unison.appproductos.ui.theme.primaryDark

@Composable
fun Inicio(
    onProductosClick: () -> Unit,
    onPresentacionClick: () -> Unit,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDarkHighContrast)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo y Título
            Image(
                painter = painterResource(id = R.drawable.little_fella),
                contentDescription = "Just a cool little fella",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Little Fella INC.",
                style = MaterialTheme.typography.displayMedium.copy(
                    shadow = Shadow(color = Color.Black, blurRadius = 8f, offset = Offset(2f, 2f))
                ),
                color = primaryDark
            )

            // Espaciado extra entre logo y botones
            Spacer(modifier = Modifier.height(180.dp))

            // Botón Productos
            Button(
                onClick = onProductosClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Text(
                    text = "Productos",
                    fontSize = 22.sp, // Tamaño del texto más grande
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Presentación
            Button(
                onClick = onPresentacionClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(5.dp, shape = MaterialTheme.shapes.medium)
            ) {
                Text(
                    text = "Presentación",
                    fontSize = 22.sp, // Tamaño del texto más grande
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
