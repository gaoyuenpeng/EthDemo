package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.compose.emptyContent
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.graphics.RectangleShape
import androidx.ui.graphics.Shape
import androidx.ui.layout.*
import androidx.ui.layout.height
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.Dp
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.R

@Composable
fun CommonContent(
    title: String? = null,
    backgroundColor: Color = Color.White,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    hasTitle: Boolean = true,
    content: @Composable() () -> Unit
) {
    MaterialTheme(colors = MyThemeColor) {
        Surface(color = backgroundColor, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (hasTitle) {
                    MyTitleBar(
                        title = title ?: "",
                        onBackClick = onBackClick
                    )
                }
                Box(modifier = Modifier.fillMaxSize().plus(modifier), children = content)
            }
        }
    }
}

@Composable
fun ValueItemWithCorner(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    ValueItem(
        title = title,
        value = value,
        corner = true,
        modifier = modifier
    )
}

@Composable
fun ValueItem(
    title: String,
    value: String,
    corner: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (corner) {
        Card(modifier = Modifier.padding(horizontal = 10.dp).plus(modifier)) {
            ValueItem(title, value, modifier)
        }
    } else {
        ValueItem(title, value, modifier)
    }
}

@Composable
fun ValueItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    BoxWhite(
        modifier = Modifier.fillMaxWidth().plus(modifier)
    ) {
        Text(
            text = title,
            color = TextBlack,
            fontSize = TextUnit.Sp(18),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Text(
            text = value,
            color = Color.Gray,
            fontSize = TextUnit.Sp(16),
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun BoxWhite(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    border: Border? = null,
    padding: Dp = border?.size ?: 0.dp,
    paddingStart: Dp = Dp.Unspecified,
    paddingTop: Dp = Dp.Unspecified,
    paddingEnd: Dp = Dp.Unspecified,
    paddingBottom: Dp = Dp.Unspecified,
    gravity: ContentGravity = ContentGravity.TopStart,
    children: @Composable() () -> Unit = emptyContent()
) {
    Box(
        modifier = modifier,
        shape = shape,
        backgroundColor = Color.White,
        border = border,
        padding = padding,
        paddingStart = paddingStart,
        paddingTop = paddingTop,
        paddingEnd = paddingEnd,
        paddingBottom = paddingBottom,
        gravity = gravity,
        children = children
    )
}

@Composable
fun BoxGray(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    border: Border? = null,
    padding: Dp = border?.size ?: 0.dp,
    paddingStart: Dp = Dp.Unspecified,
    paddingTop: Dp = Dp.Unspecified,
    paddingEnd: Dp = Dp.Unspecified,
    paddingBottom: Dp = Dp.Unspecified,
    gravity: ContentGravity = ContentGravity.TopStart,
    children: @Composable() () -> Unit = emptyContent()
) {
    Box(
        modifier = modifier,
        shape = shape,
        backgroundColor = Color(0xFFededed),
        border = border,
        padding = padding,
        paddingStart = paddingStart,
        paddingTop = paddingTop,
        paddingEnd = paddingEnd,
        paddingBottom = paddingBottom,
        gravity = gravity,
        children = children
    )
}

@Composable
fun MyTitleBar(title: String, onBackClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFF1296db)
    ) {
        Row(
            modifier = Modifier.height(50.dp),
            verticalGravity = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageResource(id = R.mipmap.icon_back), tint = Color.White)
            }
            Text(text = title, color = Color.White, fontSize = TextUnit.Sp(18))
        }
    }
}

@Composable
fun Mnemonics(mnemonics: List<String>) {

    val size = 4
    val lines = mnemonics.size / size

    for (i in 0 until lines) {
        Row(
            modifier = Modifier.height(45.dp),
            verticalGravity = Alignment.CenterVertically
        ) {
            for (j in 0 until size) {
                val text = mnemonics[i * size + j]
                Text(
                    text = text,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    color = TextBlack
                )
            }

        }
    }
}
