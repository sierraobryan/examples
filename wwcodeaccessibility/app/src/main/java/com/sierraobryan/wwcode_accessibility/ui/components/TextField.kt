package com.sierraobryan.wwcode_accessibility.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: @Composable () -> Unit,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .clip(shape = shapes.medium),
            shape = shapes.medium,
            textStyle = MaterialTheme.typography.body1,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            label = placeholder,
            singleLine = singleLine,
            keyboardActions = keyboardActions,
        )
    }
}