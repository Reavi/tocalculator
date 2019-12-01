package pl.calculator;
import pl.calculator.factory.*;
import pl.calculator.plugins.LoadedPlugins;
import pl.calculator.plugins.LoaderPlugin;
import pl.calculator.plugins.Plugin;


import java.util.ArrayList;
import java.util.Map;

public class Calculator{
	private double sum=0;
	private String actualS;
	private String sign;
	private double pB;
	private LoadedPlugins lps = new LoadedPlugins();
	private Plugin pl = new Plugin();

	public Calculator() throws Exception {
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

	}
	private void loadPlugins(LoaderPlugin lp) throws Exception {
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
			System.out.println("Złe wyrażenie");
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
}
