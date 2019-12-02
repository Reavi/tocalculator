package pl.calculator.api;

import pl.calculator.CalcCore;


public class Calculator {
    private CalcCore cal=new CalcCore();
    public Calculator() {
    }
    public void processData(String s){
        try{
            cal.read2(s);
        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }
    public Double getResult(){
        return cal.result();
    }
}
