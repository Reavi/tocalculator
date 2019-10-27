package pl.calculator;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
	ICalc cal=new Calculator();
	double end=5;
	Scanner scan = new Scanner(System.in);
	while(end!=0) {
		System.out.print("Podaj a: ");
		double a=scan.nextDouble();
		System.out.print("Podaj b: ");
		double b=scan.nextDouble();
		System.out.println("Co chcesz zrobić?");
		System.out.println("[1]Dodaj");
		System.out.println("[2]Usun");
		System.out.println("[3]Pomnoz");
		System.out.println("[4]Podziel");
		System.out.println("[0]Wyjdz");
		end=scan.nextInt();
		if(end == 1 ) {
			a=cal.add(a,b);
		} else if ( end == 2) {
			a=cal.min(a,b);
		} else if (end ==3 ) {
			a=cal.mul(a,b);
		} else if (end == 4) {
			a=cal.div(a,b);
		} else if( end != 0){
			System.out.println("Wynik : " + a);
		}
	}
    }
}
