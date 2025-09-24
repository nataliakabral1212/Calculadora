package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private var input: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.Pantalla)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnPto,
            R.id.btnMas, R.id.btnMenos, R.id.btnPor, R.id.btnDividir
        )


        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                input += (it as Button).text
                tvDisplay.text = input
            }
        }


        findViewById<Button>(R.id.btnClear).setOnClickListener {
            if (input.isNotEmpty()) {
                input = input.dropLast(1)
                tvDisplay.text = if (input.isEmpty()) "0" else input
            } else {
                tvDisplay.text = "Syntax ERROR"
            }
        }


        findViewById<Button>(R.id.btnClearAll).setOnClickListener {
            input = ""
            tvDisplay.text = "0"
        }


        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            try {
                val expression = ExpressionBuilder(input).build()
                val result = expression.evaluate()
                val resultadoNormalizado = normalizarResultado(result)

                if (resultadoNormalizado % 1.0 == 0.0) {
                    tvDisplay.text = resultadoNormalizado.toInt().toString()
                    input = resultadoNormalizado.toInt().toString()
                } else {
                    tvDisplay.text = resultadoNormalizado.toString()
                    input = resultadoNormalizado.toString()
                }

            } catch (e: Exception) {
                tvDisplay.text = "Syntax ERROR"
                input = ""
            }
        }
    }
    private fun normalizarResultado(valor: Double): Double {
        return if (abs(valor) < 1e-10) 0.0 else valor
    }
}