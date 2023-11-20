import java.util.regex.Pattern;

class Calculator {

    private String str_a;
    private String str_b;
    private String str_operation;

    private final Pattern patternArabian = Pattern.compile("[0-9]+");
    private static final Pattern patternRoman = Pattern.compile("[MDCLXVI]+");
    private static final Pattern patternOperation = Pattern.compile("[+\\-*/]");

    private static final String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};


    Calculator(String str_a, String str_b, String str_operation){
        this.str_a = str_a;
        this.str_b = str_b;
        this.str_operation = str_operation;
    }

    String calc() throws Exception {

        /**
         * Основная часть, где осуществляется проверка на принадлежность
         * той или иной системе счисления, и поддерживаемых операции,
         * для дальнейших вычислений
         */

        int a, b, total;
        boolean isArabian, isArabianA, isArabianB, isRomanA, isRomanB;
        Operation operation;
        String str_total;

        isRomanA = str_a.matches(patternRoman.pattern());
        isRomanB = str_b.matches(patternRoman.pattern());
        isArabianA = str_a.matches(patternArabian.pattern());
        isArabianB = str_b.matches(patternArabian.pattern());

/*
        здесь проверку можно убрать т.к. проверка избыточна, и проверяется ниже в switch-case
        if(!str_operation.matches(patternOperation.pattern()))
            throw new Exception("Неизвестная операция!" + str_operation);
 */
        if (!isArabianA & !isRomanA | !isArabianB & !isRomanB)
            throw new Exception("Числа не принадлежат Римским или Арабским системам счисления");
        if (isRomanA != isRomanB | isArabianA != isArabianB)
            throw new Exception("Числа разных систем счисления");

        isArabian = isArabianA & isArabianB;//Можно взять просто isArabianA, т.к. прошла проверку

        switch (str_operation) {
            case "+": operation = Operation.addition; break;
            case "-": operation = Operation.subtraction; break;
            case "*": operation = Operation.multiplication; break;
            case "/": operation = Operation.division; break;
            default: throw new Exception("Неизвестная операция!" + str_operation);
        }

        if (isArabian) {
            a = Integer.parseInt(str_a);
            b = Integer.parseInt(str_b);
            if ((a < 1 || a > 10) || (b < 1 || b > 10)) throw new Exception("Выход за пределы 1..10 по условию задачи");
            total = calclulate(a, b, operation);
            str_total = Integer.toString(total);
        } else {
            a = roman2Arabian(str_a);
            b = roman2Arabian(str_b);
            total = calclulate(a, b, operation);
//            if (total < 1) throw new Exception("Римское число может быть только положительным: " + total);
            str_total = arabian2Roman(total);
        }
        return str_total;
    }

    private int calclulate(int a, int b, Operation operation) throws Exception {

        /**
         * Выполнение арифметических опрерации исходя из параметра operation
         */

        int total;

        switch (operation) {
            case addition: total = a + b; break;
            case subtraction: total = a - b; break;
            case multiplication: total = a * b; break;
            case division: total = a / b; break;
            default: throw new IllegalStateException("Неизвестная операция: " + operation);
        }

        return total;
    }

    static String arabian2Roman(int number) throws Exception {

        /**
         * Перевод из арабского в римский путем
         * нахождения количества римского значения
         * и последовательного записывания римского
         * от старшего разряда к младшему
         */

        StringBuilder sb = new StringBuilder();

        if (number < 1) throw new Exception("Римское число может быть только положительным: " + number);


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

    static int roman2Arabian(String string) throws Exception {

        /**
         * Перевод из римского в арабский путем
         * нахождения римского значения
         * и сложения в арабском значении
         * от старшего разряда к младшему
         */

        int summ = 0;
        int indexOfWord = 0;

        if(!string.matches(patternRoman.pattern()))
            throw new Exception("Число не принадлежит Римской системе счисления" + string);

        for (int i = 0; i < keys.length; i++) {
            while (string.regionMatches(indexOfWord, keys[i], 0, keys[i].length())) {
                summ += values[i];
                indexOfWord += keys[i].length();
            }
        }
        return summ;
    }
}