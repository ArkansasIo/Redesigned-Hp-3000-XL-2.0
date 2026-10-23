package io.arkansasio.hp3000xl.apps

class CalculatorApp {
    val descriptor = OsAppCatalog.find("calculator")!!
    fun add(a: Double, b: Double) = a + b
    fun subtract(a: Double, b: Double) = a - b
    fun multiply(a: Double, b: Double) = a * b
    fun divide(a: Double, b: Double): Double = if (b == 0.0) Double.NaN else a / b
    fun percent(value: Double) = value / 100.0
}
