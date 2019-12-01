package pl.calculator.factory;

import pl.calculator.Operation;
import pl.calculator.operations.Div;

public class DivFactory extends OperationFactory {
    @Override
    public Operation CreateOperation() {
        return new Div();
    }
}
