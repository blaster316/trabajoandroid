package com.example.sueldostrabajadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sueldostrabajadores.ui.theme.SueldosTrabajadoresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SueldosTrabajadoresTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        SalaryCalculatorScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SalaryCalculatorScreen(modifier: Modifier = Modifier) {
    val nameState = remember { mutableStateOf("") }
    val salaryState = remember { mutableStateOf("") }
    val selectedTypeState = remember { mutableStateOf("Regular") }
    val resultState = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calculadora de Sueldo", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Nombre del trabajador") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = salaryState.value,
            onValueChange = { salaryState.value = it },
            label = { Text("Sueldo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { selectedTypeState.value = "Regular" },
                modifier = Modifier.weight(1f)
            ) {
                Text("Regular")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { selectedTypeState.value = "Honorarios" },
                modifier = Modifier.weight(1f)
            ) {
                Text("Honorarios")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val salaryValue = salaryState.value.toDoubleOrNull()
                if (salaryValue != null) {
                    resultState.value = calculateNetSalary(salaryValue, selectedTypeState.value)
                } else {
                    resultState.value = "Ingrese un valor de sueldo válido."
                }
            }
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        resultState.value?.let {
            Text(text = it, fontSize = 18.sp)
        }
    }
}

fun calculateNetSalary(salary: Double, type: String): String {
    val percentage = when (type) {
        "Honorarios" -> 0.13
        "Regular" -> 0.20
        else -> 0.0
    }
    val netSalary = salary - (salary * percentage)
    return "Sueldo Neto para $type: $${"%.2f".format(netSalary)}"
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SueldosTrabajadoresTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SalaryCalculatorScreen()
        }
    }
}