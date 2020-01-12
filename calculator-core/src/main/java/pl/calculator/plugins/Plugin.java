package pl.calculator.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.calculator.CalcCore;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Plugin implements Observable {
    private Set<Observer> observers = new HashSet<>();
    private static final Logger log = LoggerFactory.getLogger(Plugin.class);
    private int number = 0;
    public Plugin(Observer lps){
        attach(lps);
        check();
    }
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()  {
        for (Observer observer : observers) {
            try {
                observer.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void check(){
        log.info("sprawdzam ilossc luginow");
        File[] files = DirReader.loadFromDirJarFile();
        log.info("Ilosc: "+files.length);
        if(files.length!=number){
            this.number=files.length;
            notifyObservers();
        }
    }
}
