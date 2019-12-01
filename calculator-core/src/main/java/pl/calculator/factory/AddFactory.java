package pl.calculator.factory;

import pl.calculator.Operation;
import pl.calculator.operations.Add;

public class AddFactory extends OperationFactory {
    @Override
    public Operation CreateOperation() {
        return new Add();
    }
}
