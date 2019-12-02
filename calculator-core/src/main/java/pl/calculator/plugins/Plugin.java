package pl.calculator.plugins;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Plugin implements Observable {
    private Set<Observer> observers = new HashSet<>();
    private File[] listOfFiles;
    private int number = 0;
    public Plugin(){
        this.listOfFiles=loadFromFile();
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
    private File[] loadFromFile(){
        File folder = new File(System.getProperty("user.dir")+"/plugin/");
        return folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar"));
    }

    public void check(){
        File[] files = loadFromFile();
        if(files.length!=number){
            this.listOfFiles=files;
            this.number=files.length;
            notifyObservers();
        }
    }
}
