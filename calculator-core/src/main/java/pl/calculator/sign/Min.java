package pl.calculator.sign;

import pl.calculator.ICalc;

public class Min implements ICalc {
    private String minSign="-";
    @Override
    public String sign() {
        return this.minSign;
    }

    @Override
    public double action(double a, double... arg) {
        double suma =a;
        for(int i=0;i<arg.length;i++){
            suma-=arg[i];
        }
        return suma;
    }
}
