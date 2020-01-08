package pl.calculator.api;

import com.google.gson.Gson;
import pl.calculator.CalcCore;


public class Calculator {
    private CalcCore cal;
    public Calculator(String s) {
        cal = new CalcCore(s);
    }
    public void processData(String s){
        try{
            cal.read2(s);
        }catch (IllegalStateException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public Double getResult(){
        return cal.result();
    }
    public void updateMods(){
        cal.updateMods();
    }
    public String getPluginListString(){
        StringBuilder tmp= new StringBuilder();
        for(String i : cal.getPLuginList()){
            tmp.append(i).append(",");
        }
        return tmp.toString();
    }
}
