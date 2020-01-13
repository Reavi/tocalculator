package pl.calculator.string;

import java.util.ArrayList;

public class EntryGuard {
    public EntryGuard() {}

    public void process(String s, ArrayList<String> list){
        boolean wrong=false;
        list.add(".");
        for(char c : s.toCharArray()){
            if(!Character.isDigit(c)){
                //jezeli nie jest liczba
                boolean znalazloZnak=false;
                for(String l : list){
                    if (l.charAt(0) == c) {
                        znalazloZnak = true;
                        break;
                    }
                }
                if(!znalazloZnak){
                    wrong=true;
                }

            }
        }
        if(wrong){
            throw new IllegalStateException("Nieobsługiwany znak!");
        }

    }
}
