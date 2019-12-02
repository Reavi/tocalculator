package pl.calculator.string;

import java.util.ArrayList;

public class EntryGuard {
    public EntryGuard() {}

    public void process(String s, ArrayList<String> list){
        boolean wrong=true;
        for(char c : s.toCharArray()){
            wrong=true;
            if(!Character.isDigit(c)){
                for(String l : list){
                    if (l.charAt(0) == c) {
                        wrong = false;
                        break;
                    }
                }

            }else{
                wrong=false;
            }
        }
        if(wrong){
            throw new IllegalStateException("Nieobsługiwany znak!");
        }

    }
}
