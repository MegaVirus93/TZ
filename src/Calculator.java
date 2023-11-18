import java.util.regex.Pattern;

public class Calculator {
    static String Calculator(String str_a, String str_b, String str_operation) throws Exception {

        int a, b, total;
        boolean isArabian, isArabianA, isArabianB, isRomanA, isRomanB;
        Operation operation;
        String str_total;

        Pattern patternArabian = Pattern.compile("[0-9]+");
        Pattern patternRoman = Pattern.compile("[MDCLXVI]+");
        Pattern patternOperation = Pattern.compile("[+\\-*/]");


        isRomanA = str_a.matches(patternRoman.pattern());
        isRomanB = str_b.matches(patternRoman.pattern());
        isArabianA = str_a.matches(patternArabian.pattern());
        isArabianB = str_b.matches(patternArabian.pattern());


        if(!str_operation.matches(patternOperation.pattern()))
            throw new Exception("Неизвестная операция!" + str_operation);
        if (!isArabianA & !isRomanA | !isArabianB & !isRomanB)
            throw new Exception("Числа не принадлежат Римским или Арабским системам счисления");
        if (isRomanA != isRomanB | isArabianA != isArabianB)
            throw new Exception("Числа разных систем счисления");

        isArabian=isArabianA&isArabianB;//Можно взять просто isArabian, т.к. прошла проверку

        switch (str_operation) {
            case "+":
                operation = Operation.addition;
                break;
            case "-":
                operation = Operation.subtraction;
                break;
            case "*":
                operation = Operation.multiplication;
                break;
            case "/":
                operation = Operation.division;
                break;
            default:
                throw new Exception("Неизвестная операция!" + str_operation);
        }

        if (isArabian) {
            a = Integer.parseInt(str_a);
            b = Integer.parseInt(str_b);
            if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
                throw new Exception("Выход за пределы 1..10 по условию задачи");
            }
            total = Calclulate(a, b, operation);
            str_total = Integer.toString(total);
        } else {
            a = roman2Arabian(str_a);
            b = roman2Arabian(str_b);
            total = Calclulate(a, b, operation);
            if (total < 1) throw new Exception("Римское число римское число может быть только положительным: " + total);
            str_total = arabian2Roman(total);
        }
        return str_total;
    }

    static int Calclulate(int a, int b, Operation operation) throws Exception {

        /**
         * Выполнение опрерации исходя из параметра operation
         */

        int total;

        switch (operation) {
            case addition:
                total = a + b;
                break;
            case subtraction:
                total = a - b;
                break;
            case multiplication:
                total = a * b;
                break;
            case division:
                total = a / b;
                break;
            default:
                throw new IllegalStateException("Неизвестная операция: " + operation);
        }

        return total;
    }

    static String arabian2Roman(int number) {

        /**
         * Перевод из арабского в римский путем
         * нахождения количества римского значения
         * и последовательного записывания римского
         * от старшего разряда к младшему
         */

        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder sb = new StringBuilder();

        for (int indexOfKeyValue = 0; indexOfKeyValue < keys.length; indexOfKeyValue++) {
            while (number >= values[indexOfKeyValue]) {
                int quotient = number / values[indexOfKeyValue];
                number = number % values[indexOfKeyValue];
                for (int i = 0; i < quotient; i++)
                    sb.append(keys[indexOfKeyValue]);
            }
        }

        return sb.toString();
    }

    static int roman2Arabian(String string) {

        /**
         * Перевод из римского в арабский путем
         * нахождения римского значения
         * и сложения в арабском значении
         * от старшего разряда к младшему
         */

        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        int summ = 0;
        int indexOfWord = 0;

        for (int i = 0; i < keys.length; i++) {
            while (string.regionMatches(indexOfWord, keys[i], 0, keys[i].length())) {
                summ += values[i];
                indexOfWord += keys[i].length();
            }
        }
        return summ;
    }
}