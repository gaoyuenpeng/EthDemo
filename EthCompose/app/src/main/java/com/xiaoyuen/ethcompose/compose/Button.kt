package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.defaultMinSizeConstraints
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.Button
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp

@Composable
fun CommonButton(title: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().plus(modifier)
            .defaultMinSizeConstraints(minHeight = 50.dp)
    ) {
        Text(text = title, fontSize = TextUnit.Sp(18))
    }

}