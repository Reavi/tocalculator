package pl.calculator.api;

import com.google.gson.Gson;
import pl.calculator.CalcCore;
import pl.calculator.Operation;
import pl.calculator.repository.messages.ErrorMessages;
import pl.calculator.repositoryRepresentation.json.MessagesJsonFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Calculator {
    private CalcCore cal;
    public Calculator(String s) {
        cal = new CalcCore(s);
    }
    public void processData(String s){
        try{
            ErrorMessages.clear();
            cal.read(s);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public Double getResult(){
        return cal.result();
    }
    public void updateMods(){
        cal.updateMods();
    }
    public String getPluginListStringJson(){
        Map<String, Operation> ob = cal.getPLuginList();
        Map<Integer, ArrayList<String>> pluginList = new HashMap<>();
        int count=0;
        for(Map.Entry<String, Operation> c : ob.entrySet()){
            ArrayList<String> tmp=new ArrayList<>();
            tmp.add(c.getValue().getSign());
            tmp.add(c.getValue().getDescription());
            pluginList.put(count,tmp);
            count++;
        }
        return new Gson().toJson(pluginList);
    }
    public String getHistory(){
        return cal.getHistory();
    }
    public String getMess(){
        return new MessagesJsonFormat().getString();
    }
}
