package pl.calculator.string;

class MyString{
    private String str;
    private String expression;
    MyString(String string, String expression){
        this.str=string;
        this.expression=expression;
    }
    int getFirstIndex(){
        for(int i=0;i<this.str.length()-this.expression.length()+1;i++){
            String d = str.substring(i,i+this.expression.length());
            if(d.equals(this.expression)){
                return i;
            }
        }
        return -1;
    }
}
