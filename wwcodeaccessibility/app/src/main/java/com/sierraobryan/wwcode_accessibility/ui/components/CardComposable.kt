package com.sierraobryan.wwcode_accessibility.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sierraobryan.wwcode_accessibility.data.models.Commit

@Composable
fun CardComposable(commit: Commit) {
    val backgroundShape: Shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp, backgroundShape)
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .background(Color.White, backgroundShape)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .semantics {  }
        ) {
            Text(text = commit.commitMessage, style = TextStyle(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Author: ${commit.author}")
            Text(text = "Date: ${commit.date}")
        }
    }
}
