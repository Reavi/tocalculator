package pl.calculator.sign;

import pl.calculator.ICalc;

public class Add implements ICalc {
    private String addSign="+";
    @Override
    public String sign() {
        return this.addSign;
    }

    @Override
    public double action(double a, double... arg) {
        double suma=a;
        for(int i=0;i<arg.length;i++){
            suma+=arg[i];
        }
        return suma;
    }
}
