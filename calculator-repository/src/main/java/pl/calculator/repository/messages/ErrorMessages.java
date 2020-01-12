package pl.calculator.repository.messages;

import java.util.ArrayList;
import java.util.HashMap;

public class ErrorMessages {
    private static HashMap<String, ArrayList<String>> mess=new HashMap<>();

    public static void addMess(String id,String mes){
        mess.computeIfAbsent(id, k-> new ArrayList<>()).add(mes);
    }
    public static void clear(){
        mess.clear();
    }
    public static HashMap<String,ArrayList<String>> getMess(){
        return mess;
    }
}
