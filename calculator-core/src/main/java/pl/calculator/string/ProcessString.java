package pl.calculator.string;

import pl.calculator.Operation;
import pl.calculator.factory.SqrtFactory;
import pl.calculator.operations.Sqrt;
import pl.calculator.plugins.PluginList;

import java.util.ArrayList;

public class ProcessString {
    private String string;
    private ArrayList<String> operands;
    private PluginList ppl;
    private double sum;
    public ProcessString(String s, PluginList pl){
        this.string=s;
        this.ppl=pl;
        this.ppl.addOb(new SqrtFactory().CreateOperation());
        this.operands=new ArrayList<>(ppl.getOperands());
        this.operands.add("(");
        this.operands.add(")");
        run();
    }
    public double getSum(){
        return this.sum;
    }
    private void run(){
        //znajdz pasujacy wyraz
        ArrayList<String> oneArgumentOperations=new ArrayList<>();
        ppl.getOb().forEach((k,v) -> {
            if(v.getValidity()>=5){
                oneArgumentOperations.add(v.getSign());
            }
        });
        System.out.println("Jednoargumentowe: ");
        oneArgumentOperations.forEach((System.out::println));
        for(String s : oneArgumentOperations){
            int index=new MyString(this.string,s).getFirstIndex();
            if(index!=-1){
                int first=index+s.length();
                int last=new MyString(this.string.substring(first),")").getFirstIndex()+first;
                System.out.println("first: "+first);
                System.out.println("last: "+last);
                System.out.println(this.string.substring(first+1,last));
                String tmp=this.string.substring(first+1,last);//dzialanie
                //Tylko dla samych liczb :X
                double liczba=Double.parseDouble(tmp);
                double suma=this.ppl.getObOne(s).action(liczba);
                //mamy wynik wiec z stringa trzeba usunac i wkleic wynik
                this.string=this.string.substring(0,index)+Double.toString(suma)+this.string.substring(last+1);
                System.out.println(this.string);
            }else{
                //NIE MA TAKIEGO OPERANDU
                //CHYBA NIC NIE ROB
            }
        }
    }


}
