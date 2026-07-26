package com.example.calculatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.MathContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentInput: String = "0"
    private var storedValue: BigDecimal? = null
    private var pendingOperator: String? = null
    private var shouldResetInput: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumberButtons()
        setupOperatorButtons()

        binding.btnClear.setOnClickListener { onClear() }
        binding.btnBackspace.setOnClickListener { onBackspace() }
        binding.btnEquals.setOnClickListener { onEquals() }
        binding.btnPercent.setOnClickListener { onPercent() }
        binding.btnPlusMinus.setOnClickListener { onToggleSign() }
        binding.btnDot.setOnClickListener { onDot() }

        updateDisplay()
    }

    private fun setupNumberButtons() {
        val map = mapOf(
            binding.btn0 to "0", binding.btn1 to "1", binding.btn2 to "2",
            binding.btn3 to "3", binding.btn4 to "4", binding.btn5 to "5",
            binding.btn6 to "6", binding.btn7 to "7", binding.btn8 to "8",
            binding.btn9 to "9"
        )
        for ((button, digit) in map) {
            button.setOnClickListener { onDigit(digit) }
        }
    }

    private fun setupOperatorButtons() {
        binding.btnPlus.setOnClickListener { onOperator("+") }
        binding.btnMinus.setOnClickListener { onOperator("−") }
        binding.btnMultiply.setOnClickListener { onOperator("×") }
        binding.btnDivide.setOnClickListener { onOperator("÷") }
    }

    private fun onDigit(digit: String) {
        if (shouldResetInput) {
            currentInput = "0"
            shouldResetInput = false
        }
        currentInput = if (currentInput == "0") digit else currentInput + digit
        updateDisplay()
    }

    private fun onDot() {
        if (shouldResetInput) {
            currentInput = "0"
            shouldResetInput = false
        }
        if (!currentInput.contains(".")) {
            currentInput += "."
            updateDisplay()
        }
    }

    private fun onToggleSign() {
        currentInput = if (currentInput.startsWith("-")) {
            currentInput.substring(1)
        } else {
            if (currentInput == "0") "0" else "-$currentInput"
        }
        updateDisplay()
    }

    private fun onPercent() {
        val value = currentInput.toBigDecimalOrNull() ?: return
        currentInput = value.divide(BigDecimal(100), MathContext.DECIMAL64).stripTrailingZeros().toPlainString()
        updateDisplay()
    }

    private fun onBackspace() {
        currentInput = if (currentInput.length > 1) {
            currentInput.dropLast(1)
        } else {
            "0"
        }
        updateDisplay()
    }

    private fun onClear() {
        currentInput = "0"
        storedValue = null
        pendingOperator = null
        shouldResetInput = false
        binding.tvExpression.text = ""
        updateDisplay()
    }

    private fun onOperator(operator: String) {
        val currentValue = currentInput.toBigDecimalOrNull() ?: return

        if (storedValue != null && pendingOperator != null && !shouldResetInput) {
            storedValue = compute(storedValue!!, currentValue, pendingOperator!!)
            currentInput = storedValue!!.stripTrailingZeros().toPlainString()
        } else {
            storedValue = currentValue
        }

        pendingOperator = operator
        shouldResetInput = true
        binding.tvExpression.text = "${formatValue(storedValue!!)} $operator"
        updateDisplay()
    }

    private fun onEquals() {
        val currentValue = currentInput.toBigDecimalOrNull() ?: return
        val operator = pendingOperator
        val stored = storedValue

        if (operator != null && stored != null) {
            binding.tvExpression.text = "${formatValue(stored)} $operator ${formatValue(currentValue)} ="
            val result = compute(stored, currentValue, operator)
            currentInput = result.stripTrailingZeros().toPlainString()
            storedValue = null
            pendingOperator = null
            shouldResetInput = true
            updateDisplay()
        }
    }

    private fun compute(a: BigDecimal, b: BigDecimal, operator: String): BigDecimal {
        return when (operator) {
            "+" -> a.add(b)
            "−" -> a.subtract(b)
            "×" -> a.multiply(b)
            "÷" -> if (b.compareTo(BigDecimal.ZERO) == 0) BigDecimal.ZERO else a.divide(b, MathContext.DECIMAL64)
            else -> b
        }
    }

    private fun formatValue(value: BigDecimal): String {
        return value.stripTrailingZeros().toPlainString()
    }

    private fun String.toBigDecimalOrNull(): BigDecimal? {
        return try {
            BigDecimal(this)
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun updateDisplay() {
        binding.tvResult.text = currentInput
    }
}
