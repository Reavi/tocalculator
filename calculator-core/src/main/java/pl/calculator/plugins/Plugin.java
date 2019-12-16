package pl.calculator.plugins;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Plugin implements Observable {
    private Set<Observer> observers = new HashSet<>();
    private File[] listOfFiles;
    private int number = 0;
    public Plugin(){
        this.listOfFiles= DirReader.loadFromDirJarFile();
        if(this.listOfFiles!=null){
            this.number=listOfFiles.length;
        }


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
        File[] files = DirReader.loadFromDirJarFile();
        if(files.length!=number){
            this.listOfFiles=files;
            this.number=files.length;
            notifyObservers();
        }
    }
}
