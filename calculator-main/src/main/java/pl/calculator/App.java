package pl.calculator;

import pl.calculator.api.Calculator;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator cc = new Calculator("local");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            cc.processData(scanner.nextLine());
            System.out.println(cc.getResult());
        }
    }
}
