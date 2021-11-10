import java.util.Scanner;
/**
 * This class allows you to check if the expression is correct and get the value
 */
public class StringAnalysator {
    final int NONE = 0;
    final int DELIMITER = 1;
    final int VARIABLE = 2;
    final int NUMBER = 3;

    final String SYNTAX = "Syntax Error";
    final String UNPAIR_BRACKETS = "Unpair Brackets Error";
    final String NO_EXP = "No Expression Presented";
    final String DIV_BY_ZERO = "Division by Zero";

    final String end_of_exp = "\0";
    private String exp;
    private int exp_index;
    private String cur_char;
    private int cur_char_type;

    Container<Double> vars = new Container<>();
    Scanner scanner = new Scanner(System.in);

    /**
     * analyses the expression and returns the value
     * @param str type String
     * @return result
     * @exception Exception
     */
    public double analyse(String str) throws Exception {
        double result;
        exp = str;
        exp_index = 0;
        getCur();
        if (cur_char.equals(end_of_exp))
            throw new Exception(NO_EXP);
        result = summationOrSubtraction();
        if (!cur_char.equals(end_of_exp))
            throw new Exception(NO_EXP);
        return result;
    }

    /**
     * working if expression includes summation or subtraction
     * @return result
     * @exception Exception
     */
    private double summationOrSubtraction() throws Exception {
        double result = multiplyOrDivide();
        char op;
        double partialResult;
        while ((op = cur_char.charAt(0)) == '+' || op == '-') {
            getCur();
            partialResult = multiplyOrDivide();
            switch (op) {
                case '-' -> result -= partialResult;
                case '+' -> result += partialResult;
            }
        }
        return result;
    }

    /**
     * working if expression needed to be multiplied or divided
     * @return result
     * @exception Exception
     */
    private double multiplyOrDivide() throws Exception {
        double result = toPower();
        char op;
        double partialResult;
        while ((op = cur_char.charAt(0)) == '*' || op == '/' | op == '%') {
            getCur();
            partialResult = toPower();
            switch (op) {
                case '*' -> result *= partialResult;
                case '/' -> {
                    if (partialResult == 0.0)
                        throw new Exception(DIV_BY_ZERO);
                    result /= partialResult;
                }
                case '%' -> {
                    if (partialResult == 0.0)
                        throw new Exception(DIV_BY_ZERO);
                    result %= partialResult;
                }
            }
        }
        return result;
    }

    /**
     * works if expression needed to be powered
     * @return result
     * @throws Exception
     */
    private double toPower() throws Exception {
        double result = unaryPlusOrMinus();
        double partialResult;
        double ex;
        int t;
        if (cur_char.equals("^")) {
            getCur();
            partialResult = toPower();
            ex = result;
            if (partialResult == 0.0) {
                result = 1.0;
            } else
                for (t = (int) partialResult - 1; t > 0; t--)
                    result *= ex;
        }
        return result;
    }

    /**
     * works if expression includes unary plus or minus
     * @return result
     * @throws Exception
     */
    private double unaryPlusOrMinus() throws Exception {
        double result;
        String op = "";
        if ((cur_char_type == DELIMITER) && (cur_char.equals("+") || cur_char.equals("-"))) {
            op = cur_char;
            getCur();
        }
        result = countInBrackets();
        if (op.equals("-"))
            result = -result;
        return result;
    }

    /**
     * works if expression includes calculations in brackets
     * @return result
     * @throws Exception
     */
    private double countInBrackets() throws Exception {
        double result;
        if (cur_char.equals("(")) {
            getCur();
            result = summationOrSubtraction();
            if (!cur_char.equals(")"))
                throw new Exception(UNPAIR_BRACKETS);
            getCur();
        } else
            result = value();
        return result;
    }

    /**
     * works when expression needs to be calculated
     * @return result
     * @throws Exception
     */
    private double value() throws Exception {
        double result;
        if (cur_char_type == NUMBER) {
            try {
                result = Double.parseDouble(cur_char);
            } catch (NumberFormatException exc) {
                throw new Exception(SYNTAX);
            }
            getCur();
        } else if (cur_char_type == VARIABLE) {
            try {
                if (!vars.isEmpty() && vars.get(cur_char) != null) {
                    result = vars.get(cur_char);
                } else {
                    System.out.println("Введите значение для переменной " + cur_char);
                    result = scanner.nextDouble();
                    vars.addToHead(result, cur_char);
                }
            } catch (NumberFormatException exc) {
                throw new Exception(SYNTAX);
            }
            getCur();
        } else {
            throw new Exception(SYNTAX);
        }
        return result;
    }

    /**
     * analyses current character in expression
     */
    private void getCur() {
        cur_char_type = NONE;
        cur_char = "";
        if (exp_index == exp.length()) {
            cur_char = end_of_exp;
            return;
        }
        while (exp_index < exp.length() && Character.isWhitespace(exp.charAt(exp_index)))
            ++exp_index;
        if (exp_index == exp.length()) {
            cur_char = end_of_exp;
            return;
        }
        if (isDelimiter(exp.charAt(exp_index))) {
            cur_char += exp.charAt(exp_index);
            exp_index++;
            cur_char_type = DELIMITER;
        } else if (Character.isLetter(exp.charAt(exp_index))) { //если переменная или функция
            while (!isDelimiter(exp.charAt(exp_index))) {
                cur_char += exp.charAt(exp_index);
                exp_index++;
                if (exp_index >= exp.length())
                    break;
            }
            cur_char_type = VARIABLE;
        } else if (Character.isDigit(exp.charAt(exp_index))) {
            while (!isDelimiter(exp.charAt(exp_index))) {
                cur_char += exp.charAt(exp_index);
                exp_index++;
                if (exp_index >= exp.length())
                    break;
            }
            cur_char_type = NUMBER;
        } else {
            cur_char = end_of_exp;
        }
    }

    /**
     * analyses if parameter is delimiter
     * @param c type char
     * @return true if c is delimiter and false otherwise
     */
    private boolean isDelimiter(char c) {
        return "+-/*%^=(){}".indexOf(c) != -1;
    }
}