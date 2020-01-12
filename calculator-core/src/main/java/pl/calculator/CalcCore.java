package pl.calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.calculator.factory.*;
import pl.calculator.plugins.DirReader;
import pl.calculator.plugins.PluginList;
import pl.calculator.plugins.LoaderPlugin;
import pl.calculator.plugins.Plugin;
import pl.calculator.repository.history.History;
import pl.calculator.repository.messages.ErrorMessages;
import pl.calculator.string.EntryGuard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalcCore {
	private double sum=0;
	private String actualString;
	private PluginList listOfPluginLoaded=new PluginList();
	private Plugin observerListOfPLugin;
	private static final Logger log = LoggerFactory.getLogger(CalcCore.class);
	private History his = new History();
	public CalcCore(String name) {
		DirReader.setName(name);
		prepare();
		observerListOfPLugin =new Plugin(listOfPluginLoaded);
		loadPlugins();
	}
	private void prepare(){
		ArrayList<Operation> op = new ArrayList<>();
		op.add(new AddFactory().CreateOperation());
		op.add(new SubFactory().CreateOperation());
		op.add(new MulFactory().CreateOperation());
		op.add(new DivFactory().CreateOperation());
		for(Operation o : op){
			listOfPluginLoaded.addOb(o);
		}
	}

	public void updateMods(){
		observerListOfPLugin.check();
	}
	private void loadPlugins() {
		Map<String, Operation> tmp = new LoaderPlugin(listOfPluginLoaded).load();
		for (Map.Entry<String, Operation> entry : tmp.entrySet()) {
		listOfPluginLoaded.addOb(entry.getValue());
		}
	}


	public double result(){
		return sum;
	}


	private int getIndexOperand(){
		int actualOperand=-1;
		int i=0;
		int length= listOfPluginLoaded.getSizeOperand();
		while(i<length){
			actualOperand= actualString.indexOf(listOfPluginLoaded.getOp(i));
			i++;
			if(actualOperand!=-1){
				break;
			}
		}
		if(actualOperand==-1){
			return actualOperand;
		}
		for(String op : listOfPluginLoaded.getOperands()){
			if(actualString.indexOf(op)<actualOperand && actualString.contains(op)){
				actualOperand= actualString.indexOf(op);
			}
		}
		return actualOperand;
	}
	private void checkString(String s){
		new EntryGuard().process(s, listOfPluginLoaded.getOperands());
	}
	private void procesString(String s){
		this.actualString =s;
		ArrayList<String> parts=listOfParts();

		double suma=0;
		Map<String, Operation> obTmp = new HashMap<>(listOfPluginLoaded.getOb());
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
				double first=Double.parseDouble(parts.get(index-1));
				double two = Double.parseDouble(parts.get(index+1));
				Operation c= listOfPluginLoaded.getObOne(sign);
				suma=c.action(first,two);
				parts.set(index,String.valueOf(suma));
				parts.remove(index+1);
				parts.remove(index-1);
			}else{
				obTmp.remove(sign);
			}
		}
		this.sum=suma;
		log.info("Wynik: "+suma);
		his.add(suma);


	}
	public void read2(String s) {
		try{
			log.info("Przyszło wyrażenie "+s);
			updateMods();
			checkString(s);
			procesString(s);
		}catch (IllegalStateException e){
			log.warn(e.getMessage());
			ErrorMessages.addMess("ERROR",e.getMessage());
		}



	}
	private ArrayList<String> listOfParts(){
		ArrayList<String>  lista = new ArrayList<>();
		int actualOperand=getIndexOperand();
		if(actualOperand==0 && this.actualString.charAt(0)=='-') {
			this.actualString = this.actualString.substring(1);
			actualOperand = getIndexOperand();
			if (actualOperand == -1) {
				lista.add("-" + this.actualString);
			} else {
				lista.add("-" + this.actualString.substring(0, actualOperand));
			}
			this.actualString = this.actualString.substring(actualOperand);
		}

		while(!actualString.isEmpty()){
			actualOperand=getIndexOperand();
			if(actualOperand==0) {
				lista.add(this.actualString.substring(0, 1));
				this.actualString = this.actualString.substring(actualOperand + 1);
			}else if(actualOperand==-1){
				lista.add(this.actualString);
				this.actualString ="";
			}else {
				lista.add(this.actualString.substring(0,actualOperand));
				this.actualString =this.actualString.substring(actualOperand);
			}
		}
		return lista;
	}
	public ArrayList<String> getPLuginList(){
		return  listOfPluginLoaded.getOperands();

	}
	public void clear(){
		DirReader.delete();
	}
	public String getHistory() {
	    return his.getHistory();
    }
}
