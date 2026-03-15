public class CalculatorEngineTest {

    public static void main(String[] args) {
        shouldAddValues();
        shouldRespectChainedOperations();
        shouldToggleSignAndBackspace();
        shouldHandleDecimals();
        shouldShowErrorOnDivisionByZero();
        shouldClearEntryWithoutLosingOperation();
    }

    private static void shouldAddValues() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("1");
        engine.inputDigit("2");
        engine.applyOperator(CalculatorEngine.Operator.ADD);
        engine.inputDigit("3");
        engine.evaluate();
        assertEquals("15", engine.getDisplayText(), "simple addition");
    }

    private static void shouldRespectChainedOperations() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("9");
        engine.applyOperator(CalculatorEngine.Operator.MULTIPLY);
        engine.inputDigit("5");
        engine.applyOperator(CalculatorEngine.Operator.SUBTRACT);
        engine.inputDigit("7");
        engine.evaluate();
        assertEquals("38", engine.getDisplayText(), "chained operations");
    }

    private static void shouldToggleSignAndBackspace() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("8");
        engine.inputDigit("4");
        engine.toggleSign();
        engine.backspace();
        assertEquals("-8", engine.getDisplayText(), "sign toggle and backspace");
    }

    private static void shouldHandleDecimals() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("1");
        engine.inputDecimalPoint();
        engine.inputDigit("5");
        engine.applyOperator(CalculatorEngine.Operator.ADD);
        engine.inputDigit("2");
        engine.inputDecimalPoint();
        engine.inputDigit("2");
        engine.evaluate();
        assertEquals("3.7", engine.getDisplayText(), "decimal math");
    }

    private static void shouldShowErrorOnDivisionByZero() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("9");
        engine.applyOperator(CalculatorEngine.Operator.DIVIDE);
        engine.inputDigit("0");
        engine.evaluate();
        assertEquals("Erro", engine.getDisplayText(), "division by zero");
        engine.inputDigit("4");
        assertEquals("4", engine.getDisplayText(), "recover after error");
    }

    private static void shouldClearEntryWithoutLosingOperation() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit("7");
        engine.applyOperator(CalculatorEngine.Operator.ADD);
        engine.inputDigit("9");
        engine.clearEntry();
        engine.inputDigit("2");
        engine.evaluate();
        assertEquals("9", engine.getDisplayText(), "clear entry");
    }

    private static void assertEquals(String expected, String actual, String scenario) {
        if (!expected.equals(actual)) {
            throw new AssertionError(
                "Scenario '" + scenario + "' failed. Expected '" + expected + "', got '" + actual + "'."
            );
        }
    }
}
