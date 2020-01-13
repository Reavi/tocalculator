package pl.calculator.string;

import pl.calculator.plugins.PluginList;
import java.util.ArrayList;

public class ProcessString {
    private String string;
    private ArrayList<String> operands;
    private PluginList ppl;
    private String StringSuma;
    public ProcessString(String s, PluginList pl){

        this.string=s;
        this.ppl=pl;

        this.operands=new ArrayList<>(ppl.getOperands());
        this.operands.add("(");
        this.operands.add(")");

        run();
    }
    public double getSum(){
        return Double.parseDouble(this.StringSuma);
    }
    private void run(){
        //JEDNOARGUMENTOWE
        validity5();
        //JEDNOARGUMENTOWE SA GOTOWE

        //DWUARGUMENTOWY WAZNOSC 4
        validity4();
        //DWUARGUMENTOWE WAZNOSC c2
        validity2();
        System.out.println(this.string);


        //przetworzony string -> onp -> wylicz
        //sprawdzamy wczesniej czy reszta sie zgadza
        new EntryGuard().process(this.string, ppl.getOperands());
        this.StringSuma=new ONPCalculate(new ONP(this.string).getResult()).getResult();
    }
    public String getResultString(){
        return StringSuma;
    }
    private void validity5(){
        ArrayList<String> oneArgumentOperations=new ArrayList<>();
        ppl.getOb().forEach((k,v) -> {
            if(v.getValidity()>=5){
                oneArgumentOperations.add(v.getSign());
            }
        });
        //System.out.println("Jednoargumentowe: ");
        //oneArgumentOperations.forEach((System.out::println));
        for(String s : oneArgumentOperations){
            int index=new MyString(this.string,s).getFirstIndex();
            if(index!=-1){
                int first=index+s.length();
                int last=new MyString(this.string.substring(first),")").getFirstIndex()+first;
                //System.out.println("first: "+first);
                //System.out.println("last: "+last);
                //System.out.println(this.string.substring(first+1,last));
                String tmp=this.string.substring(first+1,last);//dzialanie
                //Tylko dla samych liczb :X
                double liczba=Double.parseDouble(tmp);
                double suma=this.ppl.getObOne(s).action(liczba);
                //mamy wynik wiec z stringa trzeba usunac i wkleic wynik
                this.string=this.string.substring(0,index)+Double.toString(suma)+this.string.substring(last+1);
                //System.out.println(this.string);
            }else{
                //NIE MA TAKIEGO OPERANDU
                //CHYBA NIC NIE ROB
            }
        }
    }
    private void validity4(){
        //wiadomo ze tutaj juz sa jednoargumentowe wyrazenia oraz dwu skladnikowe
        ArrayList<String> val4=new ArrayList<>();
        ArrayList<String> goodOperand=new ArrayList<>();
        ppl.getOb().forEach((k,v) -> {
            if(v.getValidity()==4){
                val4.add(v.getSign());
            }
            if(v.getValidity()<=4){
                goodOperand.add(v.getSign());
            }
        });
        check(goodOperand);
        boolean isExists=true;
        for(String sign : val4){
            int index=this.string.indexOf(sign);
            while(index!=-1){
                //znalazlo znak
                //wez liczbe przed
                boolean szukam=true;
                int copyIndex=index;
                while(szukam){
                    copyIndex=copyIndex-1;
                    for(String s: goodOperand){
                        if(this.string.charAt(copyIndex)==s.charAt(0)){
                            szukam=false;
                            break;
                        }
                    }
                    if(copyIndex<=0){
                        copyIndex=0;
                        szukam=false;
                    }
                }
                String liczbaA;
                if(copyIndex==0){
                    liczbaA=this.string.substring(0,index);
                }else{
                    liczbaA=this.string.substring(copyIndex+1,index);
                }
                //wez liczbe po
                szukam=true;
                int copyIndex2=index+1;
                while(szukam){

                    for(String s: goodOperand){
                        if(this.string.charAt(copyIndex2)==s.charAt(0)){
                            szukam=false;
                            break;
                        }
                    }
                    copyIndex2=copyIndex2+1;
                    if(copyIndex2>=this.string.length()){
                        szukam=false;
                    }
                }

                String liczbaB;
                if(copyIndex2>=this.string.length()){
                    liczbaB=this.string.substring(index+1);
                }else{
                    liczbaB=this.string.substring(index+1,copyIndex2-1);
                }

                double a=Double.parseDouble(liczbaA);
                double b=Double.parseDouble(liczbaB);
                double suma=ppl.getObOne(sign).action(a,b);
                if(copyIndex!=0){
                    copyIndex+=1;
                }
                if(copyIndex2<this.string.length()){
                    copyIndex2-=1;
                }
                this.string=this.string.substring(0,copyIndex)+suma+this.string.substring(copyIndex2);

                System.out.println(this.string);
                index=this.string.indexOf(sign);
            }
            //nie ma indeksu leci do nastepnego znaku
        }


    }
    private void validity2(){
        //wiadomo ze tutaj juz sa jednoargumentowe wyrazenia oraz dwu skladnikowe
        ArrayList<String> val4=new ArrayList<>();
        ArrayList<String> goodOperand=new ArrayList<>();
        ppl.getOb().forEach((k,v) -> {
            if(v.getValidity()==2){
                val4.add(v.getSign());
            }
            if(v.getValidity()<=2){
                goodOperand.add(v.getSign());
            }
        });
        boolean isExists=true;
        for(String sign : val4){
            int index=this.string.indexOf(sign);
            while(index!=-1){
                //znalazlo znak
                //wez liczbe przed
                boolean szukam=true;
                int copyIndex=index;
                while(szukam){
                    copyIndex=copyIndex-1;
                    for(String s: goodOperand){
                        if(this.string.charAt(copyIndex)==s.charAt(0)){
                            szukam=false;
                            break;
                        }
                    }
                    if(copyIndex<=0){
                        copyIndex=0;
                        szukam=false;
                    }
                }
                String liczbaA;
                if(copyIndex==0){
                    liczbaA=this.string.substring(0,index);
                }else{
                    liczbaA=this.string.substring(copyIndex+1,index);
                }
                //wez liczbe po
                szukam=true;
                int copyIndex2=index+1;
                while(szukam){

                    for(String s: goodOperand){
                        if(this.string.charAt(copyIndex2)==s.charAt(0)){
                            szukam=false;
                            break;
                        }
                    }
                    copyIndex2=copyIndex2+1;
                    if(copyIndex2>=this.string.length()){
                        szukam=false;
                    }
                }

                String liczbaB;
                if(copyIndex2>=this.string.length()){
                    liczbaB=this.string.substring(index+1);
                }else{
                    liczbaB=this.string.substring(index+1,copyIndex2-1);
                }

                double a=Double.parseDouble(liczbaA);
                double b=Double.parseDouble(liczbaB);
                double suma=ppl.getObOne(sign).action(a,b);
                if(copyIndex!=0){
                    copyIndex+=1;
                }
                if(copyIndex2<this.string.length()){
                    copyIndex2-=1;
                }
                this.string=this.string.substring(0,copyIndex)+suma+this.string.substring(copyIndex2);

                System.out.println(this.string);
                index=this.string.indexOf(sign);
            }
            //nie ma indeksu leci do nastepnego znaku
        }


    }
    private void check(ArrayList<String> op){
        for(String o : op){
            if(this.string.charAt(0)==o.charAt(0)){
                throw new IllegalArgumentException("Proszę wpisać poprawne wyrażenie");
            }
        }
    }

}
