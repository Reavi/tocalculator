package pl.calculator.operations;

import pl.calculator.Operation;

public class Mul implements Operation {
    @Override
    public String getSign() {
        return "*";
    }

    @Override
    public String getDescription() {
        return "Mnoży dwie liczby. Wzór: liczba*liczba";
    }

    @Override
    public double action(double a, double... arg) {
        for(double i : arg){
            a*=i;
        }
        return a;
    }

    @Override
    public int getValidity() {
        return 1;
    }
}
