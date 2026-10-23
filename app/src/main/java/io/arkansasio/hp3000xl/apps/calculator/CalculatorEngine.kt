package io.arkansasio.hp3000xl.apps.calculator

class CalculatorEngine {
    fun calculate(expression: String): Double {
        val normalized = expression.replace(" ", "")
        require(normalized.matches(Regex("[-+*/().0-9]+"))) { "Unsupported expression" }
        return Parser(normalized).parse()
    }

    private class Parser(private val s: String) {
        private var i = 0
        fun parse(): Double { val value = expression(); require(i == s.length) { "Invalid expression" }; return value }
        private fun expression(): Double { var v = term(); while (i < s.length && (s[i] == '+' || s[i] == '-')) { val op=s[i++]; val r=term(); v=if(op=='+')v+r else v-r }; return v }
        private fun term(): Double { var v = factor(); while (i < s.length && (s[i] == '*' || s[i] == '/')) { val op=s[i++]; val r=factor(); v=if(op=='*')v*r else v/r }; return v }
        private fun factor(): Double { if (i<s.length && s[i]=='(') { i++; val v=expression(); require(i<s.length&&s[i++]==')'); return v }; val start=i; if(i<s.length&&(s[i]=='+'||s[i]=='-'))i++; while(i<s.length&&(s[i].isDigit()||s[i]=='.'))i++; return s.substring(start,i).toDouble() }
    }
}
