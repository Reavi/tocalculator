package pl.calculator;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) throws Exception {
	Calculator cal=new Calculator();
	Scanner scanner = new Scanner(System.in);

	while (scanner.hasNextLine()) {
		cal.read2(scanner.nextLine());
		System.out.println(cal.result());
	}
    }
}
