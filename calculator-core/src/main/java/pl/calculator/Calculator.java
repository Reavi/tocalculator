package pl.calculator;
import pl.calculator.sign.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator{
	private double sum=0;
	private String actualS;
	private double pA;
	private String sign;
	private double pB;
	private ArrayList<String> operands=new ArrayList<>();
	private Map<String, ICalc> ob=new HashMap<>();
	public Calculator() {
		Add add=new Add();
		Min min=new Min();
		ob.put(add.sign(),add);
		ob.put(min.sign(),min);
		operands.add(add.sign());
		operands.add(min.sign());
	}
	public void read(String s){
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
			/*
			actualOperand=getIndexOperand();//miejsce operanda
			if(actualOperand==-1){
				break;
			}
			this.sign=actualS.substring(actualOperand,actualOperand+1);//pobierz operand
			//System.out.println(this.sign);
			//System.out.println(actualOperand);
			this.pA=Double.parseDouble(actualS.substring(0,actualOperand));//pierwsza liczba
			//System.out.println(this.pA);
			actualS=actualS.substring(actualOperand+1);
			//System.out.println(this.actualS);
			 */
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
		int length=operands.size();
		while(i<length){
			actualOperand=actualS.indexOf(operands.get(i));
			i++;
			if(actualOperand!=-1){
				break;
			}
		}
		if(actualOperand==-1){
			return actualOperand;
		}
		for(String op : operands){
			if(actualS.indexOf(op)<actualOperand && actualS.contains(op)){
				actualOperand=actualS.indexOf(op);
			}
		}
		return actualOperand;
	}
	private void work(){
		ICalc c= (ICalc) ob.get(this.sign);
		this.sum=c.action(this.sum,this.pB);
	}
}
