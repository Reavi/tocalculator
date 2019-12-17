package pl.calculator.plugins;

import pl.calculator.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PluginList implements Observer {
    private ArrayList<String> operands=new ArrayList<>();
    private Map<String, Operation> ob=new HashMap<>();
    private ArrayList<String> listFile=new ArrayList<>();

    ArrayList<String> getListFile() {
        return listFile;
    }
    void addToListFile(String s){
        this.listFile.add(s);
    }

    public PluginList(){}

    @Override
    public void update() {
        Map<String, Operation> tmp = new LoaderPlugin(this).load();
        for (Map.Entry<String, Operation> entry : tmp.entrySet()) {
            this.addOb(entry.getValue());

        }
    }
    public ArrayList<String> getOperands() {
        return operands;
    }
    public void addOb(Operation o){
        ob.put(o.getSign(),o);
        operands.add(o.getSign());
    }
    public Map<String, Operation> getOb() {
        return ob;
    }
    public int getSizeOperand(){
        return operands.size();
    }
    public String getOp(int i){
        return operands.get(i);
    }
    public Operation getObOne(String sign){
        return ob.get(sign);
    }
}
