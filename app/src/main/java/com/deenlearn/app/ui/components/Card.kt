package com.deenlearn.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DeenCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: Dp = 2.dp,
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            border = border,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            border = border,
            content = content
        )
    }
}

@Composable
fun ElevatedDeenCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    DeenCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(),
        elevation = 4.dp,
        onClick = onClick,
        content = content
    )
}

@Composable
fun OutlinedDeenCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    DeenCard(
        modifier = modifier,
        colors = CardDefaults.outlinedCardColors(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = 0.dp,
        onClick = onClick,
        content = content
    )
}

@Composable
fun FeatureCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    ElevatedDeenCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                icon()
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
