package pl.calculator.operations;

import pl.calculator.Operation;

public class Div implements Operation {
    @Override
    public String getSign() {
        return "/";
    }

    @Override
    public double action(double a, double... arg) {
        for(double i : arg){
            if(i==0){
                //rzuc wyjatek
            }else {
                a/=i;
            }
        }
        return 0;
    }

    @Override
    public int getValidity() {
        return 0;
    }
}
