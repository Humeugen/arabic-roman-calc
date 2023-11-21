package edu.humennikov.calculator;

import java.util.Scanner;

public class Main {
    static int result;
    static int firstOperand;
    static int secondOperand;
    static String inputLine;

    public static void main(String[] args) throws Exception {

        System.out.println("Введите арифметическую операцию из двух целых чисел одной строкой через пробел.");
        System.out.println("Цифры арабские или римские от 1 до 10 включительно. Например \"2 + 2\", \"X / II\": ");

        Scanner scanner = new Scanner(System.in);
        inputLine = scanner.nextLine().toUpperCase();
        System.out.println(calc(inputLine));
    }

    public static String calc(String inputLine) throws Exception {
        try {
            //проверяем корректность введенной строки
            String[] enteredLine = inputLine.split(" ");
            if (enteredLine.length != 3)
                throw new Exception("Некорректный ввод строки");
            //вычленяем знак мат операции
            char arithmeticSymbol = enteredLine[1].charAt(0);
            try {   // расчет и возврат в случае арабских целочисленных значений
                firstOperand = Integer.parseInt(enteredLine[0]);
                secondOperand = Integer.parseInt(enteredLine[2]);
                return "Результат: " + calculation(firstOperand, arithmeticSymbol, secondOperand);
            } catch (NumberFormatException e) { // расчет с переводами чисел из римских в арабские и обратно
                firstOperand = CalculatorHelper.romanToArabic((enteredLine[0]));
                secondOperand = CalculatorHelper.romanToArabic((enteredLine[2]));
                result = calculation(firstOperand, arithmeticSymbol, secondOperand);
                if (result < 1) throw new Exception("Римские цифры могут быть только положительные");
                return "Результат: " + CalculatorHelper.arabicToRoman(result);
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static int calculation(int digit1, char sign, int digit2) throws Exception {
        if (digit1 < 1 || digit1 > 10 || digit2 < 1 || digit2 > 10)
            throw new Exception("Некорректный ввод данных");
        return switch (sign) {
            case '+' -> result = digit1 + digit2;
            case '-' -> result = digit1 - digit2;
            case '*' -> result = digit1 * digit2;
            case '/' -> result = digit1 / digit2;
            default -> throw new Exception("Некорректный ввод данных - символ мат операции");
        };
    }
}

class CalculatorHelper { // методы для перевода цифр из римских а арбские и наоборот
    static int romanToArabic(String roman) throws Exception {
        return switch (roman) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> throw new Exception("Некорректный ввод строки");
        };
    }

    static String arabicToRoman(int arabic) {
        String roman = switch (arabic / 10) {
            case 1 -> "X";
            case 2 -> "XX";
            case 3 -> "XXX";
            case 4 -> "XL";
            case 5 -> "L";
            case 6 -> "LX";
            case 7 -> "LXX";
            case 8 -> "LXXX";
            case 9 -> "XC";
            case 10 -> "C";
            default -> "";
        };
        roman += switch (arabic % 10) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            default -> "";
        };
        return roman;
    }
}
