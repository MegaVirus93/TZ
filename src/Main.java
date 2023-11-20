import java.util.Scanner;
import java.util.regex.Pattern;

/*Тестовое задание. Калькулятор*/
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("(Выход командой \"stop\")\nВведите данные:");
        String s;
        while (true) {

            s = scanner.nextLine();
            if (s.equals("stop")) break;

            try {
                System.out.println("= " + calc(s));
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

        }
    }

    //Main calc
    public static String calc(String input) throws Exception {

        /**
         * Находжение в строке слова разделенные пробелами и передача при создании объекта в качестве параметров
         */

        String str_a, str_b, str_operation, str_total;

        Pattern patternSplit = Pattern.compile("\\s+");
        String[] arrayParameters = input.trim().split(patternSplit.pattern());

        if (arrayParameters.length != 3) throw new Exception("Формат математической операции не удовлетворяет заданию");

        str_a = arrayParameters[0];
        str_b = arrayParameters[2];
        str_operation = arrayParameters[1];

        str_total = new Calculator(str_a, str_b, str_operation).calc();

        return str_total;
    }

}