package pl.calculator.repository.history;


import java.util.HashMap;
import java.util.Map;

public class History {
    private int count=0;
    private Map<Integer,String> h=new HashMap<>();
    public void add(double s){
        h.put(count,Double.toString(s));
        count++;
    }
    public String getHistory(){
        StringBuilder tmp = new StringBuilder();
        for(Map.Entry<Integer,String> entry :h.entrySet()){
            tmp.append(entry.getValue()).append(",");
        }
        return tmp.toString();
    }

}
