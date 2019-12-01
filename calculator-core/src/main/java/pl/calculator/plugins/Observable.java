package pl.calculator.plugins;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers() throws Exception;
}
