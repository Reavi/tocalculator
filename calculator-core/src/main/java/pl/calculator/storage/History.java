package pl.calculator.storage;

import pl.calculator.Operation;

import java.util.HashMap;
import java.util.Map;

public class History {
    private static int count=0;
    private static Map<Integer,String> h=new HashMap<>();
    public static void add(double s){
        h.put(count,Double.toString(s));
        count++;
    }
    public static String getHistory(){
        StringBuilder tmp = new StringBuilder();
        for(Map.Entry<Integer,String> entry :h.entrySet()){
            tmp.append(entry.getValue()).append(",");
        }
        return tmp.toString();
    }
}
