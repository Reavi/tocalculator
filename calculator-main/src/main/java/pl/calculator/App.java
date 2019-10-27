package pl.calculator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
	ICalc cal=new Calculator();
	System.out.println(cal.add(1,2));
    }
}
