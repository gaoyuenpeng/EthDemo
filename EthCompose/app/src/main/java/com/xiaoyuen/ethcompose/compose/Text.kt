package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.foundation.TextFieldValue
import androidx.ui.foundation.currentTextStyle
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxWidth
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.TextUnit

@Composable
fun TextBold18(text: String, color: Color = TextBlack, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyle(fontWeight = FontWeight.SemiBold),
        color = color,
        fontSize = TextUnit.Sp(18),
        modifier = modifier
    )
}

@Composable
fun TextFieldWithHint(
    value: TextFieldValue,
    modifier: Modifier = Modifier.None,
    hint: String = "",
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = currentTextStyle(),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified,
    textAlign: TextAlign? = null,
    onImeActionPerformed: (ImeAction) -> Unit = {}
) {
    Stack(modifier = modifier) {
        TextField(
            value = value,
            modifier = Modifier.fillMaxWidth().gravity(Alignment.Center),
            onValueChange = onValueChange,
            textStyle = textStyle.plus(TextStyle(textAlign = textAlign)),
            keyboardType = keyboardType,
            imeAction = imeAction,
            onImeActionPerformed = onImeActionPerformed,
            textColor = TextBlack,
            cursorColor = Color(0xFF1296db)
        )
        if (value.text.isEmpty()) Text(
            hint,
            modifier = Modifier.fillMaxWidth().gravity(Alignment.Center),
            style = TextStyle(color = Color(0xFFC7C7C7)).plus(TextStyle(textAlign = textAlign))
        )
    }
}