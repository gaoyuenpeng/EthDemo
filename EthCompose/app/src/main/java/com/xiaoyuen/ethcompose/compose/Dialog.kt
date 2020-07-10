package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Divider
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp


@Composable
fun LoadingProgressBar(title: String = "加载中") {

    Stack(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(color = Color(0x50000000), modifier = Modifier.fillMaxSize())
        Card(
            modifier = Modifier.gravity(Alignment.Center).width(100.dp).height(100.dp),
            elevation = 0.dp
        ) {
            Column(
                horizontalGravity = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = title,
                    style = TextStyle(
                        color = TextBlack,
                        fontSize = TextUnit.Sp(16)
                    ),
                    modifier = Modifier.offset(y = 10.dp)
                )
            }
        }
    }

}

@Composable
fun AlertDialog(
    title: String? = null,
    text: String,
    cancelButton: String? = null,
    comfirmButton: String? = null,
    cancelButtonRequest: () -> Unit = {},
    comfirmButtonRequest: () -> Unit = {},
    onCloseRequest: () -> Unit
) {

    androidx.ui.material.AlertDialog(
        onCloseRequest = onCloseRequest,
        title = {
            if (title != null && title.isNotEmpty()) {
                Text(
                    text,
                    style = TextStyle(
                        color = TextBlack,
                        fontWeight = FontWeight.Black,
                        fontSize = TextUnit.Sp(24)
                    )
                )
            }
        },
        text = {
            Text(
                text,
                style = TextStyle(
                    color = TextBlack,
                    fontSize = TextUnit.Sp(20)
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        },
        buttons = {
            if ((cancelButton != null && cancelButton.isNotEmpty()) || (comfirmButton != null && comfirmButton.isNotEmpty())) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(10.dp).fillMaxWidth()
                ) {
                    if (cancelButton != null && cancelButton.isNotEmpty()) {
                        Button(
                            onClick = cancelButtonRequest,
                            backgroundColor = Color.White
                        ) {
                            Text(cancelButton, color = MyThemeColor.primary)
                        }
                    }
                    if (comfirmButton != null && comfirmButton.isNotEmpty()) {
                        Button(
                            onClick = comfirmButtonRequest,
                            backgroundColor = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(comfirmButton, color = MyThemeColor.primary)
                        }
                    }
                }
            }
        }
    )
}