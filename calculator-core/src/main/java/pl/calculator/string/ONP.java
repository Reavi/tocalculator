package pl.calculator.string;

import java.util.Stack;

public class ONP {
    private Stack stack = new Stack();
    private StringBuilder result;
    public ONP(){}
    public ONP(String string){
        this.start(string);
    }
    public void start(String string){
        if(string==null || string.equals("")){
            throw new NullPointerException("Puste wyrazenie");
        }
        check(string);
        string=string.replaceAll(" ","");
        this.process(string);
    }

    private void process(String str){
        boolean sign=true;
        StringBuilder result= new StringBuilder();
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) == '(') {
                stack.push("(");
                sign = true;
                result.append(" ");
            } else if (str.charAt(i) == ')') {
                result.append(" ").append(getFromStackUntilBracket());
                sign = false;
            } else if ((str.charAt(i) == '+' ||
                    str.charAt(i) == '-' ||
                    str.charAt(i) == '*' ||
                    str.charAt(i) == '/') && !sign) {
                result.append(" ").append(getFromStack(str.substring(i, i + 1)));
                sign = true;
            } else {
                if (sign && str.charAt(i) == '-') {
                    result.append(" ");
                }
                result.append(str.charAt(i));
                sign = false;
            }
        }
        result.append(getAllFromStack());
        result = new StringBuilder(result.toString().replaceAll("  ", " "));
        this.result=result;
    }
    private String getFromStackUntilBracket() {
        StringBuilder result = new StringBuilder();
        String c = null;
        if (!stack.empty()) {
            c = (String) stack.pop();
            while (!c.equals("(")){
                result.append(" ").append(c);
                if (stack.empty()) break;
                c = (String) stack.pop();
            }
        }
        if (result.length() > 0) {
            result.insert(0, " ");
        }
        return result.toString();
    }
    private String getFromStack(String operator) {
        StringBuilder result = new StringBuilder();
        String c;
        if (!stack.empty()) {
            c = (String) stack.pop();
            while (((operator.equals("+") || operator.equals("-")) && !c.equals("(")) ||
                    ((operator.equals("/") || operator.equals("*")) && (c.equals("/") || c.equals("*")))){
                result.append(" ").append(c).append(" ");
                if (stack.empty()) {
                    break;
                }

                c = (String) stack.pop();

            }
            if(!operator.equals("+") && !operator.equals("-")){
                stack.push(c);
            }
        }
        stack.push(operator);

        return result.toString();
    }
    private String getAllFromStack() {
        StringBuilder result = new StringBuilder();
        String c;
        while (!stack.empty()){
            c = (String) stack.pop();
            result.append(" ").append(c);
        }
        return result.toString();
    }
    public String getResult(){
        return this.result.toString();
    }
    private void check(String str){
        for(char s : str.toCharArray()){
            if(!Character.isDigit(s)){
                if ((s != '+') &&
                        (s != '-') &&
                        (s != '/') &&
                        (s != '*') &&
                        (s != '(') &&
                        (s != ')') &&
                        (s != '.')) {
                            //wykryje jakos inny znak
                            throw new IllegalStateException("Nieobsługiwany znak ONP");
                        }
            }

        }
    }

}
