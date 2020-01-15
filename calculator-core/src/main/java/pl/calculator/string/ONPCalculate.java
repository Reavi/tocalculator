package pl.calculator.string;

import pl.calculator.Operation;
import pl.calculator.factory.AddFactory;
import pl.calculator.factory.DivFactory;
import pl.calculator.factory.MulFactory;
import pl.calculator.factory.SubFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ONPCalculate {
    private String onp;
    private Stack<String> stack = new Stack<>();
    private Map<String, Operation> operacje = new HashMap<>();

    public ONPCalculate() {
        prepare();
    }

    public ONPCalculate(String str) {
        prepare();

        this.start(str);
    }

    private void prepare() {
        operacje.put("+", new AddFactory().CreateOperation());
        operacje.put("-", new SubFactory().CreateOperation());
        operacje.put("*", new MulFactory().CreateOperation());
        operacje.put("/", new DivFactory().CreateOperation());
    }

    public void start(String str) {
        str = str.replaceAll("  ", " ");
        str=str.trim();
        this.onp = str;
        while (!onp.isEmpty()) {
            int index = getIndex();
            String obj;
            if (index != -1) {
                if(index==0){
                    obj=this.onp.substring(0,1);
                }else{
                    obj = this.onp.substring(0, index);
                }

                this.onp = this.onp.substring(index + 1);
            } else {
                //jezeli nie ma spacji no to znaczy ze zostal jeden element i nic nie trzeba zrobic
                obj = this.onp;
                this.onp = "";
            }
            int len = obj.length();
            //jezeli to nie znak to wrzuc na stos
            if (obj.charAt(0) != '+' && (obj.charAt(0) != '-' || (obj.charAt(0) == '-' && obj.length() > 1)) && obj.charAt(0) != '*' && obj.charAt(0) != '/') {
                stack.push(obj);
            } else {
                String a = stack.pop();
                String b = stack.pop();
                double suma = operacje.get(obj).action(Double.parseDouble(b), Double.parseDouble(a));
                stack.push(Double.toString(suma));
            }
        }
    }

    private int getIndex() {
        return this.onp.indexOf(" ");
    }

    public String getResult() {
        return stack.pop();
    }
}
