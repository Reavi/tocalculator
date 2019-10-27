package pl.calculator;

public class Calculator implements ICalc{
	private double sum=0;
	public Calculator() {
		
	}
	public double add(double a,double b) {
		return a+b;
	}
	public double min(double a,double b) {
		return a-b;
	}
	public double mul(double a,double b) {
		return a*b;
	}
	public double div(double a,double b) {
		if(b==0) {
			return 0;
		}else{
			return a/b;
		}
	}
}
