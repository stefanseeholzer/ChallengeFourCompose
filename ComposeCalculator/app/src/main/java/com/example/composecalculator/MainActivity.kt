package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("0") }
    var valueTwo by remember { mutableStateOf("0") }
    var operatorValue by remember { mutableStateOf("C") }
    var operatorClicked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Text(
            text = inputValue,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = 48.sp

        )

        val buttons = listOf('7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '+', '0', 'C', '=', '-')
        for(row in buttons.chunked(4)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Create buttons for the current row
                for (buttonValue in row) {
                    Button(
                        onClick = {
                            if(buttonValue.isDigit()){
                                inputValue = buttonClicked(inputValue, buttonValue.toString(), operatorClicked)
                                operatorClicked = false
                            }else{
                                if(buttonValue == 'C'){
                                    inputValue = "0"
                                }else if(!operatorClicked) {
                                    inputValue = evaluate(
                                        inputValue,
                                        valueTwo,
                                        buttonValue.toString(),
                                        operatorValue
                                    )
                                    valueTwo = inputValue
                                    operatorValue = "$buttonValue"
                                    operatorClicked = true
                                }else{
                                    operatorValue = buttonValue.toString()
                                }
                            }
                        }
                    ) {
                        Text(text = "$buttonValue")
                    }
                }
            }
        }
    }
}

fun buttonClicked(inputValue: String, buttonValue: String, operatorClicked: Boolean): String{
    val updatedInput = if(inputValue == "0" || operatorClicked){
        buttonValue
    }else{
        inputValue + buttonValue
    }
    return updatedInput
}

fun evaluate(inputValue: String, valueTwo: String, buttonPressed: String, operator: String): String {
    var answer = 0.0
    val numberOne = inputValue.toDouble()
    val numberTwo = valueTwo.toDouble()

    answer = when(operator){
        "+" -> numberTwo + numberOne
        "-" -> numberTwo - numberOne
        "/" -> numberTwo / numberOne
        "*" -> numberTwo * numberOne
        else -> inputValue.toDouble()
    }

    if(answer == 0.0){
        return "0"
    }
    return answer.toString()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCalculatorTheme {
        Calculator()
    }
}