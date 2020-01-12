package pl.calculator.repository.history;


import java.util.ArrayList;
import java.util.HashMap;

public class History {
    private int count=0;
    private HashMap<Integer, ArrayList<String>> history=new HashMap<>();
    public void add(String series, String result){
        history.computeIfAbsent(count, k-> new ArrayList<>()).add(series+"="+result);
                count++;
    }
    public HashMap<Integer,ArrayList<String>> getHistory(){
        return history;
    }

}
