package io.arkansasio.hp3000xl.apps.calculator

/**
 * Small, dependency-free arithmetic parser for the handheld calculator.
 *
 * Supported operators: +, -, *, /, parentheses, unary +/- and decimal numbers.
 * The parser intentionally does not evaluate arbitrary code or platform expressions.
 */
class CalculatorEngine {
    fun calculate(expression: String): Double {
        require(expression.length <= MAX_EXPRESSION_LENGTH) { "Expression is too long" }
        val normalized = expression.filterNot(Char::isWhitespace)
        require(normalized.isNotEmpty()) { "Expression is empty" }
        require(normalized.matches(Regex("[-+*/().0-9]+"))) { "Unsupported expression" }

        val result = Parser(normalized).parse()
        require(result.isFinite()) { "Result is outside the supported numeric range" }
        return result
    }

    private class Parser(private val source: String) {
        private var index = 0

        fun parse(): Double {
            val value = expression()
            require(index == source.length) { "Invalid expression at position $index" }
            return value
        }

        private fun expression(): Double {
            var value = term()
            while (peek('+') || peek('-')) {
                val operator = source[index++]
                val right = term()
                value = if (operator == '+') value + right else value - right
            }
            return value
        }

        private fun term(): Double {
            var value = factor()
            while (peek('*') || peek('/')) {
                val operator = source[index++]
                val right = factor()
                if (operator == '/') require(right != 0.0) { "Division by zero" }
                value = if (operator == '*') value * right else value / right
                require(value.isFinite()) { "Result is outside the supported numeric range" }
            }
            return value
        }

        private fun factor(): Double {
            if (peek('+') || peek('-')) {
                val operator = source[index++]
                val value = factor()
                return if (operator == '-') -value else value
            }

            if (peek('(')) {
                index++
                val value = expression()
                require(peek(')')) { "Missing closing parenthesis" }
                index++
                return value
            }

            val start = index
            var digits = 0
            var decimalPoints = 0
            while (index < source.length) {
                val character = source[index]
                when {
                    character in '0'..'9' -> {
                        digits++
                        index++
                    }
                    character == '.' -> {
                        decimalPoints++
                        require(decimalPoints <= 1) { "Invalid decimal number" }
                        index++
                    }
                    else -> break
                }
            }

            require(digits > 0) { "Expected a number at position $start" }
            return source.substring(start, index).toDouble()
        }

        private fun peek(character: Char): Boolean =
            index < source.length && source[index] == character
    }

    private companion object {
        const val MAX_EXPRESSION_LENGTH = 256
    }
}
