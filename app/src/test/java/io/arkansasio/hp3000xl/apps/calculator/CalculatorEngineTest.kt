package io.arkansasio.hp3000xl.apps.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class CalculatorEngineTest {
    private val calculator = CalculatorEngine()

    @Test fun respectsOperatorPrecedence() {
        assertEquals(14.0, calculator.calculate("2 + 3 * 4"), 0.0)
    }

    @Test fun supportsParenthesesAndUnarySigns() {
        assertEquals(-9.0, calculator.calculate("-(2 + 7)"), 0.0)
        assertEquals(5.0, calculator.calculate("10 / (1 + 1)"), 0.0)
        assertEquals(-3.5, calculator.calculate("-7 / 2"), 0.0)
    }

    @Test fun supportsDecimalNumbers() {
        assertEquals(3.75, calculator.calculate("1.25 + 2.5"), 0.000001)
    }

    @Test fun rejectsEmptyAndMalformedExpressions() {
        assertFails { calculator.calculate("  ") }
        assertFails { calculator.calculate("1 +") }
        assertFails { calculator.calculate("(2 + 3") }
        assertFails { calculator.calculate("1..2") }
        assertFails { calculator.calculate("2 ^ 3") }
    }

    @Test fun rejectsDivisionByZeroAndNonFiniteResults() {
        assertFails { calculator.calculate("1 / 0") }
        assertFails { calculator.calculate("1 / (3 - 3)") }
        assertFails { calculator.calculate("999999999999999999999999999999999999 * 999999999999999999999999999999999999") }
    }

    @Test fun rejectsExcessivelyLongInput() {
        assertFails { calculator.calculate("1".repeat(257)) }
    }

    private fun assertFails(block: () -> Unit) {
        try {
            block()
            fail("Expected expression to be rejected")
        } catch (_: IllegalArgumentException) {
            // Expected validation failure.
        }
    }
}
