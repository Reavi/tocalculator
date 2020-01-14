package pl.calculator.operations;


import pl.calculator.Operation;

public class Add implements Operation {
    @Override
    public String getSign() {
        return "+";
    }

    @Override
    public double action(double a, double... arg) {
        double suma = a;
        for (double v : arg) {
            suma += v;
        }
        return suma;
    }

    @Override
    public int getValidity() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Dodaje dwie liczby. Wzór: liczba+liczba";
    }
}
