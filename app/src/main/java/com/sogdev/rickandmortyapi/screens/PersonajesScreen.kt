package com.sogdev.rickandmortyapi.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sogdev.rickandmortyapi.model.Result

@Composable
fun PersonajesScreen( modifier: Modifier, viewModel: PersonajesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Log.d("Cantidad, Items", "${state.size}")
    LazyColumn(modifier = modifier) {
        items(state) { personaje ->
            PersonajeCard(personaje)
        }
        item {
            LaunchedEffect(Unit) {
                viewModel.LoadingCharacter()
            }
        }
    }
}

@Composable
fun PersonajeCard(
    personaje: Result,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row {
                Surface(
                    modifier.size(120.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                ) {
                    AsyncImage(
                        model = personaje.image,
                        contentDescription = personaje.name,
                        contentScale = ContentScale.FillBounds
                    )
                }
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                ) {
                    Text(
                        text = personaje.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(verticalAlignment =Alignment.CenterVertically ){
                        val color = when (personaje.status) {
                            "Alive" -> Color.Green
                            "Dead" -> Color.Red
                            else -> Color.Gray
                        }
                        Box(
                            modifier
                                .padding(7.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(12.dp)
                        )
                        Text(
                            text = "${personaje.status} - ${personaje.species} - ${personaje.gender}",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                IconButton(onClick = {
                    expanded = !expanded
                },
                    modifier.align(Alignment.CenterVertically)
                    ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess
                                else
                            Icons.Filled.ExpandMore,
                        contentDescription = "Ver más"
                    )
                }
            }
            if (expanded) {
                Row(modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = "Origen",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = personaje.origin.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier.padding(4.dp))
                        Text(
                            text = "Ultima ubicación",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = personaje.location.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

}

