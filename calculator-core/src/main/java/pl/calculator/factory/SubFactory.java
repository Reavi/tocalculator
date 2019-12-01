package pl.calculator.factory;

import pl.calculator.Operation;
import pl.calculator.operations.Sub;

public class SubFactory extends OperationFactory {
    @Override
    public Operation CreateOperation() {
        return new Sub();
    }
}
