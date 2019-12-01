package pl.calculator.operations;

import pl.calculator.Operation;

public class Sub implements Operation {
    private String sign="-";
    private int val=0;

    @Override
    public String getSign() {
        return this.sign;
    }

    @Override
    public double action(double a, double... arg) {
        double suma =a;
        for(int i=0;i<arg.length;i++){
            suma-=arg[i];
        }
        return suma;
    }

    @Override
    public int getValidity() {
        return this.val;
    }
}
