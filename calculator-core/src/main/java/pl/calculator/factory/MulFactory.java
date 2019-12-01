package pl.calculator.factory;

import pl.calculator.Operation;
import pl.calculator.operations.Mul;

public class MulFactory extends OperationFactory {
    @Override
    public Operation CreateOperation() {
        return new Mul();
    }
}
