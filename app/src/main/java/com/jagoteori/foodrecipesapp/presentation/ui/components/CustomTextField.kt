package com.jagoteori.foodrecipesapp.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.presentation.ui.theme.*

@Composable
fun CustomTextField(
    title: String,
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean,
    errorMessage: String
) {
    Column {
        Text(
            text = title,
            color = GreyColor500,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp, top = 15.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(size = 12.dp),
            textStyle = TextStyle(color = BlackColor500),
            isError = isError,
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = BlackColor500,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = BlackColorBody,
                unfocusedIndicatorColor = GreyColorTextInput,
            ),
            placeholder = {
                Text(
                    text = "Masukkan ${title.lowercase()} kamu", style = TextStyle(
                        color = GreyColor300,
                    )
                )
            }
        )
        Log.d("isError", "oi is error ${title}: $isError")
        if (isError) {
            Text(
                text = errorMessage, color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

var value by mutableStateOf(TextFieldValue(""))

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    MaterialTheme {
        CustomTextField("Test", modifier = Modifier, value = value, isError = true, errorMessage = "beli ko rong basso", onValueChange = {
            value = it
        })
    }
}