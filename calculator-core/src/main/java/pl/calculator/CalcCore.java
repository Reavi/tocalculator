package pl.calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.calculator.factory.*;
import pl.calculator.plugins.LoadedPlugins;
import pl.calculator.plugins.LoaderPlugin;
import pl.calculator.plugins.Plugin;
import pl.calculator.string.EntryGuard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalcCore {
	private double sum=0;
	private String actualS;
	private String sign;
	private double pB;
	private LoadedPlugins lps;
	private Plugin pl;
	private boolean bf=true;
	private static final Logger log = LoggerFactory.getLogger(CalcCore.class);
	public CalcCore() {
		log.error("ERROR");
		log.warn("WARN");
		log.debug("DEBUG");
		log.info("INFO");
		try{
			lps=new LoadedPlugins();
			pl=new Plugin();
			ArrayList<Operation> op = new ArrayList<>();
			op.add(new AddFactory().CreateOperation());
			op.add(new SubFactory().CreateOperation());
			op.add(new MulFactory().CreateOperation());
			op.add(new DivFactory().CreateOperation());
			pl.attach(lps);
			for(Operation o : op){
				lps.addOb(o);
			}
			LoaderPlugin lp = new LoaderPlugin(lps);
			loadPlugins(lp);
		}catch (NullPointerException e){
			bf=false;
            log.info("Brak folderu na pluginy");
        }


	}
	private void loadPlugins(LoaderPlugin lp) {
		Map<String, Operation> tmp = lp.load();
		for (Map.Entry<String, Operation> entry : tmp.entrySet()) {
		lps.addOb(entry.getValue());
		}
	}

	public void read(String s){
		pl.check();
		sum=0;
		actualS=s.trim();
		int actualOperand=getIndexOperand();//miejsce operanda
		if(actualOperand==-1){
			log.warn("Złe wyrażenie");
		}
		this.sign=actualS.substring(actualOperand,actualOperand+1);//pobierz operand
		this.sum=Double.parseDouble(actualS.substring(0,actualOperand));//pierwsza liczba
		actualS=actualS.substring(actualOperand+1);
		while(!actualS.isEmpty()){
			actualOperand=getIndexOperand();//znajdz drugi operand
			if(actualOperand==-1){
				this.pB=Double.parseDouble(actualS);
				actualS="";
			}else{
				this.pB=Double.parseDouble(actualS.substring(0,actualOperand));//druga liczba

			}
			work();
			if(actualOperand!=-1){
				this.sign=actualS.substring(actualOperand,actualOperand+1);//ustaw kolejny operand
				actualS=actualS.substring(actualOperand+1);
			}
		}
	}

	public double result(){
		return sum;
	}
	private int getIndexOperand(){
		int actualOperand=-1;
		int i=0;
		int length=lps.getSizeOperand();
		while(i<length){
			actualOperand=actualS.indexOf(lps.getOp(i));
			i++;
			if(actualOperand!=-1){
				break;
			}
		}
		if(actualOperand==-1){
			return actualOperand;
		}
		for(String op : lps.getOperands()){
			if(actualS.indexOf(op)<actualOperand && actualS.contains(op)){
				actualOperand=actualS.indexOf(op);
			}
		}
		return actualOperand;
	}
	private void work(){
		Operation c=lps.getObOne(this.sign);
		this.sum=c.action(this.sum,this.pB);
	}

	public void read2(String s) {
		if(bf){pl.check();}
		new EntryGuard().process(s,lps.getOperands());
		this.actualS=s;
		ArrayList<String> parts=listOfParts();

		double suma=0;
		Map<String, Operation> obTmp = new HashMap<>(lps.getOb());
		while(!obTmp.isEmpty()){
			int highesValue=0;
			String sign="";
			for (Operation o : obTmp.values()) {
				if(highesValue<=o.getValidity()){
					highesValue=o.getValidity();
					sign=o.getSign();
				}
			}
			int index=0;
			boolean exists=false;
			for(int i=0;i<parts.size();i++){
				if (parts.get(i).equals(sign)) {
					index=i;
					exists=true;
					break;

				}

			}
			if(exists){
				this.sign=sign;
				double first=Double.parseDouble(parts.get(index-1));
				double two = Double.parseDouble(parts.get(index+1));
				Operation c=lps.getObOne(this.sign);
				suma=c.action(first,two);
				parts.set(index,String.valueOf(suma));
				parts.remove(index+1);
				parts.remove(index-1);
			}else{
				obTmp.remove(sign);
			}
		}
		this.sum=suma;

	}
	private ArrayList<String> listOfParts(){
		ArrayList<String>  lista = new ArrayList<>();
		int actualOperand=getIndexOperand();
		if(actualOperand==0 && this.actualS.charAt(0)=='-') {
			this.actualS = this.actualS.substring(1);
			actualOperand = getIndexOperand();
			if (actualOperand == -1) {
				lista.add("-" + this.actualS);
			} else {
				lista.add("-" + this.actualS.substring(0, actualOperand));
			}
			this.actualS = this.actualS.substring(actualOperand);
		}

		while(!actualS.isEmpty()){
			actualOperand=getIndexOperand();
			if(actualOperand==0) {
				lista.add(this.actualS.substring(0, 1));
				this.actualS = this.actualS.substring(actualOperand + 1);
			}else if(actualOperand==-1){
				lista.add(this.actualS);
				this.actualS="";
			}else {
				lista.add(this.actualS.substring(0,actualOperand));
				this.actualS=this.actualS.substring(actualOperand);
			}
		}
		return lista;
	}
}
