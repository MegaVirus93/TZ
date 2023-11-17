import java.util.Scanner;

/*Тестовое задание Калькулятор*/
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("(Выход командой \"stop\")\nВведите данные:");
        String s;
        while (true) {
            s = scanner.nextLine();
            if (s.equals("stop")) break;
            System.out.println("\t=\t" + calc(s));
        }

//        System.out.println("\t=\t"+calc("VI * VII"));

    }

    //Main calc
    public static String calc(String input) throws Exception {
        boolean isArabianA, isArabianB, isRomanA, isRomanB;
        Operation operation;
        String str_a, str_b, str_total;

        String[] arrayString = input.split(" ");
        if (arrayString.length > 3) throw new Exception("Формат математической операции не удовлетворяет заданию");

        str_a = arrayString[0].toUpperCase();
        str_b = arrayString[2].toUpperCase();
        switch (arrayString[1]) {
            case "+" : operation = Operation.addition; break;
            case "-" : operation = Operation.subtraction; break;
            case "*" : operation = Operation.multiplication; break;
            case "/" : operation = Operation.division; break;
            default : throw new IllegalStateException("Неизвестная операция: " + arrayString[1]);
        }

        isRomanA = str_a.matches("[MDCLXVI]*");
        isRomanB = str_b.matches("[MDCLXVI]*");
        isArabianA = str_a.matches("[0-9]*");
        isArabianB = str_b.matches("[0-9]*");

        if (isArabianA==isRomanA || isArabianB==isRomanB)
            throw new Exception("Не принадлежать Римским или Арабским системам счисления");
        if (isRomanA != isRomanB) throw new Exception("Числа разных систем счисления");

        str_total = Calculon(str_a, str_b, isArabianA, operation);
        return str_total;
    }

    static String Calculon(String str_a, String str_b, boolean isArabian, Operation operation) throws Exception {
        int int_a, int_b, int_total;
        String str_total;
        if (isArabian) {
            int_a = Integer.parseInt(str_a);
            int_b = Integer.parseInt(str_b);
            int_total= Calclulate(int_a, int_b, operation);
            str_total = Integer.toString(int_total);
        } else {
            int_a = roman2Arabian(str_a);
            int_b = roman2Arabian(str_b);
            int_total = Calclulate(int_a, int_b, operation);
            if (int_total < 1) throw new Exception("Римское число римское число может быть только положительным: " + int_total);
            str_total = arabian2Roman(int_total);
        }
        return str_total;
    }

    static int Calclulate(int a, int b, Operation operation) throws Exception {

        int total;

        if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
            throw new Exception("Выход за пределы 1..10");
        }

        switch (operation) {
            case addition: total = a + b; break;
            case subtraction: total = a - b; break;
            case multiplication: total = a * b; break;
            case division: total = a / b; break;
            default: throw new IllegalStateException("Неизвестная операция: " + operation);
        }

        return total;
    }

    static String arabian2Roman(int num) {
        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder ret = new StringBuilder();
        int ind = 0;//index of keys & vals

        while (ind < keys.length) {
            while (num >= vals[ind]) {
                var d = num / vals[ind];
                num = num % vals[ind];
                for (int i = 0; i < d; i++) {
                    ret.append(keys[ind]);
                }
            }
            ind++;
        }

        return ret.toString();
    }

    static int roman2Arabian(String string) {
        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        int summ = 0;
        int ind = 0;//index of word

        for (int i = 0; i < keys.length; i++) {
            while (string.regionMatches(ind, keys[i], 0, keys[i].length() )){
                summ+=vals[i];
                ind+= keys[i].length();
            }
        }
        return summ;
    }
}