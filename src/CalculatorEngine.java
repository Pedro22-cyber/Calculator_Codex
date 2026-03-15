import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorEngine {

    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public String symbol() {
            return symbol;
        }
    }

    private static final int DIVISION_SCALE = 10;
    private static final String ERROR_TEXT = "Erro";

    private String currentInput = "0";
    private BigDecimal storedValue;
    private Operator pendingOperator;
    private boolean resetInput = false;
    private boolean errorState = false;
    private String historyText = "";

    public void inputDigit(String digit) {
        if (errorState) {
            clearAll();
        }

        if (resetInput || "0".equals(currentInput)) {
            currentInput = digit;
            resetInput = false;
            return;
        }

        currentInput += digit;
    }

    public void inputDecimalPoint() {
        if (errorState) {
            clearAll();
        }

        if (resetInput) {
            currentInput = "0.";
            resetInput = false;
            return;
        }

        if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }

    public void toggleSign() {
        if (errorState || "0".equals(currentInput) || "0.".equals(currentInput)) {
            return;
        }

        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
    }

    public void clearEntry() {
        currentInput = "0";
        resetInput = false;
        if (errorState) {
            errorState = false;
            historyText = "";
        }
    }

    public void clearAll() {
        currentInput = "0";
        storedValue = null;
        pendingOperator = null;
        resetInput = false;
        errorState = false;
        historyText = "";
    }

    public void backspace() {
        if (errorState || resetInput) {
            clearEntry();
            return;
        }

        if (currentInput.length() <= 1 || (currentInput.length() == 2 && currentInput.startsWith("-"))) {
            currentInput = "0";
            return;
        }

        currentInput = currentInput.substring(0, currentInput.length() - 1);
    }

    public void applyOperator(Operator operator) {
        if (errorState) {
            return;
        }

        BigDecimal currentValue = parseCurrentInput();
        if (pendingOperator != null && !resetInput) {
            BigDecimal result = calculate(storedValue, currentValue, pendingOperator);
            if (result == null) {
                enterErrorState();
                return;
            }
            storedValue = result;
            currentInput = format(result);
        } else if (storedValue == null || !resetInput) {
            storedValue = currentValue;
        }

        pendingOperator = operator;
        resetInput = true;
        historyText = format(storedValue) + " " + operator.symbol();
    }

    public void evaluate() {
        if (errorState || pendingOperator == null || storedValue == null) {
            return;
        }

        BigDecimal currentValue = parseCurrentInput();
        BigDecimal result = calculate(storedValue, currentValue, pendingOperator);
        if (result == null) {
            enterErrorState();
            return;
        }

        historyText = format(storedValue) + " " + pendingOperator.symbol() + " " + format(currentValue) + " =";
        currentInput = format(result);
        storedValue = result;
        pendingOperator = null;
        resetInput = true;
    }

    public String getDisplayText() {
        return errorState ? ERROR_TEXT : currentInput;
    }

    public String getHistoryText() {
        return historyText;
    }

    private BigDecimal parseCurrentInput() {
        String normalized = currentInput.endsWith(".")
            ? currentInput.substring(0, currentInput.length() - 1)
            : currentInput;
        if (normalized.isEmpty() || "-".equals(normalized)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(normalized);
    }

    private BigDecimal calculate(BigDecimal left, BigDecimal right, Operator operator) {
        return switch (operator) {
            case ADD -> left.add(right);
            case SUBTRACT -> left.subtract(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> right.compareTo(BigDecimal.ZERO) == 0
                ? null
                : left.divide(right, DIVISION_SCALE, RoundingMode.HALF_UP).stripTrailingZeros();
        };
    }

    private String format(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    private void enterErrorState() {
        currentInput = ERROR_TEXT;
        storedValue = null;
        pendingOperator = null;
        resetInput = true;
        errorState = true;
        historyText = "";
    }
}
