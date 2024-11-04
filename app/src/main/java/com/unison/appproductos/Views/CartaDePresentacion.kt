package com.unison.appproductos.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.unison.appproductos.R
import com.unison.appproductos.ui.theme.backgroundDarkHighContrast
import com.unison.appproductos.ui.theme.inversePrimaryDarkHighContrast
import com.unison.appproductos.ui.theme.onPrimaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDark
import com.unison.appproductos.ui.theme.primaryContainerDarkHighContrast
import com.unison.appproductos.ui.theme.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartaPresentacion(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Carta de Presentación",
                        color = onPrimaryContainerDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar", tint = onPrimaryContainerDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundDarkHighContrast
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundDarkHighContrast)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                // Imagen de presentación con borde redondeado y sombra
                Image(
                    painter = painterResource(id = R.drawable.fotoestudiante),
                    contentDescription = "Foto del estudiante",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(inversePrimaryDarkHighContrast)
                        .padding(4.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre y ocupación
                Text(
                    text = "Ricardo Camargo Chacón",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = primaryDark
                )
                Text(
                    text = "Desarrollador Web",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = primaryDark
                )

                Spacer(modifier = Modifier.height(170.dp))

                // Caja con información de contacto con borde y sombra
                Box(
                    modifier = Modifier
                        .background(primaryContainerDark, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Información de contacto
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.telefono),
                                contentDescription = "Teléfono",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "6621038633",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 12.dp),
                                color = primaryContainerDarkHighContrast
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.github),
                                contentDescription = "GitHub",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "@Tichi05",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 12.dp),
                                color = primaryContainerDarkHighContrast
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.correo),
                                contentDescription = "Correo",
                                modifier = Modifier.size(24.dp),

                            )
                            Text(
                                text = "a220214981@unison.mx",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 12.dp),
                                color = primaryContainerDarkHighContrast
                            )
                        }
                    }
                }
            }
        }
    }
}
