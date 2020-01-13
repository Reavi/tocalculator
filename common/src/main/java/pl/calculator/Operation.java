package pl.calculator;

public interface Operation {
    String getSign();
    double action(double a, double...arg);
    int getValidity();
    String getDescription();
}
