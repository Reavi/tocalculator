package pl.caluclator.string;

import org.junit.jupiter.api.Test;
import pl.calculator.Operation;
import pl.calculator.factory.*;
import pl.calculator.plugins.PluginList;
import pl.calculator.string.ProcessString;

import java.util.ArrayList;

public class ProcessStringTest {
    private PluginList listOfPluginLoaded=new PluginList();
    private ProcessString ps;
    @Test
    void test(){
        ArrayList<Operation> op = new ArrayList<>();
        op.add(new AddFactory().CreateOperation());
        op.add(new SubFactory().CreateOperation());
        op.add(new MulFactory().CreateOperation());
        op.add(new DivFactory().CreateOperation());
        op.add(new SqrtFactory().CreateOperation());
        for(Operation o : op){
            listOfPluginLoaded.addOb(o);
        }
        ps=new ProcessString("2+sqrt(9)-4*2",listOfPluginLoaded);
        System.out.println("wynik: "+ps.getResultString());
    }


}
