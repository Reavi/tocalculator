package pl.calculator.factory;

import pl.calculator.Operation;
import pl.calculator.operations.Sqrt;

public class SqrtFactory extends OperationFactory {
    @Override
    public Operation CreateOperation() {
        return new Sqrt();
    }
}
