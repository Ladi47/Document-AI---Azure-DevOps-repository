import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Nie mozna dzielic przez zero.");
        }
        return a / b;
    }

    public double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Silnia jest zdefiniowana tylko dla n >= 0.");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(a * b) / gcd(a, b);
    }

    /**
     * Prosty kalkulator RPN, np. "3 4 + 2 *" => 14
     */
    public double evaluateRpn(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Wyrazenie nie moze byc puste.");
        }

        Deque<Double> stack = new ArrayDeque<>();
        String[] tokens = expression.trim().split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
                continue;
            }

            if (stack.size() < 2) {
                throw new IllegalArgumentException("Niepoprawne wyrazenie RPN: za malo argumentow dla operatora " + token);
            }

            double b = stack.pop();
            double a = stack.pop();
            stack.push(applyOperator(a, b, token));
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Niepoprawne wyrazenie RPN: pozostaly dodatkowe wartosci na stosie.");
        }

        return stack.pop();
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private double applyOperator(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> add(a, b);
            case "-" -> subtract(a, b);
            case "*" -> multiply(a, b);
            case "/" -> divide(a, b);
            default -> throw new IllegalArgumentException("Nieznany operator: " + operator);
        };
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("5 + 7 = " + calc.add(5, 7));
        System.out.println("10 / 2 = " + calc.divide(10, 2));
        System.out.println("2^8 = " + calc.power(2, 8));
        System.out.println("5! = " + calc.factorial(5));
        System.out.println("NWD(24, 36) = " + calc.gcd(24, 36));
        System.out.println("NWW(24, 36) = " + calc.lcm(24, 36));
        System.out.println("RPN '3 4 + 2 *' = " + calc.evaluateRpn("3 4 + 2 *"));
    }
}
