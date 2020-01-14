package pl.calculator.operations;

import pl.calculator.Operation;

public class Div implements Operation {
    @Override
    public String getSign() {
        return "/";
    }

    @Override
    public String getDescription() {
        return "Dzieli dwie liczby. Wzór: liczba/liczba";
    }

    @Override
    public double action(double a, double... arg) {
        for (double i : arg) {
            if (i == 0) {
                throw new IllegalArgumentException("Nie dziel przez 0!");
            } else {
                a /= i;
            }
        }
        return a;
    }

    @Override
    public int getValidity() {
        return 1;
    }
}
