package pl.calculator.operations;

import pl.calculator.Operation;

public class Sqrt implements Operation {
    @Override
    public String getSign() {
        return "s";
    }

    @Override
    public double action(double a, double... arg) {
        return Math.sqrt(a);
    }

    @Override
    public int getValidity() {
        return 5;
    }
}
