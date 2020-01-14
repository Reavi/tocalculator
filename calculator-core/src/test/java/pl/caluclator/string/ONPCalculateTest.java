package pl.caluclator.string;

import org.junit.jupiter.api.Test;
import pl.calculator.string.ONPCalculate;

public class ONPCalculateTest {
    private ONPCalculate oc=new ONPCalculate();
    @Test
    void test(){
        String str="5 3.0 + 2 -";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }
    @Test
    void test2(){
        String str="-2 5 +";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }    @Test
    void test3(){
        String str="5 3 2 * 3 1  - * +";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }    @Test
    void test4(){
        String str="2  -4 2  + -";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }    @Test
    void test5(){
        String str="2 -4 ";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }    @Test
    void test6(){
        String str="5 3.0 + 2 -";
        oc.start(str);
        System.out.println(str+": "+oc.getResult());
    }
}
