package pl.caluclator.string;

import org.junit.jupiter.api.Test;
import pl.calculator.string.ONP;

public class ONPTest {
    private ONP onp = new ONP();

    @Test
    void ONPCOnvert() {
        String str = "5+3*2*(3-1)";
        onp.start(str);
        System.out.println("Good conver: " + onp.getResult());
    }

    @Test
    void ONPminusnumber() {
        String str = "-2";
        onp.start(str);
        System.out.println("minus number conver: " + onp.getResult());
    }

    @Test
    void ONPminusnumberexp() {
        String str = "-2+5";
        onp.start(str);
        System.out.println("minus number exp: " + onp.getResult());
    }

    @Test
    void ONPminusnumberexp2() {
        String str = "2--4";
        onp.start(str);
        System.out.println("minus number exp2: " + onp.getResult());
    }

    @Test
    void ONPminusnumberexp3() {
        String str = "2-(-4+2)";
        onp.start(str);
        System.out.println("minus number exp3: " + onp.getResult());
    }

    @Test
    void ONPCOnvertOneNumber() {
        String str = "5";
        onp.start(str);
        System.out.println("One number: " + onp.getResult());
    }

    @Test
    void ONPCOnvertErrorString() {
        String str = "sqrt(9)";
        try {
            onp.start(str);
            System.out.println("Error exp: " + onp.getResult());
        } catch (IllegalStateException e) {
            System.out.println("Error exp: " + e.getMessage());
        }

    }

    @Test
    void ONPCOnvertDoubleNumber() {
        String str = new String("5+3.0-2");
        onp.start(str);
        System.out.println("Double number: " + onp.getResult());
    }

    @Test
    void ONPCOnvertnull() {
        String str = "";
        try {
            onp.start(str);
            System.out.println("Bad convert: " + onp.getResult());
        } catch (NullPointerException e) {
            System.out.println("Bad convert: " + e.getMessage());
        }

    }
}
